package com.taghiashrafov.home.presentation

import android.location.Location
import com.google.android.gms.maps.model.LatLng

data class HomeUIState(
    val isLoading: Boolean = false,
    val currentLocation: LatLng? = null,
    val isAllPermissionGranted: Boolean = false,
    val shouldShowRationaleDialog: Boolean = false
)

sealed interface HomeSideEffects {
    data class OnUpdateCurrentLocation(val location: LatLng) : HomeSideEffects
    data object LaunchLocationPermissionRequest : HomeSideEffects
}

sealed interface HomeEvents {
    data class OnLocationChanged(val location: Location) : HomeEvents
    data object OnLocationPermissionGranted : HomeEvents
    data object OnLocationPermissionDenied : HomeEvents
    data object RequestLocationPermission : HomeEvents
    data object StartNewJourney : HomeEvents
}