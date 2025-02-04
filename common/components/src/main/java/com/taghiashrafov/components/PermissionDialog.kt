package com.taghiashrafov.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PermissionDialog(
    permissionTitleTextProvider: PermissionTitleTextProvider,
    permissionTextProvider: PermissionTextProvider,
    permissionButtonTextProvider: PermissionButtonTextProvider,
    isPermanentlyDeclined: Boolean,
    onDismiss: () -> Unit,
    onOkClick: () -> Unit,
    onGoToAppSettingsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = permissionTitleTextProvider.getTitle(isPermanentlyDeclined),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        },
        confirmButton = {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                HorizontalDivider()
                Text(
                    text = permissionButtonTextProvider.getButtonTitle(isPermanentlyDeclined),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            if (isPermanentlyDeclined) {
                                onGoToAppSettingsClick()
                            } else {
                                onOkClick()
                            }
                        }
                        .padding(16.dp)
                )
            }
        },
        text = {
            Text(
                text = permissionTextProvider.getDescription(
                    isPermanentlyDeclined = isPermanentlyDeclined
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },
        modifier = modifier
    )
}

interface PermissionTextProvider {
    fun getDescription(isPermanentlyDeclined: Boolean): String
}

interface PermissionButtonTextProvider {
    fun getButtonTitle(isPermanentlyDeclined: Boolean): String
}

interface PermissionTitleTextProvider {
    fun getTitle(isPermanentlyDeclined: Boolean): String
}

@Preview(showSystemUi = false, showBackground = true)
@Composable
fun PreviewPermissionDialog() {
    PermissionDialog(
        permissionTitleTextProvider = object : PermissionTitleTextProvider {
            override fun getTitle(isPermanentlyDeclined: Boolean): String {
                return "Permission Required"
            }
        },
        permissionButtonTextProvider = object : PermissionButtonTextProvider {
            override fun getButtonTitle(isPermanentlyDeclined: Boolean): String {
                return "Ok"
            }
        },
        permissionTextProvider = object : PermissionTextProvider {
            override fun getDescription(isPermanentlyDeclined: Boolean): String {
                return "This is permission is required for demo to be visible"
            }
        },
        isPermanentlyDeclined = false,
        onDismiss = {},
        onOkClick = {},
        onGoToAppSettingsClick = {}
    )
}