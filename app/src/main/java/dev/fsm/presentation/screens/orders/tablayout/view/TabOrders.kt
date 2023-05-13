package dev.fsm.presentation.screens.orders.tablayout.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.fsm.domain.models.order.Status
import dev.fsm.presentation.screens.orders.models.Orders
import dev.fsm.presentation.screens.orders.tablayout.contract.OrdersContract
import dev.fsm.ui.components.orders.OrdersCard
import dev.fsm.ui.components.states.EmptyScreen
import dev.fsm.ui.components.states.LoadingScreen
import dev.fsm.ui.theme.AppTheme
import dev.fsm.ui.theme.spacing
import java.time.LocalDateTime
import java.util.UUID

@Composable
fun TabOrders(
    state: OrdersContract.Actual,
    intent: (OrdersContract.Intent) -> Unit
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        when (state) {
            is OrdersContract.Actual.Loading ->
                LoadingScreen(modifier = Modifier.fillMaxSize())

            is OrdersContract.Actual.Loaded ->
                Loaded(orders = state.orders, onCardClick = intent)

            else -> EmptyScreen()
        }
    }
}

@Composable
fun TabOrders(
    state: OrdersContract.New,
    intent: (OrdersContract.Intent) -> Unit
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        when (state) {
            is OrdersContract.New.Loading ->
                LoadingScreen(modifier = Modifier.fillMaxSize())

            is OrdersContract.New.Loaded ->
                Loaded(orders = state.orders, onCardClick = intent)

            else -> EmptyScreen()
        }
    }
}

@Composable
private fun Loaded(
    orders: List<Orders>,
    onCardClick: (OrdersContract.Intent.OrderClick) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(MaterialTheme.spacing.medium),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium)
    ) {
        items(
            items = orders,
            key = { it.id }
        ) { ordersItem ->
            val cardClick = remember {
                { onCardClick.invoke(OrdersContract.Intent.OrderClick(order = ordersItem)) }
            }
            OrdersCard(
                order = ordersItem,
                onClick = cardClick
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ScreenOrdersPreview() {
    AppTheme(darkTheme = false) {
        TabOrders(
            state = OrdersContract.Actual.Loaded(
                orders = arrayListOf<Orders>().apply {
                    repeat(20) {
                        add(
                            Orders(
                                id = UUID.randomUUID().toString(),
                                name = it.toString(),
                                type = "Монтаж двери $it",
                                status = Status(code = "NEW", name = "Новая"),
                                address = "г. Тольятти ул. Тополиная д. 10",
                                isChecked = true,
                                date = LocalDateTime.now(),
                                duration = LocalDateTime.now().withHour(1)
                            )
                        )
                    }
                }
            ),
            intent = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ScreenOrdersEmptyPreview() {
    var state: OrdersContract.Actual = remember {
        OrdersContract.Actual.Empty
    }
    AppTheme(darkTheme = false) {
        TabOrders(
            state = state,
            intent = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ScreenOrdersFailurePreview() {
    AppTheme(darkTheme = false) {
        TabOrders(
            state = OrdersContract.Actual.Failure(exception = Exception()),
            intent = {}
        )
    }
}