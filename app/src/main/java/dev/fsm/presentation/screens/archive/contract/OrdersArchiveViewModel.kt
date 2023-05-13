package dev.fsm.presentation.screens.archive.contract

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.fsm.core.BaseViewModel
import dev.fsm.destinations.OrderInfoScreenDestination
import dev.fsm.domain.usecase.orders.FetchArchiveOrdersByPage
import dev.fsm.domain.utils.Response
import dev.fsm.presentation.screens.orders.models.ArchiveFilter
import dev.fsm.presentation.screens.orders.models.ArchiveFilter.Companion.toDomain
import dev.fsm.presentation.screens.orders.models.Orders.Companion.toOrders
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrdersArchiveViewModel @Inject constructor(
    private val archive: FetchArchiveOrdersByPage
) : BaseViewModel<OrdersArchiveContract.State, OrdersArchiveContract.Intent, OrdersArchiveContract.Effect>() {

    private var archiveFilter: ArchiveFilter? = null

    override fun createInitialState(): OrdersArchiveContract.State = OrdersArchiveContract.State(
        screenState = OrdersArchiveContract.ScreenState.Loading,
        updating = false
    )

    override fun handleIntent(event: OrdersArchiveContract.Intent) {
        when (event) {
            is OrdersArchiveContract.Intent.GetArchiveOrders -> getOrderArchive()
            is OrdersArchiveContract.Intent.OpenDrawer -> setEffect {
                OrdersArchiveContract.Effect.OpenDrawer
            }

            is OrdersArchiveContract.Intent.SetFilter -> {
                archiveFilter = event.params
                if (event.params != null) {
                    getFilteredArchive(params = event.params)
                } else {
                    getOrderArchive()
                }
            }

            is OrdersArchiveContract.Intent.Update -> update()
            is OrdersArchiveContract.Intent.OrderClick -> setEffect {
                OrdersArchiveContract.Effect.Navigate(
                    direction = OrderInfoScreenDestination(orderId = event.orderId)
                )
            }
        }
    }

    init {
        getOrderArchive()
    }

    private fun update() {
        viewModelScope.launch(dispatcher) {
            setState { copy(updating = true) }
            if (archiveFilter != null) {
                val response = archive.getFiltered(params = archiveFilter!!.toDomain())
                when (response) {
                    is Response.Success -> {
                        val data = response.data?.map { it.toOrders() }
                        if (data != null) setState {
                            copy(screenState = OrdersArchiveContract.ScreenState.Loaded(data = data))
                        }
                        else setState {
                            copy(screenState = OrdersArchiveContract.ScreenState.Failure(exception = NullPointerException()))
                        }
                    }

                    is Response.Error -> {
                        setState {
                            copy(screenState = OrdersArchiveContract.ScreenState.Failure(exception = response.exception))
                        }
                    }
                }
            } else {
                val response = archive.get()
                when (response) {
                    is Response.Success -> {
                        val data = response.data?.map { it.toOrders() }
                        if (data != null) setState {
                            copy(screenState = OrdersArchiveContract.ScreenState.Loaded(data = data))
                        }
                        else setState {
                            copy(screenState = OrdersArchiveContract.ScreenState.Failure(exception = NullPointerException()))
                        }
                    }

                    is Response.Error -> setState {
                        copy(screenState = OrdersArchiveContract.ScreenState.Failure(exception = response.exception))
                    }
                }
            }
            setState { copy(updating = false) }
        }
    }

    private fun getOrderArchive() {
        viewModelScope.launch(dispatcher) {
            setState { copy(screenState = OrdersArchiveContract.ScreenState.Loading) }
            val response = archive.get()
            when (response) {
                is Response.Success -> {
                    val data = response.data?.map { it.toOrders() }
                    if (data != null) setState {
                        copy(screenState = OrdersArchiveContract.ScreenState.Loaded(data = data))
                    }
                    else setState {
                        copy(screenState = OrdersArchiveContract.ScreenState.Failure(exception = NullPointerException()))
                    }
                }
                is Response.Error -> setEffect {
                    OrdersArchiveContract.Effect.ShowErrorDialog(exception = response.exception)
                }
            }
        }
    }

    private fun getFilteredArchive(params: ArchiveFilter) {
        viewModelScope.launch(dispatcher) {
            setState { copy(screenState = OrdersArchiveContract.ScreenState.Loading) }
            val response = archive.getFiltered(params = params.toDomain())
            when (response) {
                is Response.Success -> {
                    val data = response.data?.map { it.toOrders() }
                    if (data != null) setState {
                        copy(screenState = OrdersArchiveContract.ScreenState.Loaded(data = data))
                    }
                    else setState {
                        copy(screenState = OrdersArchiveContract.ScreenState.Failure(exception = NullPointerException()))
                    }
                }

                is Response.Error -> {
                    setState {
                        copy(screenState = OrdersArchiveContract.ScreenState.Failure(exception = response.exception))
                    }
                }
            }
        }
    }
}