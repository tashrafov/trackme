package com.taghiashrafov.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun OutlinedInput(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    placeHolder: String? = null,
    info: String? = null,
    error: String? = null,
    trailingIcon: ImageVector? = null,
    trailingIconClick: (() -> Unit)? = null,
    onValueChanged: (String) -> Unit
) {
    Column {
        OutlinedTextField(
            label = { Text(label) },
            value = value,
            onValueChange = onValueChanged,
            placeholder = { placeHolder?.let { placeHolderText -> Text(placeHolderText) } },
            isError = !error.isNullOrBlank(),
            maxLines = 1,
            trailingIcon = {
                trailingIcon?.let {
                    IconButton(onClick = {
                        trailingIconClick?.invoke()
                    }) {
                        Icon(
                            imageVector = it,
                            contentDescription = null
                        )
                    }
                }
            },
            modifier = modifier
        )
        error?.let { infoMessage ->
            Text(
                infoMessage,
                maxLines = 2,
                modifier = Modifier.padding(top = 2.dp),
                color = MaterialTheme.colorScheme.error
            )
        }
        info?.let { infoMessage ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 4.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Info,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.outline
                )
                Text(
                    infoMessage,
                    maxLines = 2,
                    modifier = Modifier.padding(start = 2.dp),
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun OutlinedInputPreview() {
    Column {
        OutlinedInput(
            modifier = Modifier.fillMaxWidth(),
            "Label",
            "Value",
        ) {}
        OutlinedInput(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            "Label",
            "Value",
            info = "Info text"
        ) {}
        OutlinedInput(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            "Label",
            "Value",
            error = "Error message"
        ) {}
        OutlinedInput(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            "Label",
            "Value",
            trailingIcon = Icons.Outlined.Edit
        ) {}
    }
}

