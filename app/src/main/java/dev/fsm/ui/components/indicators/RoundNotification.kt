package dev.fsm.ui.components.indicators

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.fsm.ui.theme.AppTheme
import dev.fsm.ui.theme.Roboto

@Composable
fun RoundNotification(
    notifications: Int?,
    maxNotifications: Int = 99,
    size: Dp = 20.dp,
    backgroundColor: Color = MaterialTheme.colorScheme.secondary,
    notificationStyle: TextStyle = TextStyle(
        fontFamily = Roboto,
        fontStyle = FontStyle.Normal,
        fontWeight = FontWeight.W600,
        fontSize = 12.sp,
        color = Color.White
    )
) {
    if (notifications != null) {
        Box(modifier = Modifier.wrapContentSize(Alignment.Center)) {
            Text(
                modifier = Modifier
                    .size(size)
                    .background(
                        color = backgroundColor,
                        shape = AbsoluteRoundedCornerShape(size)
                    )
                    .wrapContentHeight(),
                text = if (notifications > maxNotifications ) ":D" else notifications.toString(),
                style = notificationStyle,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RoundNotificationPreview() {
    AppTheme(darkTheme = false) {
        Column(modifier = Modifier.padding(8.dp)) {
            RoundNotification(
                notifications = 0
            )
            Spacer(modifier = Modifier.height(8.dp))
            RoundNotification(
                notifications = 1
            )
            Spacer(modifier = Modifier.height(8.dp))
            RoundNotification(
                notifications = 2
            )
            Spacer(modifier = Modifier.height(8.dp))
            RoundNotification(
                notifications = 55
            )
            Spacer(modifier = Modifier.height(8.dp))
            RoundNotification(
                notifications = 99
            )
            Spacer(modifier = Modifier.height(8.dp))
            RoundNotification(
                notifications = 100
            )
            Spacer(modifier = Modifier.height(8.dp))
            RoundNotification(
                notifications = null
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                modifier = Modifier
                    .size(20.dp)
                    .background(
                        color = MaterialTheme.colorScheme.secondary,
                        shape = AbsoluteRoundedCornerShape(20.dp)
                    )
                    .wrapContentHeight(),
                text = "0",
                textAlign = TextAlign.Center,
                fontSize = 12.sp
            )
        }
    }
}