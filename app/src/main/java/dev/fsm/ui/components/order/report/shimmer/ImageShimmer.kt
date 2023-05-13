package dev.fsm.ui.components.order.report.shimmer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.fsm.ui.components.extensions.modifier.shimmerEffect
import dev.fsm.ui.theme.AppTheme
import dev.fsm.ui.theme.corners

@Composable
fun ImageShimmer() {
    Box(
        modifier = Modifier
            .size(
                width = 118.dp,
                height = 156.dp
            )
            .clip(shape = MaterialTheme.corners.mediumRoundCornerShape())
            .background(color = Color.LightGray)
            .shimmerEffect()
    )
}

@Preview(showBackground = true)
@Composable
fun ImageShimmerPreview() {
    AppTheme {
        ImageShimmer()
    }
}