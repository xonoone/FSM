package dev.fsm.ui.components.indicators

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

@Composable
fun RoundIndicator(
    modifier: Modifier = Modifier,
    size: Dp,
    color: Color,
    content: @Composable BoxScope.() -> Unit = {}
) {
    Box(
        modifier = modifier
            .size(size)
            .background(
                color = color,
                shape = AbsoluteRoundedCornerShape(size)
            ),
        contentAlignment = Alignment.Center,
        content = content
    )
}