package com.taghiashrafov.home.presentation.permission

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.platform.LocalContext
import com.taghiashrafov.components.PermissionButtonTextProvider
import com.taghiashrafov.components.PermissionTextProvider
import com.taghiashrafov.components.PermissionTitleTextProvider
import com.taghiashrafov.home.R


@Composable
@Stable
fun fineLocationTextProvider(): PermissionTextProvider {
    val context = LocalContext.current
    return object : PermissionTextProvider {
        override fun getDescription(isPermanentlyDeclined: Boolean): String {
            return when (isPermanentlyDeclined) {
                true -> {
                    context.getString(R.string.message_coarse_location_disabled_warning)
                }

                false -> {
                    context.getString(R.string.message_coarse_location_default_warning)
                }
            }
        }
    }
}

@Composable
@Stable
fun defaultPermissionTitleProvider(): PermissionTitleTextProvider {
    val context = LocalContext.current
    return object : PermissionTitleTextProvider {
        override fun getTitle(isPermanentlyDeclined: Boolean): String {
            return when (isPermanentlyDeclined) {
                true -> {
                    context.getString(R.string.title_permission_required)
                }

                false -> {
                    context.getString(R.string.title_info)
                }
            }
        }
    }
}

@Composable
@Stable
fun defaultPermissionButtonTitleProvider(): PermissionButtonTextProvider {
    val context = LocalContext.current
    return object : PermissionButtonTextProvider {
        override fun getButtonTitle(isPermanentlyDeclined: Boolean): String {
            return when (isPermanentlyDeclined) {
                true -> {
                    context.getString(R.string.title_grant_permission)
                }

                false -> {
                    context.getString(R.string.title_ok)
                }
            }
        }
    }
}