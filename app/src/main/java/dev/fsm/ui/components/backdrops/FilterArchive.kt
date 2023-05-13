package dev.fsm.ui.components.backdrops

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.fsm.R
import dev.fsm.domain.utils.INullabilityChecker.NullabilityChecker.notNull
import dev.fsm.presentation.screens.orders.models.ArchiveFilter
import dev.fsm.ui.components.indicators.RoundStatusIndicator
import dev.fsm.ui.theme.AppTheme
import dev.fsm.ui.theme.buttons
import dev.fsm.ui.theme.fields
import dev.fsm.ui.theme.spacing
import dev.fsm.utils.statuses.Statuses
import java.time.LocalDate

@Composable
fun FilterArchive(
    value: ArchiveFilter? = null,
    onValueChange: (ArchiveFilter?) -> Unit
) {
    var selectedStatus: Statuses? by rememberSaveable { mutableStateOf(value?.status) }
    var selectedSort: Boolean? by rememberSaveable { mutableStateOf(value?.isSortedEarly) }

    var dateFrom: LocalDate? by rememberSaveable { mutableStateOf(value?.dateFrom) }
    var dateTo: LocalDate? by rememberSaveable { mutableStateOf(value?.dateTo) }

    val isFilterApplied = {
        selectedStatus.notNull() or selectedSort.notNull() or dateFrom.notNull() or dateTo.notNull()
    }

    BackdropTemplate {
        Title(
            enabled = isFilterApplied(),
            onClick = {
                selectedStatus = null
                selectedSort = null
                dateFrom = null
                dateTo = null
                onValueChange.invoke(null)
            }
        )
        MaterialTheme.spacing.SpacerSmall()
        StatusChips(
            selected = selectedStatus,
            onSelectChange = { selectedStatus = it }
        )
        MaterialTheme.spacing.SpacerSmall()
        Period(
            dateFrom = dateFrom,
            onDateFromChange = { dateFrom = it },
            dateTo = dateTo,
            onDateToChange = { dateTo = it }
        )
        MaterialTheme.spacing.SpacerBig()
        Sort(
            selected = selectedSort,
            onChange = { selectedSort = it }
        )
        if (isFilterApplied()) {
            MaterialTheme.spacing.SpacerBig()
            MaterialTheme.buttons.Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                onClick = {
                    onValueChange.invoke(
                        ArchiveFilter(
                            status = selectedStatus,
                            dateFrom = dateFrom,
                            dateTo = dateTo,
                            isSortedEarly = selectedSort
                        )
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
    dateFrom: LocalDate?,
    onDateFromChange: (LocalDate) -> Unit,
    dateTo: LocalDate?,
    onDateToChange: (LocalDate) -> Unit,
) {
    Column {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.date),
            style = MaterialTheme.typography.bodyMedium
        )
        MaterialTheme.spacing.SpacerSmall()
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            MaterialTheme.fields.ClickableDateField(
                modifier = Modifier
                    .weight(1f)
                    .height(40.dp),
                name = R.string.start,
                value = dateFrom,
                onValueChange = onDateFromChange
            )
            MaterialTheme.spacing.SpacerMedium()
            MaterialTheme.fields.ClickableDateField(
                modifier = Modifier
                    .weight(1f)
                    .height(40.dp),
                name = R.string.end,
                value = dateTo,
                onValueChange = onDateToChange
            )
        }
    }
}

@Composable
private fun StatusChips(
    selected: Statuses? = null,
    onSelectChange: (Statuses) -> Unit
) {
    val values = remember { listOf(Statuses.Completed, Statuses.Canceled, Statuses.Rejected) }
    val change = remember<(Int) -> Unit> { { onSelectChange.invoke(values[it]) } }

    Text(
        text = stringResource(R.string.category_filters_status),
        modifier = Modifier.fillMaxWidth(),
        style = MaterialTheme.typography.bodyMedium
    )
    MaterialTheme.buttons.RowGroupChips(
        modifier = Modifier.fillMaxWidth(),
        values = values,
        selectedIndex = selected?.let { values.indexOf(it) },
        onSelectChanged = change,
        content = { status ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(1F),
                    text = stringResource(id = status.name),
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.size(MaterialTheme.spacing.extraSmall))
                RoundStatusIndicator(statusCode = status.code)
            }
        }
    )
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
fun FilterArchivePreview() {
    var value: ArchiveFilter? by rememberSaveable { mutableStateOf(null) }
    AppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            FilterArchive(
                value = value,
                onValueChange = { value = it }
            )
        }
    }
}