package dev.fsm.ui.components.order.info

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import dev.fsm.R
import dev.fsm.presentation.model.order.OrderList
import dev.fsm.presentation.model.order.OrderListItem
import dev.fsm.ui.components.fields.global.BulletList
import dev.fsm.ui.components.fields.global.Category
import dev.fsm.ui.theme.spacing
import java.util.UUID

@Composable
fun OrderList(
    modifier: Modifier = Modifier,
    title: String,
    content: OrderList?
) {
    Category(
        modifier = modifier,
        name = title
    ) {
        content?.let { list ->
            val items = ArrayList<String>()
            if (items.isNotEmpty()) {
                list.values.forEach {
                    if (it.isChecked) items.add(it.name)
                }
                BulletList(
                    category = if (list.isSingle) {
                        stringResource(id = R.string.single_list)
                    }
                    else stringResource(id = R.string.multiple_list),
                    items = items
                )
            } else {
                Text(
                    text = stringResource(id = R.string.no_data),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        } ?:
            Text(
                text = stringResource(id = R.string.no_data),
                style = MaterialTheme.typography.bodyMedium
        )

    }
}

@Preview(showBackground = true)
@Composable
private fun OrderListMultiplePreview() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.spacing.medium)
        ) {
            OrderList(
                title = "Необходимые инструменты",
                content = OrderList(
                    id = UUID.randomUUID().toString(),
                    name = "Инструменты",
                    isSingle = false,
                    values = arrayListOf()
                )
            )
        }
    }
}