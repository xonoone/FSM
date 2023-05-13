package dev.fsm.presentation.screens.orders.tablayout.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import dev.fsm.R
import dev.fsm.destinations.ErrorDialogDestination
import dev.fsm.presentation.intents.menu.OrdersAppBarIntent
import dev.fsm.presentation.screens.navigation.contract.DrawerContract
import dev.fsm.presentation.screens.navigation.contract.DrawerViewModel
import dev.fsm.presentation.screens.orders.tablayout.contract.OrdersContract
import dev.fsm.presentation.screens.orders.tablayout.contract.OrdersContract.Actual
import dev.fsm.presentation.screens.orders.tablayout.contract.OrdersContract.Intent
import dev.fsm.presentation.screens.orders.tablayout.contract.OrdersContract.New
import dev.fsm.presentation.screens.orders.tablayout.contract.OrdersContract.State
import dev.fsm.presentation.screens.orders.tablayout.contract.OrdersViewModel
import dev.fsm.presentation.screens.orders.tablayout.models.OrdersFilter
import dev.fsm.presentation.screens.orders.tablayout.models.TabItem
import dev.fsm.ui.components.app_bars.OrdersAppBar
import dev.fsm.ui.components.backdrops.OrdersFilterContent
import dev.fsm.ui.theme.AppTheme
import dev.fsm.utils.ILocalizeExceptionMessage.LocalizeExceptionMessage.localized
import kotlinx.coroutines.launch

@RootNavGraph(start = true)
@Destination
@Composable
fun OrdersScreen(
    viewModel: OrdersViewModel = hiltViewModel(),
    drawerViewModel: DrawerViewModel,
    navigator: DestinationsNavigator,
    drawerState: DrawerState
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()
    drawerViewModel.handleIntent(DrawerContract.Intent.UpdateNotifications)

    OrdersScreen(
        state = state,
        onIntent = viewModel::handleIntent,
        onDrawerIntentNotification = drawerViewModel::handleIntent,
        onDrawerIntentUser = drawerViewModel::handleIntent
    )

    LaunchedEffect(state) {
        if (state.new is New.Failure) navigator.navigate(
            direction = ErrorDialogDestination.invoke(
                message = (state.new as New.Failure).exception.localized(context = context)
            )
        )
        if (state.actual is Actual.Failure) navigator.navigate(
            direction = ErrorDialogDestination.invoke(
                message = (state.actual as Actual.Failure).exception.localized(context = context)
            )
        )
    }
    LaunchedEffect(key1 = true) {
        viewModel.effect.collect { event ->
            when (event) {
                is OrdersContract.Effect.NavigateTo -> navigator.navigate(
                    direction = event.direction
                )

                is OrdersContract.Effect.OpenDrawer -> if (drawerState.isClosed) drawerState.open()
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun OrdersScreen(
    state: State,
    onIntent: (Intent) -> Unit,
    onDrawerIntentNotification: (DrawerContract.Intent) -> Unit,
    onDrawerIntentUser: (DrawerContract.Intent) -> Unit
) {
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()
    val pagerState = rememberPagerState()
    var filter: OrdersFilter? by rememberSaveable { mutableStateOf(null) }
    val swipeRefreshState = rememberSwipeRefreshState(
        isRefreshing = state.update is OrdersContract.Update.Loading
    )

    val tabs = mutableListOf(
        TabItem.Actual(
            state = state.actual,
            intent = onIntent
        ),
        TabItem.New(
            state = state.new,
            intent = onIntent
        )
    )

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        topBar = {
            OrdersAppBar(
                filterCount = filter?.let {
                    var count = 0
                    if (it.date != null) count++
                    if (it.isSortedEarly != null) count++
                    count
                },
                title = stringResource(id = R.string.orders),
                action = { action ->
                    when (action) {
                        is OrdersAppBarIntent.OpenFilter -> scope.launch {
                            if (scaffoldState.bottomSheetState.hasExpandedState)
                                scaffoldState.bottomSheetState.expand()
                        }

                        is OrdersAppBarIntent.OpenNavigationDrawer ->
                            onIntent.invoke(Intent.OpenDrawer)
                    }
                }
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                Tabs(
                    tabs = tabs,
                    pagerState = pagerState
                )
                SwipeRefresh(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1F),
                    state = swipeRefreshState,
                    onRefresh = {
                        onIntent.invoke(Intent.Update)
                        onDrawerIntentUser.invoke(DrawerContract.Intent.UpdateUser)
                        onDrawerIntentNotification.invoke(DrawerContract.Intent.UpdateNotifications)
                    },
                    content = {
                        TabsContent(
                            modifier = Modifier.fillMaxSize(),
                            tabs = tabs,
                            pagerState = pagerState
                        )
                    }
                )
            }
        },
        sheetContent = {
            OrdersFilterContent(
                value = filter,
                onValueChange = { newFilter ->
                    filter = newFilter
                    onIntent.invoke(Intent.SetFilter(params = filter))
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
}

@Preview(showBackground = true)
@Composable
fun PreviewScreenOrders() {
    AppTheme {
        OrdersScreen(
            state = State(
                actual = Actual.Failure(exception = Exception("Some Error")),
                new = New.Failure(exception = Exception("Some Failed Message")),
                update = OrdersContract.Update.Idle
            ),
            onIntent = {},
            onDrawerIntentNotification = {},
            onDrawerIntentUser = {}
        )
    }
}