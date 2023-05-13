package dev.fsm.ui.components.fields.global

import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import dev.fsm.ui.theme.PaletteLightColors

@Composable
fun ClickableLink(
    modifier: Modifier = Modifier,
    link: String,
    name: String = link,
    maxLines: Int = 1,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    style: TextStyle = MaterialTheme.typography.bodyMedium.copy(
        textDecoration = TextDecoration.Underline,
        color = PaletteLightColors.Blue.Blue37
    ),
) {
    val linkTag = "URL"
    val httpsString =
        if (link.startsWith("https://") or link.startsWith("http://")) link
        else "https://$link"
    val annotatedLinkString: AnnotatedString = buildAnnotatedString {
        append(name)
        addStringAnnotation(
            tag = linkTag,
            annotation = httpsString,
            start = 1,
            end = httpsString.length
        )
    }
    val uriHandler = LocalUriHandler.current
    ClickableText(
        modifier = modifier,
        text = annotatedLinkString,
        style = style,
        maxLines = maxLines,
        overflow = overflow,
        onClick = { offset ->
            annotatedLinkString.getStringAnnotations(linkTag, offset, offset)
                .firstOrNull()?.let { stringAnnotation ->
                    uriHandler.openUri(stringAnnotation.item)
                }
        }
    )
}