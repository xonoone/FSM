package dev.fsm.presentation.screens.user.info.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.fsm.ui.components.extensions.modifier.shimmerEffect
import dev.fsm.ui.theme.corners

@Composable
fun UserImageShimmer() {
    Box(
        modifier = Modifier
            .size(60.dp)
            .clip(shape = MaterialTheme.corners.mediumRoundCornerShape())
            .background(color = Color.LightGray)
            .shimmerEffect()
    )
}