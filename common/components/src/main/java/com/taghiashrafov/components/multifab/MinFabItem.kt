package com.taghiashrafov.components.multifab

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MiniFabItem(
    item: FabButtonItem,
    fabOption: FabButtonSub,
    onFabItemClicked: (item: FabButtonItem) -> Unit
) {
    Row(
        modifier = Modifier
            .wrapContentSize()
            .padding(end = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = item.label,
            style = MaterialTheme.typography.labelSmall,
            color = Color.White,
            modifier = Modifier
                .clip(RoundedCornerShape(size = 8.dp))
                .background(MaterialTheme.colorScheme.scrim.copy(alpha = 0.5f))
                .padding(all = 8.dp)
        )

        FloatingActionButton(
            onClick = { onFabItemClicked(item) },
            modifier = Modifier.size(40.dp),
            containerColor = fabOption.backgroundTint,
            contentColor = fabOption.iconTint
        ) {
            Icon(
                imageVector = item.iconRes,
                contentDescription = item.label,
                tint = fabOption.iconTint
            )
        }
    }
}