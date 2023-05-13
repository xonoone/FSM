package dev.fsm.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Corners(
    val default: Dp = 0.dp,
    val medium: Dp = 10.dp
) {
    @Composable
    fun defaultRoundCornerShape() = RoundedCornerShape(size = default)

    @Composable
    fun mediumRoundCornerShape() = RoundedCornerShape(size = medium)
}

val LocalCorners = compositionLocalOf { Corners() }

val MaterialTheme.corners: Corners
    @Composable
    @ReadOnlyComposable
    get() = LocalCorners.current

