package dev.fsm.presentation.screens.archive.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import dev.fsm.R
import dev.fsm.destinations.ErrorDialogDestination
import dev.fsm.presentation.intents.menu.OrdersAppBarIntent
import dev.fsm.presentation.screens.archive.contract.OrdersArchiveContract
import dev.fsm.presentation.screens.archive.contract.OrdersArchiveViewModel
import dev.fsm.presentation.screens.orders.models.ArchiveFilter
import dev.fsm.ui.components.app_bars.OrdersAppBar
import dev.fsm.ui.components.backdrops.FilterArchive
import dev.fsm.ui.components.orders.OrdersCard
import dev.fsm.ui.components.states.EmptyScreen
import dev.fsm.ui.components.states.LoadingScreen
import dev.fsm.ui.theme.spacing
import dev.fsm.utils.ILocalizeExceptionMessage.LocalizeExceptionMessage.localized
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun OrdersArchiveScreen(
    viewModel: OrdersArchiveViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
    drawerState: DrawerState
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()
    val scaffoldState = rememberBottomSheetScaffoldState()
    val scope = rememberCoroutineScope()
    var filter: ArchiveFilter? by rememberSaveable { mutableStateOf(null) }

    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = state.updating)

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        topBar = {
            OrdersAppBar(
                filterCount = filter?.let {
                    var count = 0
                    if (it.status != null) count++
                    if (it.isSortedEarly != null) count++
                    if (it.dateFrom != null || it.dateTo != null) count++
                    count
                },
                title = stringResource(id = R.string.archive),
                action = { event ->
                    when (event) {
                        is OrdersAppBarIntent.OpenFilter -> scope.launch {
                            if (scaffoldState.bottomSheetState.hasExpandedState)
                                scaffoldState.bottomSheetState.expand()
                        }

                        is OrdersAppBarIntent.OpenNavigationDrawer ->
                            viewModel.handleIntent(event = OrdersArchiveContract.Intent.OpenDrawer)
                    }
                }
            )
        },
        content = {
            SwipeRefresh(
                modifier = Modifier
                    .fillMaxSize(),
                state = swipeRefreshState,
                onRefresh = { viewModel.handleIntent(OrdersArchiveContract.Intent.Update) },
                content = {
                    OrdersArchiveContent(
                        screenState = state.screenState,
                        onIntent = viewModel::handleIntent
                    )
                }
            )
        },
        sheetContent = {
            FilterArchive(
                value = filter,
                onValueChange = { newFilter ->
                    filter = newFilter
                    viewModel.handleIntent(
                        OrdersArchiveContract.Intent.SetFilter(
                            params = filter
                        )
                    )
                    scope.launch {
                        if (scaffoldState.bottomSheetState.hasExpandedState)
                            scaffoldState.bottomSheetState.partialExpand()
                    }
                }
            )
        },
        sheetSwipeEnabled = true,
        sheetPeekHeight = 0.dp,
        containerColor = Color.Transparent
    )

    LaunchedEffect(key1 = true) {
        viewModel.effect.collect { event ->
            when (event) {
                is OrdersArchiveContract.Effect.Navigate -> navigator.navigate(event.direction)
                is OrdersArchiveContract.Effect.OpenDrawer -> if (drawerState.isClosed) drawerState.open()
                is OrdersArchiveContract.Effect.ShowErrorDialog -> navigator.navigate(
                    ErrorDialogDestination.invoke(message = event.exception.localized(context))
                )

            }
        }
    }
}

@Composable
private fun OrdersArchiveContent(
    screenState: OrdersArchiveContract.ScreenState,
    onIntent: (OrdersArchiveContract.Intent) -> Unit
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        when (screenState) {
            is OrdersArchiveContract.ScreenState.Loading -> LoadingScreen()
            is OrdersArchiveContract.ScreenState.Loaded -> {
                LazyColumn(
                    contentPadding = PaddingValues(MaterialTheme.spacing.medium),
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium)
                ) {
                    items(
                        items = screenState.data,
                        key = { it.id }
                    ) { ordersItem ->
                        val cardClick = remember {
                            {
                                onIntent.invoke(
                                    OrdersArchiveContract.Intent.OrderClick(
                                        orderId = ordersItem.id
                                    )
                                )
                            }
                        }
                        OrdersCard(
                            order = ordersItem,
                            onClick = cardClick
                        )
                    }
                }
            }
            is OrdersArchiveContract.ScreenState.Failure -> EmptyScreen()
        }
    }
}