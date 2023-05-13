package dev.fsm.ui.components.user

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.fsm.R
import dev.fsm.ui.theme.AppTheme
import dev.fsm.ui.theme.corners

@Composable
fun UserNoPhoto(
    size: Dp = 60.dp,
    shape: Shape = MaterialTheme.corners.mediumRoundCornerShape(),
) {
    Box(
        modifier = Modifier
            .size(size)
            .clip(shape)
            .background(MaterialTheme.colorScheme.surfaceVariant),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier.size(size-20.dp),
            painter = painterResource(id = R.drawable.ic_round_person),
            tint = MaterialTheme.colorScheme.tertiary,
            contentDescription = stringResource(R.string.user_image_description)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun UserNoPhotoPreview() {
    AppTheme {
        UserNoPhoto()
    }
}