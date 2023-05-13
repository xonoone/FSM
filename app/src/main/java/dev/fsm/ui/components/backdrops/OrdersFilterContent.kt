package dev.fsm.ui.components.backdrops

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.fsm.R
import dev.fsm.domain.utils.INullabilityChecker.NullabilityChecker.orNotNull
import dev.fsm.presentation.screens.orders.tablayout.models.FilterDate
import dev.fsm.presentation.screens.orders.tablayout.models.OrdersFilter
import dev.fsm.ui.theme.AppTheme
import dev.fsm.ui.theme.buttons
import dev.fsm.ui.theme.fields
import dev.fsm.ui.theme.spacing
import java.time.LocalTime

@Composable
fun OrdersFilterContent(
    value: OrdersFilter? = null,
    onValueChange: (OrdersFilter?) -> Unit
) {
    var selectedPeriod: FilterDate? by rememberSaveable { mutableStateOf(value?.date) }
    var selectedSort: Boolean? by rememberSaveable { mutableStateOf(value?.isSortedEarly) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(MaterialTheme.spacing.medium)
    ) {
        Title(
            enabled = value?.isSortedEarly orNotNull value?.date,
            onClick = {
                onValueChange.invoke(null)
                selectedSort = null
                selectedPeriod = null
            }
        )
        MaterialTheme.spacing.SpacerSmall()
        Period(
            selected = selectedPeriod,
            onChange = { selectedPeriod = it }
        )
        MaterialTheme.spacing.SpacerBig()
        Sort(
            selected = selectedSort,
            onChange = { selectedSort = it }
        )
        if (selectedPeriod orNotNull selectedSort) {
            MaterialTheme.spacing.SpacerBig()
            MaterialTheme.buttons.Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                onClick = {
                    onValueChange.invoke(
                        OrdersFilter(date = selectedPeriod, isSortedEarly = selectedSort)
                    )
                }
            ) {
                Text(
                    text = stringResource(id = R.string.apply),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.W700
                )
            }
        }
    }
}

@Composable
private fun Sort(
    selected: Boolean?,
    onChange: (Boolean) -> Unit
) {
    val early = stringResource(id = R.string.filter_param_sort_early)
    val recent = stringResource(id = R.string.filter_param_sort_recent)
    val values: List<String> = remember { listOf(early, recent) }

    val change = remember<(Int) -> Unit> { { onChange.invoke(it == 0) } }

    Text(
        text = stringResource(R.string.category_sort),
        modifier = Modifier.fillMaxWidth(),
        style = MaterialTheme.typography.titleMedium
    )
    MaterialTheme.spacing.SpacerMedium()
    Text(
        text = stringResource(R.string.category_filters_sort),
        modifier = Modifier.fillMaxWidth(),
        style = MaterialTheme.typography.bodyMedium
    )
    MaterialTheme.spacing.SpacerSmall()
    MaterialTheme.buttons.RadioGroup(
        values = values,
        content = { name ->
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = name,
                style = MaterialTheme.typography.bodyMedium
            )
        },
        selected = selected?.let { if (it) 0 else values.lastIndex },
        onChange = change
    )
}

@Composable
private fun Period(
    selected: FilterDate?,
    onChange: (FilterDate) -> Unit
) {
    val values = remember {
        listOf(
            FilterDate.Today,
            FilterDate.Tomorrow,
            FilterDate.Week,
            FilterDate.Period()
        )
    }
    val change = remember<(Int) -> Unit> { { onChange.invoke(values[it]) } }

    Text(
        text = stringResource(R.string.category_filters_period),
        modifier = Modifier.fillMaxWidth(),
        style = MaterialTheme.typography.bodyMedium
    )
    MaterialTheme.spacing.SpacerSmall()
    MaterialTheme.buttons.RadioGroup(
        values = values,
        content = { date ->
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = date.name),
                style = MaterialTheme.typography.bodyMedium
            )
        },
        selected = selected?.let { date ->
            if (date is FilterDate.Period) values.lastIndex
            else values.indexOf(date)
        },
        onChange = change
    )
    if (selected is FilterDate.Period) {
        MaterialTheme.spacing.SpacerSmall()
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            MaterialTheme.fields.ClickableDateField(
                modifier = Modifier
                    .weight(1f)
                    .height(40.dp),
                name = R.string.start,
                value = selected.dateFrom?.toLocalDate(),
                onValueChange = {
                    onChange.invoke(selected.copy(periodFrom = it.atStartOfDay()))
                }
            )
            MaterialTheme.spacing.SpacerSmall()
            MaterialTheme.fields.ClickableDateField(
                modifier = Modifier
                    .weight(1f)
                    .height(40.dp),
                name = R.string.end,
                value = selected.dateTo?.toLocalDate(),
                onValueChange = {
                    onChange.invoke(
                        selected.copy(periodTo = it.atTime(LocalTime.MAX).withNano(0))
                    )
                }
            )
        }
    }
}

@Composable
private fun Title(
    enabled: Boolean = false,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 48.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.category_filters),
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.titleMedium
        )
        if (enabled) TextButton(
            onClick = onClick,
            colors = ButtonDefaults.textButtonColors(
                contentColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Text(text = stringResource(R.string.category_clear_all))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewScreenOrders() {
    var filters: OrdersFilter? by rememberSaveable {
        mutableStateOf(
            OrdersFilter(
                isSortedEarly = true,
                date = FilterDate.Tomorrow
            )
        )
    }
    AppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            OrdersFilterContent(
                value = filters,
                onValueChange = {
                    filters = it
                    Log.d("Test", "PreviewScreenOrders: filters = $filters")
                }
            )
        }
    }
}