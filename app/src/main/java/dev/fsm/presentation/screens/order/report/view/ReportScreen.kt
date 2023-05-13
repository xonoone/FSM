package dev.fsm.presentation.screens.order.report.view

import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import dev.fsm.R
import dev.fsm.destinations.ErrorDialogDestination
import dev.fsm.destinations.InfoDialogDestination
import dev.fsm.destinations.OrdersScreenDestination
import dev.fsm.destinations.ReportScreenDestination
import dev.fsm.presentation.model.report.IField
import dev.fsm.presentation.model.report.OrderListItem
import dev.fsm.presentation.model.report.ReportView
import dev.fsm.presentation.screens.order.report.contract.ReportContract
import dev.fsm.presentation.screens.order.report.contract.ReportViewModel
import dev.fsm.ui.components.app_bars.PageAppBar
import dev.fsm.ui.components.dialogs.CloseDialog
import dev.fsm.ui.components.order.report.ReportCondition
import dev.fsm.ui.components.order.report.ReportDate
import dev.fsm.ui.components.order.report.ReportDateTime
import dev.fsm.ui.components.order.report.ReportDescription
import dev.fsm.ui.components.order.report.ReportDouble
import dev.fsm.ui.components.order.report.ReportFiles
import dev.fsm.ui.components.order.report.ReportListMultiple
import dev.fsm.ui.components.order.report.ReportListSingle
import dev.fsm.ui.components.order.report.ReportLong
import dev.fsm.ui.components.order.report.ReportMoney
import dev.fsm.ui.components.order.report.ReportPhotos
import dev.fsm.ui.components.order.report.ReportText
import dev.fsm.ui.components.states.LoadingScreen
import dev.fsm.ui.theme.buttons
import dev.fsm.ui.theme.spacing
import dev.fsm.utils.ILocalizeExceptionMessage.LocalizeExceptionMessage.localized
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@Destination
@Composable
fun ReportScreen(
    viewModel: ReportViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
    orderId: String
) {
    val context = LocalContext.current
    val state = viewModel.state.collectAsState().value
    val closeDialogState = rememberMaterialDialogState()

    var buttonComplete = remember { false }
    CloseDialog(dialogState = closeDialogState)

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            PageAppBar(
                title = stringResource(id = R.string.report),
                onBackPressed = {
                    if (state.complete is ReportContract.CompleteState.Loading) {
                        if (!closeDialogState.showing) closeDialogState.show()
                    } else {
                        navigator.navigateUp()
                    }
                }
            )
        },
        content = { innerPadding ->
            ReportContent(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                orderId = orderId,
                state = state,
                buttonCompleteEnabled = buttonComplete,
                onIntent = viewModel::handleIntent
            )
        }
    )

    val reportErrorMessage = if (state.report is ReportContract.ReportState.Failure)
        state.report.exception.localized()
    else
        stringResource(id = R.string.unexpected_error)

    val completeErrorMessage = if (state.complete is ReportContract.CompleteState.Failure)
        state.complete.exception.localized()
    else
        stringResource(id = R.string.unexpected_error)

    LaunchedEffect(state) {
        if (state.report is ReportContract.ReportState.Idle) viewModel.handleIntent(
            event = ReportContract.Intent.GetReport(id = orderId)
        )
        if (state.report is ReportContract.ReportState.Failure) {
            navigator.navigate(ErrorDialogDestination.invoke(reportErrorMessage))
        }
        if (state.complete is ReportContract.CompleteState.Success) {
            if (closeDialogState.showing) closeDialogState.hide()
        }
        if (state.complete is ReportContract.CompleteState.Failure) {
            navigator.navigate(ErrorDialogDestination.invoke(completeErrorMessage))
        }
    }

    LaunchedEffect(true) {
        viewModel.effect.collect { event ->
            when (event) {
                is ReportContract.Effect.NavigateWithPopUp -> navigator.navigate(event.direction) {
                    popUpTo(ReportScreenDestination.route) {
                        inclusive = true
                    }
                }

                is ReportContract.Effect.ShowErrorDialog -> navigator.navigate(
                    ErrorDialogDestination.invoke(message = event.exception.localized(context))
                )

                is ReportContract.Effect.ChangeStateButton -> buttonComplete = event.value
                is ReportContract.Effect.ShowSuccessDialog -> navigator.navigate(
                    InfoDialogDestination(
                        route = OrdersScreenDestination.route,
                        title = event.message
                    )
                )

                is ReportContract.Effect.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                    navigator.navigate(OrdersScreenDestination) {
                        popUpTo(ReportScreenDestination.route) {
                            inclusive = true
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ReportContent(
    modifier: Modifier,
    orderId: String,
    state: ReportContract.State,
    buttonCompleteEnabled: Boolean,
    onIntent: (ReportContract.Intent) -> Unit,
) {
    Surface(modifier = modifier) {
        when (state.complete) {
            is ReportContract.CompleteState.Loading -> LoadingScreen()
            is ReportContract.CompleteState.Success -> LoadingScreen()
            else -> when (val reportState = state.report) {
                is ReportContract.ReportState.Failure -> Unit
                is ReportContract.ReportState.Loaded -> reportState.Content(buttonCompleteEnabled) {
                    onIntent.invoke(
                        ReportContract.Intent.CompleteOrder(
                            id = orderId,
                            fields = reportState.data.fields
                        )
                    )
                }

                is ReportContract.ReportState.Loading -> LoadingScreen()
                is ReportContract.ReportState.Idle -> Unit
            }
        }
    }
}

@Composable
private fun ReportContract.ReportState.Loaded.Content(
    buttonCompleteEnabled: Boolean,
    onClick: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        ReportForm(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1F),
            reportView = data
        )
        CompleteButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.spacing.medium),
            enabled =buttonCompleteEnabled,
            onClick = onClick
        )
    }
}

@Composable
private fun ReportForm(
    modifier: Modifier = Modifier,
    reportView: ReportView
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(
            space = 16.dp,
            alignment = Alignment.Top
        )
    ) {
        item {
            ReportDescription(
                name = reportView.name,
                description = reportView.description
            )
        }
        items(
            items = reportView.fields,
            key = { item ->
                item.id
            }
        ) { item ->
            ReportItem(item = item)
        }
    }
}

@Composable
private fun ReportItem(item: IField) {
    when (item) {
        is IField.Text -> Field(value = item)
        is IField.Money -> Field(value = item)
        is IField.Double -> Field(value = item)
        is IField.Long -> Field(value = item)
        is IField.Condition -> Field(value = item)
        is IField.List -> Field(value = item)
        is IField.Date -> Field(value = item)
        is IField.DateTime -> Field(value = item)
        is IField.File -> Field(value = item)
        is IField.Photo -> Field(value = item)
    }
}

@Composable
private fun Field(value: IField.Text) {
    var text by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(text = value.value ?: ""))
    }
    ReportText(
        title = value.name,
        required = value.required,
        value = text,
        onValueChange = {
            text = it
            value.value = it.text
        }
    )
}

@Composable
private fun Field(value: IField.Money) {
    var money by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(text = value.value?.toString() ?: ""))
    }
    ReportMoney(
        title = value.name,
        required = value.required,
        value = money,
        onValueChange = {
            money = it
            value.value = it.text.toDoubleOrNull()
        }
    )
}

@Composable
private fun Field(value: IField.Double) {
    var double by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(text = value.value?.toString() ?: ""))
    }
    ReportDouble(
        title = value.name,
        required = value.required,
        value = double,
        onValueChange = {
            double = it
            value.value = it.text.toDoubleOrNull()
        }
    )
}

@Composable
private fun Field(value: IField.Long) {
    var long by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(text = value.value?.toString() ?: ""))
    }
    ReportLong(
        title = value.name,
        required = value.required,
        value = long,
        onValueChange = { newValue ->
            long = newValue
            value.value = newValue.text.toLongOrNull()
        }
    )
}

@Composable
private fun Field(value: IField.Condition) {
    var condition: Boolean by rememberSaveable {
        mutableStateOf(value.value ?: false)
    }
    ReportCondition(
        title = value.name,
        required = value.required,
        value = condition,
        onValueChange = {
            condition = it
            value.value = it
        }
    )
}

@Composable
private fun Field(value: IField.List) {
    val list by rememberSaveable { mutableStateOf(value.value) }

    if (list?.isSingle == true) {
        ReportListSingle(
            title = value.name,
            required = value.required,
            values = list?.values?.toList(),
            onValuesChange = { lastIndex, newIndex ->
                val setNewValue: ArrayList<OrderListItem>.() -> Unit = {
                    if (lastIndex != null)
                        set(
                            index = lastIndex,
                            element = this[lastIndex].copy(isChecked = false)
                        )
                    set(
                        index = newIndex,
                        element = this[newIndex].copy(isChecked = true)
                    )
                }
                value.value?.values?.apply(setNewValue)
                list?.values?.apply(setNewValue)
            }
        )
    } else {
        ReportListMultiple(
            title = value.name,
            required = value.required,
            values = value.value?.values?.toList(),
            onValuesChange = { index ->
                value.value?.values?.apply {
                    val listIem = this[index]
                    set(
                        index = index,
                        element = listIem.copy(isChecked = !listIem.isChecked)
                    )
                }
            }
        )
    }
}

@Composable
private fun Field(value: IField.Date) {
    var date: LocalDate? by rememberSaveable {
        mutableStateOf(value.value)
    }
    ReportDate(
        title = value.name,
        value = date,
        required = value.required,
        onValueChange = {
            date = it
            value.value = it
        }
    )
}

@Composable
private fun Field(value: IField.DateTime) {
    var time: LocalTime? by rememberSaveable { mutableStateOf(value.value?.toLocalTime()) }
    var date: LocalDate? by rememberSaveable { mutableStateOf(value.value?.toLocalDate()) }
    ReportDateTime(
        title = value.name,
        required = value.required,
        date = date,
        time = time,
        onDateChange = {
            date = it
            value.value = LocalDateTime.of(date ?: LocalDate.now(), time ?: LocalTime.now())
        },
        onTimeChange = {
            time = it
            value.value = LocalDateTime.of(date ?: LocalDate.now(), time ?: LocalTime.now())
        }
    )
}

@Composable
private fun Field(value: IField.File) {
    val valueList = rememberSaveable(
        saver = listSaver(save = { it.toList() },
            restore = { mutableStateListOf<Uri>().apply { addAll(it) } })
    ) {
        mutableStateListOf<Uri>().apply { addAll(value.localValue) }
    }
    ReportFiles(
        title = value.name,
        required = value.required,
        serverValue = value.value ?: emptyList(),
        localValue = valueList,
        onAdd = { uri ->
            valueList.add(uri)
            value.localValue.add(uri)
        },
        onRemove = { index ->
            valueList.removeAt(index)
            value.localValue.removeAt(index)
        }
    )
}

@Composable
private fun Field(value: IField.Photo) {
    val valueLocal = rememberSaveable(
        saver = listSaver(
            save = { it.toList() },
            restore = { mutableStateListOf<Uri>().apply { addAll(it) } }
        )
    ) {
        mutableStateListOf<Uri>().apply { addAll(value.localValue) }
    }
    ReportPhotos(
        title = value.name,
        required = value.required,
        serverValue = value.value ?: emptyList(),
        localValue = valueLocal,
        onAdd = { uri ->
            valueLocal.add(uri)
            value.localValue.add(uri)
        },
        onRemove = { index ->
            valueLocal.removeAt(index)
            value.localValue.removeAt(index)
        }
    )
}

@Composable
private fun CompleteButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        MaterialTheme.buttons.Button(
            modifier = Modifier
                .weight(1F)
                .defaultMinSize(minHeight = 56.dp),
            onClick = onClick,
            enabled = true // TODO: Заменить
        ) {
            Text(
                text = stringResource(id = R.string.complete),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}