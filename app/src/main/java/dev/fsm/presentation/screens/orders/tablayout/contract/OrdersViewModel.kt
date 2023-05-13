package dev.fsm.presentation.screens.orders.tablayout.contract

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.fsm.core.BaseViewModel
import dev.fsm.destinations.OrderInfoScreenDestination
import dev.fsm.domain.models.orders.Orders
import dev.fsm.domain.usecase.orders.FetchActualOrdersByPage
import dev.fsm.domain.usecase.orders.FetchNewOrdersByPage
import dev.fsm.domain.utils.Response
import dev.fsm.presentation.screens.orders.models.Orders.Companion.toOrders
import dev.fsm.presentation.screens.orders.tablayout.contract.OrdersContract.Actual
import dev.fsm.presentation.screens.orders.tablayout.contract.OrdersContract.Effect
import dev.fsm.presentation.screens.orders.tablayout.contract.OrdersContract.Intent
import dev.fsm.presentation.screens.orders.tablayout.contract.OrdersContract.New
import dev.fsm.presentation.screens.orders.tablayout.contract.OrdersContract.State
import dev.fsm.presentation.screens.orders.tablayout.contract.OrdersContract.Update
import dev.fsm.presentation.screens.orders.tablayout.models.OrdersFilter
import dev.fsm.presentation.screens.orders.tablayout.models.OrdersFilter.Companion.toDomain
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val newOrders: FetchNewOrdersByPage,
    private val actualOrders: FetchActualOrdersByPage
) : BaseViewModel<State, Intent, Effect>() {

    private var filterParams: OrdersFilter? = null

    override fun createInitialState(): State = State(
        actual = Actual.Idle,
        new = New.Idle,
        update = Update.Idle
    )

    override fun handleIntent(event: Intent): Unit = when (event) {
        is Intent.OrderClick -> setEffect {
            Effect.NavigateTo(
                direction = OrderInfoScreenDestination.invoke(orderId = event.order.id)
            )
        }

        is Intent.Update -> update()
        is Intent.SetFilter -> {
            filterParams = event.params
            if (filterParams != null) {
                setFilter(params = filterParams!!)
            } else {
                getActualOrders()
                getNewOrders()
            }
        }

        is Intent.OpenDrawer -> setEffect { Effect.OpenDrawer }
    }


    init {
        getNewOrders()
        getActualOrders()
    }

    private fun update() {
        viewModelScope.launch(dispatcher) {
            setState { copy(update = Update.Loading) }

            if (filterParams != null) {
                val mappedParams = filterParams!!.toDomain()
                val asyncActualResponse = async { actualOrders.getFiltered(params = mappedParams) }
                val asyncNewResponse = async { newOrders.getFiltered(params = mappedParams) }

                val actualState = asyncActualResponse.await().toStateActual()
                val newState = asyncNewResponse.await().toStateNew()

                setState { copy(actual = actualState) }
                setState { copy(new = newState) }
            } else {
                val asyncActualResponse = async { actualOrders.get() }
                val asyncNewResponse = async { newOrders.get() }

                val actualState = asyncActualResponse.await().toStateActual()
                val newState = asyncNewResponse.await().toStateNew()

                setState { copy(actual = actualState) }
                setState { copy(new = newState) }
            }
            setState { copy(update = Update.Idle) }
        }
    }

    private fun getNewOrders() {
        viewModelScope.launch(dispatcher) {
            setState { copy(new = New.Loading) }
            val newState = newOrders.get().toStateNew()
            setState { copy(new = newState) }
        }
    }

    private fun getActualOrders() {
        viewModelScope.launch(dispatcher) {
            setState { copy(actual = Actual.Loading) }
            val newState = actualOrders.get().toStateActual()
            setState { copy(actual = newState) }
        }
    }

    private fun setFilter(params: OrdersFilter) {
        viewModelScope.launch(dispatcher) {
            setState {
                copy(
                    actual = Actual.Loading,
                    new = New.Loading
                )
            }

            val mappedParams = params.toDomain()
            val responseActualAsync = async { actualOrders.getFiltered(params = mappedParams) }
            val responseNewAsync = async { newOrders.getFiltered(params = mappedParams) }

            val waitedActual = responseActualAsync.await().toStateActual()
            val waitedNew = responseNewAsync.await().toStateNew()

            setState { copy(actual = waitedActual) }
            setState { copy(new = waitedNew) }
        }
    }

    private fun Response<List<Orders>>.toStateNew(): New = when (this) {
        is Response.Error -> New.Failure(exception = exception)
        is Response.Success -> {
            val data = this.data
            if (data != null) New.Loaded(orders = data.map { it.toOrders() })
            else New.Empty
        }
    }

    private fun Response<List<Orders>>.toStateActual(): Actual = when (this) {
        is Response.Error -> Actual.Failure(exception = exception)
        is Response.Success -> {
            val data = this.data
            if (data != null) Actual.Loaded(orders = data.map { it.toOrders() })
            else Actual.Empty
        }
    }
}