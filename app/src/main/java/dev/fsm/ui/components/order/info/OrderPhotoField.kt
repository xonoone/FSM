package dev.fsm.ui.components.order.info

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import dev.fsm.R
import dev.fsm.presentation.model.file.FileData
import dev.fsm.ui.components.fields.global.Category
import dev.fsm.ui.components.states.LoadingScreen
import dev.fsm.ui.theme.AppTheme
import dev.fsm.ui.theme.corners
import dev.fsm.ui.theme.spacing
import java.util.UUID

@Composable
fun OrderPhotoField(
    modifier: Modifier = Modifier,
    title: String,
    content: List<FileData>?,
) {
    val linkTag = "URL"
    val uriHandler = LocalUriHandler.current
    Category(
        modifier = modifier,
        name = title
    ) {
        content?.let {
            Row(
                modifier = Modifier
                    .clip(shape = MaterialTheme.corners.mediumRoundCornerShape())
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
            ) {
                content.forEach { item ->
                    val link = item.url
                    val httpsString =
                        if (link.startsWith("https://") or link.startsWith("http://")) link
                        else "https://$link"
                    val annotatedLinkString: AnnotatedString = buildAnnotatedString {
                        append(title)
                        addStringAnnotation(
                            tag = linkTag,
                            annotation = httpsString,
                            start = 1,
                            end = httpsString.length
                        )
                    }
                    PhotoItem(
                        modifier = Modifier.size(width = 120.dp, height = 156.dp),
                        content = item,
                        onClick = {
                            annotatedLinkString
                                .getStringAnnotations(linkTag, 1, httpsString.length)
                                .firstOrNull()?.let { stringAnnotation ->
                                    uriHandler.openUri(stringAnnotation.item)
                                }
                        }
                    )
                }
            }
        } ?: Text(
            text = stringResource(id = R.string.no_data),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun PhotoItem(
    modifier: Modifier = Modifier,
    content: FileData,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
    ) {
        SubcomposeAsyncImage(
            modifier = Modifier
                .fillMaxSize()
                .clip(MaterialTheme.corners.mediumRoundCornerShape())
                .background(color = MaterialTheme.colorScheme.errorContainer),
            model = content.url,
            contentScale = ContentScale.Crop,
            contentDescription = content.initialName ?: content.actualName
        ) {
            when (painter.state) {
                is AsyncImagePainter.State.Loading -> LoadingScreen()
                is AsyncImagePainter.State.Error -> Icon(
                    painter = painterResource(id = R.drawable.ic_outline_broken_image),
                    tint = MaterialTheme.colorScheme.onErrorContainer,
                    contentDescription = stringResource(id = R.string.no_data)
                )
                else -> {
                    SubcomposeAsyncImageContent(modifier = Modifier.clickable(onClick = onClick))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun OrderPhotoFieldPreview() {
    AppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            OrderPhotoField(
                modifier = Modifier.padding(MaterialTheme.spacing.medium),
                title = "Фотографии",
                content = arrayListOf(
                    FileData(
                        initialName = "Name",
                        actualName = UUID.randomUUID().toString(),
                        url = "https://boyard32.ru/upload/iblock/bf1/bf15ecd83b7c7c1afe2b0b5809b0a4b8.jpg"
                    ),
                    FileData(
                        initialName = null,
                        actualName = UUID.randomUUID().toString(),
                        url = "https://dvery-kazani.ru/images/1280998604_79.jpg"
                    ),
                    FileData(
                        initialName = null,
                        actualName = UUID.randomUUID().toString(),
                        url = "https://voroasdnezh.d-mastera.ru/wp-content/uploads/2016/08/ustanovka-dveri-1.jpg"
                    )
                )
            )
        }
    }
}