package dev.fsm.ui.components.buttons

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.fsm.R
import dev.fsm.ui.components.indicators.RoundNotification
import dev.fsm.ui.theme.AppTheme
import dev.fsm.ui.theme.Roboto

@Composable
fun FilterButton(
    value: Int = 0,
    maxValue: Int = 9,
    size: Dp = 12.dp,
    onClick: () -> Unit
) {
    IconButton(onClick = onClick) {
        Box(contentAlignment = Alignment.TopEnd) {
            Icon(
                painter = painterResource(id = R.drawable.icon_filter),
                contentDescription = stringResource(id = R.string.icon_filter_description),
                tint = Color.Unspecified
            )
            if (value > 0) {
                RoundNotification(
                    notifications = value,
                    maxNotifications = maxValue,
                    size = size,
                    notificationStyle = TextStyle(
                        fontFamily = Roboto,
                        fontStyle = FontStyle.Normal,
                        fontWeight = FontWeight.W600,
                        fontSize = 8.sp,
                        color = Color.White
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewFilterIconButton() {
    AppTheme {
        Column {
            FilterButton {}
            FilterButton(3) {}
            FilterButton(9) {}
            FilterButton(10) {}
        }
    }
}