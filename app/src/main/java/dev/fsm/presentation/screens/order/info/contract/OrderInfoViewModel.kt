package dev.fsm.presentation.screens.order.info.contract

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.fsm.core.BaseViewModel
import dev.fsm.destinations.OrdersScreenDestination
import dev.fsm.destinations.ReportScreenDestination
import dev.fsm.domain.models.cancel.CancelOrderForReasonParams
import dev.fsm.domain.models.order.OrderChangeStatusParams
import dev.fsm.domain.models.order.OrderFetchParams
import dev.fsm.domain.usecase.cancel.CancelOrderForReasonById
import dev.fsm.domain.usecase.order.ChangeOrderStatusById
import dev.fsm.domain.usecase.order.FetchOrderInfoById
import dev.fsm.domain.utils.OrderStatuses
import dev.fsm.domain.utils.Response
import dev.fsm.presentation.screens.order.info.models.CancelOrderReason.Companion.toCancelOrderReason
import dev.fsm.presentation.screens.order.info.models.OrderInfo.Companion.toOrderInfo
import dev.fsm.utils.statuses.Statuses
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderInfoViewModel @Inject constructor(
    private val orderInfo: FetchOrderInfoById,
    private val cancelOrder: CancelOrderForReasonById,
    private val changeOrderStatus: ChangeOrderStatusById
) : BaseViewModel<OrderContract.State, OrderContract.Intent, OrderContract.Effect>() {

    override fun createInitialState(): OrderContract.State = OrderContract.State(
        order = OrderContract.Order.Loading,
        getCancelReasons = OrderContract.GetCancelReasons.Loading,
        cancelOrder = OrderContract.CancelOrder.Idle
    )

    override fun handleIntent(event: OrderContract.Intent) {
        when (event) {
            is OrderContract.Intent.GetOrder -> getOrder(orderId = event.id)
            is OrderContract.Intent.CancelOrder -> cancelOrder(
                orderId = event.orderId,
                reasonId = event.reasonId,
                note = event.note
            )
            is OrderContract.Intent.RejectOrder -> rejectOrder(orderId = event.id)

            is OrderContract.Intent.NegativeClick -> {
                if (event.currentStatus is Statuses.New) {
                    setEffect { OrderContract.Effect.ShowRejectDialog }
                } else {
                    setEffect { OrderContract.Effect.ShowReasonsDialog }
                    if (currentState.getCancelReasons !is OrderContract.GetCancelReasons.Success) getReasons()
                }
            }

            is OrderContract.Intent.PositiveClick -> {
                when (event.currentStatus) {
                    is Statuses.New -> {
                        changeStatus(
                            orderId = event.orderId,
                            statusCode = OrderStatuses.ACCEPTED
                        )
                    }

                    is Statuses.Accepted -> {
                        changeStatus(
                            orderId = event.orderId,
                            statusCode = OrderStatuses.INROAD
                        )
                    }

                    is Statuses.InRoad -> {
                        changeStatus(
                            orderId = event.orderId,
                            statusCode = OrderStatuses.ACTIVE
                        )
                    }

                    is Statuses.Active -> setEffect {
                        OrderContract.Effect.Navigate(
                            direction = ReportScreenDestination(orderId = event.orderId)
                        )
                    }

                    else -> Unit
                }
            }

            is OrderContract.Intent.OnBackClick -> setEffect {
                OrderContract.Effect.NavigateBackStack(direction = OrdersScreenDestination)
            }

        }
    }

    private fun rejectOrder(orderId: String) {
        viewModelScope.launch(dispatcher) {
            val response = changeOrderStatus.changeStatus(
                params = OrderChangeStatusParams(
                    orderId = orderId,
                    statusCode = Statuses.Rejected.code
                )
            )
            when (response) {
                is Response.Success -> {
                    setEffect {
                        OrderContract.Effect.ShowToast(message = "Вы отклонили заявку")
                    }
                }

                is Response.Error -> setEffect {
                    OrderContract.Effect.ShowErrorDialog(exception = response.exception)
                }
            }
        }
    }

    private fun changeStatus(orderId: String, statusCode: String) {
        viewModelScope.launch(dispatcher) {
            val response = changeOrderStatus.changeStatus(
                params = OrderChangeStatusParams(
                    orderId = orderId,
                    statusCode = statusCode
                )
            )
            when (response) {
                is Response.Error -> setEffect {
                    OrderContract.Effect.ShowErrorDialog(exception = response.exception)
                }

                is Response.Success -> setEffect {
                    OrderContract.Effect.ShowToast(message = "Вы сменили статус заявки")
                }
            }
        }
    }

    private fun getOrder(orderId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            setState { copy(order = OrderContract.Order.Loading) }
            val response = orderInfo.get(params = OrderFetchParams(orderId = orderId))
            when (response) {
                is Response.Success -> {
                    val order = response.data?.toOrderInfo()
                    if (order != null) {
                        setState { copy(order = OrderContract.Order.Loaded(order = order)) }
                    } else {
                        setState {
                            copy(
                                order = OrderContract.Order.Failure(
                                    exception = NullPointerException()
                                )
                            )
                        }
                    }
                }

                is Response.Error -> setState {
                    copy(order = OrderContract.Order.Failure(exception = response.exception))
                }
            }
        }
    }

    private fun getReasons() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = cancelOrder.getReasons()
            setState { copy(getCancelReasons = OrderContract.GetCancelReasons.Loading) }
            when (response) {
                is Response.Error -> setEffect {
                    OrderContract.Effect.ShowErrorDialog(response.exception)
                }

                is Response.Success -> {
                    val data = response.data
                    if (data != null) {
                        setState {
                            copy(getCancelReasons = OrderContract.GetCancelReasons.Success(
                                data = data.map { it.toCancelOrderReason() }
                            ))
                        }
                    } else {
                        setEffect {
                            OrderContract.Effect.ShowErrorDialog(exception = NullPointerException())
                        }
                    }
                }
            }
        }
    }

    private fun cancelOrder(orderId: String, reasonId: String, note: String? = null) {
        viewModelScope.launch(Dispatchers.IO) {
            setState { copy(cancelOrder = OrderContract.CancelOrder.Loading) }
            val response = cancelOrder.cancel(
                params = CancelOrderForReasonParams(
                    orderId = orderId,
                    reasonId = reasonId,
                    note = note
                )
            )
            when (response) {
                is Response.Error -> setEffect {
                    OrderContract.Effect.ShowErrorDialog(exception = response.exception)
                }

                is Response.Success -> setEffect {
                    OrderContract.Effect.ShowToast(message = "Вы отменили заявку")
                }
            }
        }
    }
}