package dev.fsm.presentation.screens.orders.tablayout.models

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import dev.fsm.R
import dev.fsm.presentation.screens.orders.tablayout.contract.OrdersContract
import dev.fsm.presentation.screens.orders.tablayout.view.TabOrders

private typealias ComposableFun = @Composable () -> Unit

sealed class TabItem(@StringRes val title: Int, val screen: ComposableFun) {
    data class Actual(
        val state: OrdersContract.Actual,
        val intent: (OrdersContract.Intent) -> Unit
    ) : TabItem(
        title = R.string.tab_actual,
        screen = { TabOrders(state = state, intent = intent) }
    )

    data class New(
        val state: OrdersContract.New,
        val intent: (OrdersContract.Intent) -> Unit
    ) : TabItem(
        title = R.string.tab_new,
        screen = { TabOrders(state = state, intent = intent) }
    )
}