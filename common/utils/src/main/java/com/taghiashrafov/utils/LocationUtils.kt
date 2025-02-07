package com.taghiashrafov.utils

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Looper
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.Priority
import kotlinx.coroutines.suspendCancellableCoroutine

@SuppressLint("MissingPermission")
@Composable
fun rememberLocationEnabledState(context: Context): State<Boolean> {
    val locationManager =
        remember { context.getSystemService(Context.LOCATION_SERVICE) as LocationManager }
    val isLocationEnabled = remember { mutableStateOf(isLocationEnabled(context)) }

    DisposableEffect(context) {
        val listener = object : LocationListener {
            override fun onLocationChanged(location: Location) {}

            override fun onProviderEnabled(provider: String) {
                isLocationEnabled.value = true
            }

            override fun onProviderDisabled(provider: String) {
                isLocationEnabled.value = isLocationEnabled(context)
            }
        }

        if (isLocationEnabled(context)) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, listener)
        }

        onDispose {
            locationManager.removeUpdates(listener)
        }
    }

    return isLocationEnabled
}

private fun isLocationEnabled(context: Context): Boolean {
    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
            locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
}

@SuppressLint("MissingPermission")
suspend fun requestLocationUpdates(
    locationClient: FusedLocationProviderClient,
    updateIntervalInMillis: Long = 5000L,
    onLocationUpdated: (Location) -> Unit
) = suspendCancellableCoroutine<Unit> { continuation ->
    val locationRequest =
        LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, updateIntervalInMillis).build()
    val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            locationResult.lastLocation?.let { onLocationUpdated(it) }
        }
    }

    locationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())

    continuation.invokeOnCancellation {
        locationClient.removeLocationUpdates(locationCallback)
    }
}

fun getDistanceBetweenTwoLocations(
    startLatitude: Double,
    startLongitude: Double,
    endLatitude: Double,
    endLongitude: Double
): Float {
    val results = FloatArray(1)
    Location.distanceBetween(startLatitude, startLongitude, endLatitude, endLongitude, results)
    return results[0]
}
