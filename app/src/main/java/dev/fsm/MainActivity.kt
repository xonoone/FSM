package dev.fsm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.manualcomposablecalls.composable
import com.ramcosta.composedestinations.navigation.navigate
import dagger.hilt.android.AndroidEntryPoint
import dev.fsm.destinations.LoginScreenDestination
import dev.fsm.destinations.OrdersArchiveScreenDestination
import dev.fsm.destinations.OrdersScreenDestination
import dev.fsm.destinations.ScreenUserDestination
import dev.fsm.presentation.screens.archive.view.OrdersArchiveScreen
import dev.fsm.presentation.screens.login.contract.IsLoggedViewModel
import dev.fsm.presentation.screens.navigation.contract.DrawerContract
import dev.fsm.presentation.screens.navigation.contract.DrawerViewModel
import dev.fsm.presentation.screens.navigation.view.Drawer
import dev.fsm.presentation.screens.orders.tablayout.view.OrdersScreen
import dev.fsm.presentation.screens.user.info.view.ScreenUser
import dev.fsm.ui.theme.AppTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            val navController = rememberNavController()
            val isLoggedViewModel: IsLoggedViewModel = hiltViewModel()
            val drawerViewModel: DrawerViewModel = hiltViewModel()
            val startRoute = if (!isLoggedViewModel.isLogged) LoginScreenDestination
            else OrdersScreenDestination
            val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

            AppTheme {
                DestinationsNavHost(
                    startRoute = startRoute,
                    navGraph = NavGraphs.root,
                    navController = navController
                ) {
                    composable(OrdersScreenDestination) {
                        Drawer(
                            drawerState = drawerState,
                            navController = navController,
                            viewModel = drawerViewModel
                        ) {
                            OrdersScreen(
                                navigator = destinationsNavigator,
                                drawerState = drawerState,
                                drawerViewModel = drawerViewModel
                            )
                        }
                    }
                    composable(OrdersArchiveScreenDestination) {
                        Drawer(
                            drawerState = drawerState,
                            navController = navController,
                            viewModel = drawerViewModel
                        ) {
                            OrdersArchiveScreen(
                                navigator = destinationsNavigator,
                                drawerState = drawerState
                            )
                        }
                    }
                    composable(ScreenUserDestination) {
                        Drawer(
                            drawerState = drawerState,
                            navController = navController,
                            viewModel = drawerViewModel
                        ) {
                            ScreenUser(
                                navigator = destinationsNavigator,
                                drawerState = drawerState
                            )
                        }
                    }
                }
                ShowLoginWhenLoggedOut(
                    viewModel = isLoggedViewModel,
                    navController = navController
                )
            }
        }
    }

    @Composable
    private fun ShowLoginWhenLoggedOut(
        viewModel: IsLoggedViewModel,
        navController: NavHostController
    ) {
        val currentDestination by navController.appCurrentDestinationAsState()
        val isLoggedIn by viewModel.isLoggedFlow.collectAsState()
        val isNeedGoToLoginScreen = !isLoggedIn
                && currentDestination != null
                && currentDestination != LoginScreenDestination

        if (isNeedGoToLoginScreen) {
            navController.navigate(LoginScreenDestination) {
                popUpTo(navController.graph.id) {
                    inclusive = true
                }
            }
        }
    }
}