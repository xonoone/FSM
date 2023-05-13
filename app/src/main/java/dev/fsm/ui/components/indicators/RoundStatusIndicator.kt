package dev.fsm.ui.components.indicators

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.fsm.ui.theme.StatusColor

@Composable
fun RoundStatusIndicator(
    modifier: Modifier = Modifier,
    size: Dp = 12.dp,
    statusCode: String,
    isChecked: Boolean = true
) {
    StatusColor(statusCode = statusCode)?.let { color ->
        RoundIndicator(
            modifier = modifier,
            size = size,
            color = color,
            content = {
                if (!isChecked) {
                    val contentSize = size / 2
                    Box(
                        modifier = Modifier
                            .size(contentSize)
                            .background(
                                color = Color.White,
                                shape = AbsoluteRoundedCornerShape(contentSize)
                            )
                    )
                }
            }
        )
    }
}