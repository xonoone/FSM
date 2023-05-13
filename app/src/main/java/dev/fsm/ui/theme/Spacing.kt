package dev.fsm.ui.theme

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Spacing(
    val default: Dp = 0.dp,
    val extraSmall: Dp = 4.dp,
    val small: Dp = 8.dp,
    val medium: Dp = 16.dp,
    val big: Dp = 32.dp,
    val large: Dp = 64.dp
) {
    @Composable
    fun SpacerExtraSmall() = Spacer(modifier = Modifier.size(extraSmall))

    @Composable
    fun SpacerSmall() = Spacer(modifier = Modifier.size(small))

    @Composable
    fun SpacerMedium() = Spacer(modifier = Modifier.size(medium))

    @Composable
    fun SpacerBig() = Spacer(modifier = Modifier.size(big))

    @Composable
    fun SpacerLarge() = Spacer(modifier = Modifier.size(large))

}

val LocalSpacing = compositionLocalOf { Spacing() }

val MaterialTheme.spacing: Spacing
    @Composable
    @ReadOnlyComposable
    get() = LocalSpacing.current
