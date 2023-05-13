package dev.fsm.ui.components.order.report

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import dev.fsm.R
import dev.fsm.ui.components.fields.global.ClickableLink
import dev.fsm.ui.components.order.report.shimmer.ImageShimmer
import dev.fsm.ui.components.states.LoadingScreen
import dev.fsm.ui.theme.AppTheme
import dev.fsm.ui.theme.corners
import dev.fsm.ui.theme.spacing
import java.util.UUID

@Composable
fun PhotoItem(
    modifier: Modifier = Modifier,
    content: Uri,
    onDelete: () -> Unit
) {
    Box(
        modifier = modifier.size(
            width = 118.dp,
            height = 156.dp
        )
    ) {
        SubcomposeAsyncImage(
            modifier = modifier
                .fillMaxSize()
                .clip(MaterialTheme.corners.mediumRoundCornerShape()),
            model = content,
            contentScale = ContentScale.Crop,
            contentDescription = null
        ) {
            when (painter.state) {
                is AsyncImagePainter.State.Loading -> ImageShimmer()
                is AsyncImagePainter.State.Error -> Icon(
                    modifier = modifier
                        .background(MaterialTheme.colorScheme.secondaryContainer)
                        .padding(16.dp),
                    painter = painterResource(id = R.drawable.ic_photo),
                    tint = MaterialTheme.colorScheme.tertiary,
                    contentDescription = null
                )
                else -> SubcomposeAsyncImageContent()
            }
        }
        IconButton(
            modifier = modifier
                .align(alignment = Alignment.TopEnd)
                .padding(8.dp)
                .size(12.dp),
            onClick = onDelete
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_close),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary),
                contentDescription = null
            )
        }
    }
}

@Composable
fun PhotoItem(
    modifier: Modifier = Modifier,
    url: String
) {
    Box(
        modifier = modifier.size(
            width = 120.dp,
            height = 156.dp
        )
    ) {
        SubcomposeAsyncImage(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(10.dp)),
            model = url,
            contentScale = ContentScale.Crop,
            contentDescription = null
        ) {
            when (painter.state) {
                is AsyncImagePainter.State.Loading -> ImageShimmer()
                is AsyncImagePainter.State.Error -> Icon(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.secondaryContainer)
                        .padding(24.dp),
                    painter = painterResource(id = R.drawable.ic_photo),
                    tint = MaterialTheme.colorScheme.tertiary,
                    contentDescription = null
                )
                else -> SubcomposeAsyncImageContent()
            }
        }
    }
}

@Composable
fun FileItem(
    name: String,
    url: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .size(36.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(color = MaterialTheme.colorScheme.secondaryContainer)
                .padding(8.dp),
            painter = painterResource(id = R.drawable.ic_outline_file),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.secondary
        )
        MaterialTheme.spacing.SpacerMedium()
        ClickableLink(
            link = url,
            name = name
        )
    }
}

@Composable
fun FileItem(
    name: String,
    uri: Uri,
    onDelete: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .size(36.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(color = MaterialTheme.colorScheme.secondaryContainer)
                .padding(8.dp),
            painter = painterResource(id = R.drawable.ic_outline_file),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.secondary
        )
        MaterialTheme.spacing.SpacerMedium()
        Text(
            modifier = Modifier
                .weight(1F)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    // TODO: Реализовать открытие файла
                },
            text = name,
            style = TextStyle(
                color = MaterialTheme.colorScheme.secondary,
                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                textDecoration = TextDecoration.Underline,
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.size(12.dp))
        IconButton(
            modifier = Modifier.size(12.dp),
            onClick = onDelete
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_close),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.tertiary),
                contentDescription = null
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FileItemPreview() {
    AppTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            repeat(10) {
                FileItem(
                    name = UUID.randomUUID().toString(),
                    uri = Uri.EMPTY,
                    onDelete = {}
                )
            }
        }
    }
}