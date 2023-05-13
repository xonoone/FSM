package dev.fsm.ui.components.order.info

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.fsm.R
import dev.fsm.presentation.model.file.FileData
import dev.fsm.ui.components.fields.global.Category
import dev.fsm.ui.components.fields.global.ClickableLink
import dev.fsm.ui.theme.AppTheme
import dev.fsm.ui.theme.corners
import dev.fsm.ui.theme.spacing
import java.util.UUID

@Composable
fun OrderFilesField(
    modifier: Modifier = Modifier,
    title: String,
    content: List<FileData>?
) {
    Category(
        modifier = modifier,
        name = title
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraSmall)
        ) {
            if (!content.isNullOrEmpty()) {
                content.forEach { fileData ->
                    FileItem(
                        name = fileData.initialName
                            ?: fileData.actualName.ifEmpty { stringResource(id = R.string.no_data) },
                        link = fileData.url
                    )
                }
            } else {
                Text(
                    text = stringResource(id = R.string.no_data),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
private fun FileItem(
    modifier: Modifier = Modifier,
    name: String,
    link: String
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .size(36.dp)
                .clip(MaterialTheme.corners.mediumRoundCornerShape())
                .background(color = MaterialTheme.colorScheme.primaryContainer)
                .padding(MaterialTheme.spacing.small),
            painter = painterResource(id = R.drawable.ic_outline_file),
            contentDescription = name,
            tint = MaterialTheme.colorScheme.secondary
        )
        MaterialTheme.spacing.SpacerSmall()
        ClickableLink(
            modifier = Modifier.weight(1F),
            link = link,
            name = name
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun FilesFieldPreview() {
    AppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            OrderFilesField(
                modifier = Modifier.padding(MaterialTheme.spacing.medium),
                title = "Файлы",
                content = arrayListOf(
                    FileData(
                        initialName = "Document.doc",
                        actualName = "${UUID.randomUUID()}.doc",
                        size = 1040L,
                        url = "https://file-examples.com/wp-content/uploads/2017/02/file-sample_100kB.doc"
                    ),
                    FileData(
                        initialName = "Presentation.pdf",
                        actualName = "${UUID.randomUUID()}.pdf",
                        size = 10012L,
                        url = "https://www.africau.edu/images/default/sample.pdf"
                    ),
                    FileData(
                        initialName = null,
                        actualName = "${UUID.randomUUID()}.doc",
                        size = 1500L,
                        url = "https://file-examples.com/wp-content/uploads/2017/02/file-sample_500kB.doc"
                    ),
                    FileData(
                        initialName = null,
                        actualName = "",
                        size = 1500L,
                        url = "https://file-examples.com/wp-content/uploads/2017/02/file-sample_500kB.doc"
                    ),
                )
            )
        }
    }
}