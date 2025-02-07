package com.taghiashrafov.home.presentation

import android.location.Location
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.taghiashrafov.utils.getDistanceBetweenTwoLocations
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUIState())
    val uiState = _uiState.asStateFlow()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            HomeUIState()
        )

    private val _sideEffects = Channel<HomeSideEffects>(Channel.BUFFERED)
    val sideEffects = _sideEffects.receiveAsFlow()

    fun onEvent(event: HomeEvents) {
        Log.d("HomeScreen", "onEvent: $event")
        when (event) {
            is HomeEvents.OnLocationChanged -> {
                onLocationChanged(event.location)
            }

            HomeEvents.OnLocationPermissionDenied -> {
                onLocationPermissionDenied()
            }

            HomeEvents.OnLocationPermissionGranted -> {
                onLocationPermissionGranted()
            }

            HomeEvents.StartNewJourney -> {
                //todo
            }

            HomeEvents.RequestLocationPermission -> {
                _sideEffects.trySend(HomeSideEffects.LaunchLocationPermissionRequest)
            }
        }
    }

    private fun onLocationChanged(location: Location) {
        val newLocation = LatLng(location.latitude, location.longitude)
        uiState.value.currentLocation?.let { currentLocation ->
            val distance = getDistanceBetweenTwoLocations(
                newLocation.latitude,
                newLocation.longitude,
                currentLocation.latitude,
                currentLocation.longitude
            )
            if (distance > DEFAULT_MIN_DISTANCE_FOR_HOME_SCREEN_UPDATES) {
                _sideEffects.trySend(HomeSideEffects.OnUpdateCurrentLocation(newLocation))
            }
        } ?: run {
            _sideEffects.trySend(HomeSideEffects.OnUpdateCurrentLocation(newLocation))
        }
        _uiState.update { it.copy(currentLocation = newLocation) }
    }

    private fun onLocationPermissionDenied() {
        _uiState.update {
            it.copy(
                isAllPermissionGranted = false,
                shouldShowRationaleDialog = true
            )
        }
    }

    private fun onLocationPermissionGranted() {
        _uiState.update {
            it.copy(
                isAllPermissionGranted = true,
                shouldShowRationaleDialog = false
            )
        }
    }

    companion object {
        private const val DEFAULT_MIN_DISTANCE_FOR_HOME_SCREEN_UPDATES = 5f
    }
}