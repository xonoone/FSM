package dev.fsm.presentation.screens.order.info.view

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import dev.fsm.R
import dev.fsm.destinations.ErrorDialogDestination
import dev.fsm.destinations.InfoDialogDestination
import dev.fsm.destinations.OrdersScreenDestination
import dev.fsm.destinations.ReportScreenDestination
import dev.fsm.presentation.model.order.IField
import dev.fsm.presentation.screens.order.info.contract.OrderContract
import dev.fsm.presentation.screens.order.info.contract.OrderInfoViewModel
import dev.fsm.presentation.screens.order.info.models.OrderInfo
import dev.fsm.presentation.screens.order.info.view.OrderSteps.Companion.toStep
import dev.fsm.ui.components.app_bars.PageAppBar
import dev.fsm.ui.components.dialogs.ReasonDialog
import dev.fsm.ui.components.dialogs.RejectDialog
import dev.fsm.ui.components.order.info.OrderAddress
import dev.fsm.ui.components.order.info.OrderClientContacts
import dev.fsm.ui.components.order.info.OrderDate
import dev.fsm.ui.components.order.info.OrderDateField
import dev.fsm.ui.components.order.info.OrderDateTimeField
import dev.fsm.ui.components.order.info.OrderDescription
import dev.fsm.ui.components.order.info.OrderDoubleField
import dev.fsm.ui.components.order.info.OrderFilesField
import dev.fsm.ui.components.order.info.OrderLinkField
import dev.fsm.ui.components.order.info.OrderList
import dev.fsm.ui.components.order.info.OrderLongField
import dev.fsm.ui.components.order.info.OrderMoneyField
import dev.fsm.ui.components.order.info.OrderPhotoField
import dev.fsm.ui.components.order.info.OrderTextField
import dev.fsm.ui.components.states.LoadingScreen
import dev.fsm.ui.theme.buttons
import dev.fsm.ui.theme.corners
import dev.fsm.ui.theme.spacing
import dev.fsm.utils.ILocalizeExceptionMessage.LocalizeExceptionMessage.localized
import dev.fsm.utils.statuses.Statuses

@Destination
@Composable
fun OrderInfoScreen(
    viewModel: OrderInfoViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
    orderId: String
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()
    // Dialogs
    val dialogState = rememberMaterialDialogState()
    val dialogRejectState = rememberMaterialDialogState()

    ReasonDialog(
        state = state.getCancelReasons,
        dialogState = dialogState,
        onConfirm = { index, note ->
            val listReasons =
                (state.getCancelReasons as OrderContract.GetCancelReasons.Success).data.map { it.id }
            viewModel.handleIntent(
                event = OrderContract.Intent.CancelOrder(
                    orderId = orderId,
                    reasonId = listReasons[index],
                    note = note
                )
            )
        },
        onCancel = { dialogState.hide() }
    )

    RejectDialog(
        dialogState = dialogRejectState,
        onIntent = {
            viewModel.handleIntent(event = OrderContract.Intent.RejectOrder(id = orderId))
        }
    )

    OrderInfoContent(
        modifier = Modifier.fillMaxSize(),
        orderId = orderId,
        state = state,
        onIntent = viewModel::handleIntent
    )

    LaunchedEffect(state) {
        when (state.order) {
            is OrderContract.Order.Loading -> viewModel.handleIntent(
                event = OrderContract.Intent.GetOrder(id = orderId)
            )

            is OrderContract.Order.Failure -> {
                val exception = (state.order as OrderContract.Order.Failure).exception
                navigator.navigate(
                    ErrorDialogDestination.invoke(exception.localized(context = context))
                )
            }

            else -> Unit
        }
    }

    LaunchedEffect(key1 = true) {
        viewModel.effect.collect { event ->
            when (event) {
                is OrderContract.Effect.NavigateBackStack -> navigator.navigateUp()

                is OrderContract.Effect.Navigate -> navigator.navigate(
                    ReportScreenDestination.invoke(orderId = orderId)
                )

                is OrderContract.Effect.NavigateWithPopUp -> {
                    navigator.navigate(event.direction) {
                        popUpTo(OrdersScreenDestination.route) {
                            inclusive = true
                        }
                    }
                }

                is OrderContract.Effect.ShowErrorDialog -> navigator.navigate(
                    ErrorDialogDestination.invoke(message = event.exception.localized(context))
                )

                is OrderContract.Effect.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                    navigator.navigate(OrdersScreenDestination.route) {
                        popUpTo(OrdersScreenDestination.route) {
                            inclusive = true
                        }
                    }
                }

                is OrderContract.Effect.ShowReasonsDialog ->
                    if (!dialogState.showing) dialogState.show()

                is OrderContract.Effect.ShowRejectDialog ->
                    if (!dialogRejectState.showing) dialogRejectState.show()
            }
        }
    }
}

@Composable
private fun OrderInfoContent(
    modifier: Modifier,
    orderId: String,
    state: OrderContract.State,
    onIntent: (OrderContract.Intent) -> Unit
) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.background
    ) {
        when (val order = state.order) {
            is OrderContract.Order.Failure -> Unit
            is OrderContract.Order.Loaded -> Content(
                order = order.order,
                onBackPressed = { onIntent.invoke(OrderContract.Intent.OnBackClick) },
                onNegativeClick = {
                    onIntent.invoke(
                        OrderContract.Intent.NegativeClick(
                            orderId = orderId,
                            currentStatus = order.order.status
                        )
                    )
                },
                onPositiveClick = {
                    onIntent.invoke(
                        OrderContract.Intent.PositiveClick(
                            orderId = orderId,
                            currentStatus = order.order.status
                        )
                    )
                }
            )

            is OrderContract.Order.Loading -> LoadingScreen()
        }
    }
}

@Composable
private fun Content(
    order: OrderInfo,
    onBackPressed: () -> Unit,
    onNegativeClick: () -> Unit,
    onPositiveClick: () -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            PageAppBar(
                title = stringResource(id = R.string.order) + " №${order.name}",
                onBackPressed = onBackPressed
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                Content(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1F),
                    order = order
                )
                order.status.toStep()?.let { step ->
                    Buttons(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(MaterialTheme.spacing.medium),
                        step = step,
                        onNegativeClick = onNegativeClick,
                        onPositiveClick = onPositiveClick
                    )
                }
            }
        }
    )
}

@Composable
private fun Content(
    modifier: Modifier = Modifier,
    order: OrderInfo
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
        contentPadding = PaddingValues(MaterialTheme.spacing.medium)
    ) {
        item {
            OrderDescription(description = order.description)
        }
        item {
            OrderDate(
                date = order.plannedDateTime,
                duration = order.duration
            )
        }
        item {
            OrderAddress(address = order.address?.fullAddress)
        }
        item {
            OrderClientContacts(contacts = order.contacts)
        }
        items(
            items = order.fields,
            key = { it.id }
        ) { field: IField ->
            if (field.visible) {
                when (field) {
                    is IField.Text -> OrderTextField(
                        title = field.name,
                        content = field.value
                    )

                    is IField.Long -> OrderLongField(
                        title = field.name,
                        content = field.value
                    )

                    is IField.Double -> OrderDoubleField(
                        title = field.name,
                        content = field.value
                    )

                    is IField.Money -> OrderMoneyField(
                        title = field.name,
                        content = field.value
                    )

                    is IField.Date -> OrderDateField(
                        title = field.name,
                        content = field.value
                    )

                    is IField.DateTime -> OrderDateTimeField(
                        title = field.name,
                        content = field.value
                    )

                    is IField.File -> OrderFilesField(
                        title = field.name,
                        content = field.value
                    )

                    is IField.Photo -> OrderPhotoField(
                        title = field.name,
                        content = field.value
                    )

                    is IField.Link -> OrderLinkField(
                        title = field.name,
                        link = field.value
                    )

                    is IField.List -> OrderList(
                        title = field.name,
                        content = field.value
                    )
                }
            }
        }
    }
}

@Composable
private fun Buttons(
    modifier: Modifier = Modifier,
    step: OrderSteps,
    enabled: Boolean = true,
    onNegativeClick: () -> Unit,
    onPositiveClick: () -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextButton(
            modifier = Modifier
                .weight(1F)
                .defaultMinSize(minHeight = 56.dp),
            onClick = onNegativeClick,
            enabled = enabled,
            shape = MaterialTheme.corners.mediumRoundCornerShape()
        ) {
            Text(
                text = stringResource(id = step.negativeName),
                style = MaterialTheme.typography.titleMedium
            )
        }
        MaterialTheme.spacing.SpacerMedium()
        MaterialTheme.buttons.Button(
            modifier = Modifier
                .weight(1F)
                .defaultMinSize(minHeight = 56.dp),
            onClick = onPositiveClick,
            enabled = enabled
        ) {
            Text(
                text = stringResource(id = step.positiveName),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

private sealed class OrderSteps(
    @StringRes val positiveName: Int,
    @StringRes val negativeName: Int = R.string.cancel
) {
    object Accept : OrderSteps(positiveName = R.string.accept, negativeName = R.string.reject)
    object LeaveForWork : OrderSteps(positiveName = R.string.leave_for_work)
    object GetStarted : OrderSteps(positiveName = R.string.get_started)
    object Report : OrderSteps(positiveName = R.string.report)

    companion object {
        fun Statuses.toStep(): OrderSteps? = when (this) {
            is Statuses.Accepted -> LeaveForWork
            is Statuses.Active -> Report
            is Statuses.InRoad -> GetStarted
            is Statuses.New -> Accept
            else -> null
        }
    }
}