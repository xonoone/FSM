package dev.fsm.ui.components.order.report

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import dev.fsm.R
import dev.fsm.presentation.model.report.OrderListItem
import dev.fsm.ui.theme.AppTheme
import dev.fsm.ui.theme.buttons
import dev.fsm.ui.theme.spacing
import java.util.UUID

@Composable
fun ReportListMultiple(
    modifier: Modifier = Modifier,
    title: String,
    required: Boolean = false,
    values: List<OrderListItem>? = null,
    onValuesChange: (index: Int) -> Unit,
) {
    Category(
        modifier = modifier,
        name = if (required) "$title*" else title
    ) {
        values?.forEachIndexed { index, item ->
            var isChecked by rememberSaveable { mutableStateOf(item.isChecked) }
            MaterialTheme.buttons.Checkbox(
                checked = isChecked,
                onCheckedChange = {
                    isChecked = it
                    onValuesChange.invoke(index)
                },
                content = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = item.name,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            )
        } ?: Text(
            text = stringResource(id = R.string.no_data),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun ReportListSingle(
    modifier: Modifier = Modifier,
    title: String,
    required: Boolean = false,
    values: List<OrderListItem>? = null,
    onValuesChange: (lastSelectedIndex: Int?, newSelectedIndex: Int) -> Unit,
) {
    var selectedIndex: Int? by rememberSaveable { mutableStateOf(null) }
    values?.forEachIndexed { index, item ->
        if (item.isChecked) selectedIndex = index
    }
    Category(
        modifier = modifier,
        name = if (required) "$title*" else title
    ) {
        values?.let {
            MaterialTheme.buttons.RadioGroup(
                modifier = Modifier.fillMaxSize(),
                values = values,
                content = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = it.name,
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                selected = selectedIndex,
                onChange = { index ->
                    onValuesChange.invoke(selectedIndex, index)
                    selectedIndex = index
                }
            )
        } ?: Text(
            text = stringResource(id = R.string.no_data),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ListPreview() {
    val valuesMultiple = arrayListOf(
        OrderListItem(
            id = UUID.randomUUID().toString(),
            name = "1-ый вариант",
        ),
        OrderListItem(
            id = UUID.randomUUID().toString(),
            name = "2-ый вариант"
        ),
        OrderListItem(
            id = UUID.randomUUID().toString(),
            name = "3-ый вариант",
        )
    )
    val valuesSingle = arrayListOf(
        OrderListItem(
            id = UUID.randomUUID().toString(),
            name = "1-ый вариант",
        ),
        OrderListItem(
            id = UUID.randomUUID().toString(),
            name = "2-ый вариант"
        ),
        OrderListItem(
            id = UUID.randomUUID().toString(),
            name = "3-ый вариант",
        )
    )

    AppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(MaterialTheme.spacing.medium)
            ) {
                ReportListMultiple(
                    title = "Выбор из списка (несколько)",
                    values = valuesMultiple,
                    onValuesChange = { index ->
                        valuesMultiple.set(
                            index = index,
                            element = valuesMultiple[index].copy(isChecked = false)
                        )
                    }
                )
                MaterialTheme.spacing.SpacerMedium()
                ReportListSingle(
                    title = "Выбор из списка (Один)",
                    values = valuesSingle,
                    onValuesChange = { lastIndex, newIndex ->
                        valuesSingle.apply {
                            if (lastIndex != null)
                                set(
                                    index = lastIndex,
                                    element = valuesSingle[lastIndex].copy(isChecked = false)
                                )
                            set(
                                index = newIndex,
                                element = valuesSingle[newIndex].copy(isChecked = true)
                            )
                        }
                    }
                )
            }
        }
    }
}