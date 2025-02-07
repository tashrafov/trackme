package com.taghiashrafov.home.presentation

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState
import com.taghiashrafov.components.PermissionDialog
import com.taghiashrafov.components.multifab.FabButtonItem
import com.taghiashrafov.components.multifab.FabButtonMain
import com.taghiashrafov.components.multifab.FabButtonSub
import com.taghiashrafov.components.multifab.MultiFloatingActionButton
import com.taghiashrafov.home.R
import com.taghiashrafov.home.presentation.permission.defaultPermissionButtonTitleProvider
import com.taghiashrafov.home.presentation.permission.defaultPermissionTitleProvider
import com.taghiashrafov.home.presentation.permission.fineLocationTextProvider
import com.taghiashrafov.utils.rememberLocationEnabledState
import com.taghiashrafov.utils.requestLocationUpdates
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private val DEFAULT_ZOOM_FOR_LOCATION = 15f
private val TAG = "HomeScreen"

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {
    val state = viewModel.uiState.collectAsStateWithLifecycle()
    val context: Context = LocalContext.current
    val locationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    val cameraPositionState = rememberCameraPositionState()
    val coroutineScope = rememberCoroutineScope()
    val permissionState = rememberPermissionState(
        Manifest.permission.ACCESS_FINE_LOCATION
    ) { result ->
        if (result) {
            viewModel.onEvent(HomeEvents.OnLocationPermissionGranted)
        } else {
            viewModel.onEvent(HomeEvents.OnLocationPermissionDenied)
        }
    }
    val isLocationEnabled = rememberLocationEnabledState(context)

    val SCHEME = "package"
    val settingsLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = {
            viewModel.onEvent(HomeEvents.RequestLocationPermission)
        }
    )

    LaunchedEffect(Unit) {
        if (permissionState.status.isGranted && isLocationEnabled.value) {
            requestLocationUpdates(locationClient) { location ->
                viewModel.onEvent(HomeEvents.OnLocationChanged(location))
            }
        } else {
            viewModel.onEvent(HomeEvents.RequestLocationPermission)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.sideEffects.collectLatest { effect ->
            Log.d(TAG, "HomeScreen: $effect")
            when (effect) {
                is HomeSideEffects.OnUpdateCurrentLocation -> {
                    coroutineScope.launch {
                        cameraPositionState.animate(
                            update = CameraUpdateFactory.newLatLngZoom(
                                effect.location, DEFAULT_ZOOM_FOR_LOCATION
                            )
                        )
                    }
                }

                HomeSideEffects.LaunchLocationPermissionRequest -> {
                    permissionState.launchPermissionRequest()
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            uiSettings = MapUiSettings(zoomControlsEnabled = false, zoomGesturesEnabled = true),
            properties = MapProperties(isMyLocationEnabled = isLocationEnabled.value && permissionState.status.isGranted)
        )

        MultiFloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 16.dp, end = 16.dp),
            items = listOf(
                FabButtonItem(
                    iconRes = Icons.Filled.PlayArrow,
                    label = stringResource(R.string.start_new_journey)
                ),
                FabButtonItem(
                    iconRes = Icons.Filled.Search,
                    label = stringResource(R.string.show_previous_journeys)
                ),
            ),
            onFabItemClicked = {
                Toast.makeText(context, it.label, Toast.LENGTH_SHORT).show()
            },
            fabIcon = FabButtonMain(),
            fabOption = FabButtonSub()
        )
    }
    if (state.value.shouldShowRationaleDialog) {
        PermissionDialog(
            onDismiss = {
                viewModel.onEvent(HomeEvents.OnLocationPermissionDenied)
            },
            permissionTextProvider = fineLocationTextProvider(),
            permissionTitleTextProvider = defaultPermissionTitleProvider(),
            permissionButtonTextProvider = defaultPermissionButtonTitleProvider(),
            isPermanentlyDeclined = !permissionState.status.shouldShowRationale,
            onGoToAppSettingsClick = {
                settingsLauncher.launch(
                    Intent(
                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.fromParts(SCHEME, context.packageName, null)
                    )
                )
            },
            onOkClick = {
                permissionState.launchPermissionRequest()
            }
        )
    }
}