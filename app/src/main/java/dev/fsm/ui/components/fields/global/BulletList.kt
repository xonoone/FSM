package dev.fsm.ui.components.fields.global

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.fsm.ui.theme.AppTheme
import dev.fsm.ui.theme.spacing

@Composable
fun BulletList(
    modifier: Modifier = Modifier,
    category: String,
    items: ArrayList<String>
) {
    Column(modifier = modifier) {
        Text(
            text = category,
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.size(4.dp))
        Column {
            items.forEach { childRegion ->
                BulletListItem(text = childRegion)
            }
        }
    }
}

@Composable
fun BulletListItem(
    modifier: Modifier = Modifier,
    text: String
) {
    val space = MaterialTheme.spacing.small
    val dotSize = 4.dp
    
    Row(
        modifier = Modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.size(space))
        Box(
            modifier = modifier
                .size(dotSize)
                .background(color = Color.Black, shape = AbsoluteRoundedCornerShape(dotSize))
        ) {}
        Spacer(modifier = Modifier.size(space))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRegions() {
    AppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                repeat(20) {
                    BulletList(
                        category = "Тольятти $it",
                        items = arrayListOf("Центральный район", "Комсомольский район")
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                }
            }
        }
    }
}