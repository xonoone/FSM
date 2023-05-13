package dev.fsm.ui.components.images

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import dev.fsm.R
import dev.fsm.presentation.screens.user.info.view.UserImageShimmer
import dev.fsm.ui.components.user.UserNoPhoto
import dev.fsm.ui.theme.corners

@Composable
fun UserImage(
    userImageUrl: String?,
    size: Dp = 60.dp,
    shape: Shape = MaterialTheme.corners.mediumRoundCornerShape(),
    isClickable: Boolean = false,
    onClick: () -> Unit = {}
) {
    val modifier =
        if (isClickable) Modifier
            .size(size)
            .clickable(onClick = onClick)
        else Modifier.size(size)
    
    SubcomposeAsyncImage(
        modifier = modifier.clip(shape),
        model = userImageUrl,
        contentScale = ContentScale.Crop,
        contentDescription = stringResource(id = R.string.user_image_description)
    ) {
        when (painter.state) {
            is AsyncImagePainter.State.Loading -> UserImageShimmer()
            is AsyncImagePainter.State.Error -> UserNoPhoto()
            else -> SubcomposeAsyncImageContent()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserImagePreview() {
    val image = "https://s3-alpha-sig.figma.com/img/ef95/5732/04426e8c731252e1deef770a02f50ec5?Expires=1677456000&Signature=eECeQqZmic-Vek2JLfWV6v8scyzt44WFGnOEYbYwjmin7nD4XBv32caObsFrKo45C4g2oxJXnEhfkzn8r61924sd~UObH-zQo2T6~0S5Z11opho6eDbhPcvvPM6zLuOXh8iyT2Wx3XK9p9cqxBtMqGyxXdyr0u133p9y9zz1-FNBm9Q7nhojNWVtUjVxKis1wAcjG~Gr8EptQRk2-g-MCKy2ih7oXCzc-ZOyDOv2xzHVjtqfnRWUuaJJZfdPvZCEvGqgVNfbSlkC69Fpy3VHGDqNwjWNevJHqrr2GHNisjU-0QYN6QvLb8PVyQjjoTaJHch4Eku8LUZGWK8cWuR9uw__&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4"
    Box(
        modifier = Modifier.padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        UserImage(userImageUrl = image)
    }
}