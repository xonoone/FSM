package dev.fsm.presentation.screens.navigation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.utils.currentDestinationAsState
import dev.fsm.presentation.screens.navigation.contract.DrawerContract
import dev.fsm.presentation.screens.navigation.contract.DrawerViewModel
import dev.fsm.ui.components.indicators.RoundNotification
import dev.fsm.ui.theme.AppTheme
import kotlinx.coroutines.launch

@Composable
fun Drawer(
    viewModel: DrawerViewModel,
    drawerState: DrawerState,
    navController: NavHostController,
    content: @Composable () -> Unit
) {
    val state = viewModel.state.collectAsState().value
    val currentDestination by navController.currentDestinationAsState()
    val drawerItems = listOf(
        DrawerItem.Orders,
        DrawerItem.Archive,
        DrawerItem.Profile
    )
    val currentDrawerItem = drawerItems.find {
        it.direction == currentDestination
    }

    NavigationDrawer(
        drawerState = drawerState,
        state = state,
        values = drawerItems,
        selected = currentDrawerItem,
        onSelectChange = { item ->
            val currentRoute = currentDestination?.route
            item.direction?.let { direction ->
                navController.navigate(direction) {
                    currentRoute?.let { route ->
                        popUpTo(route = route) {
                            inclusive = true
                        }
                    }
                }
            }
        },
        content = content
    )
}

@Composable
fun NavigationDrawer(
    drawerState: DrawerState,
    state: DrawerContract.State,
    values: List<DrawerItem>,
    selected: DrawerItem?,
    onSelectChange: (DrawerItem) -> Unit,
    content: @Composable () -> Unit
) {
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerShape = RectangleShape,
                drawerContainerColor = MaterialTheme.colorScheme.background,
                modifier = Modifier.padding(end = 72.dp)
            ) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    item {
                        state.user.Header()
                    }
                    items(
                        items = values,
                        key = { it.title }
                    ) { item ->
                        state.notifications.DrawerItem(
                            drawerItem = item,
                            selected = selected == item,
                            onClick = {
                                if (selected != item) {
                                    scope.launch { if (drawerState.isOpen) drawerState.close() }
                                    onSelectChange.invoke(item)
                                }
                            }
                        )
                    }
                }
            }
        },
        content = content
    )
}

@Composable
fun DrawerContract.User.Header(): Unit = when (this) {
    is DrawerContract.User.Loading -> DrawerHeaderShimmer()
    is DrawerContract.User.Loaded -> DrawerHeader(
        profileImageUrl = data.imageUrl,
        firstName = data.firstName,
        lastName = data.lastName,
        middleName = data.middleName,
        login = data.login
    )

    is DrawerContract.User.Failure -> Unit
}

@Composable
private fun DrawerContract.Notifications.DrawerItem(
    drawerItem: DrawerItem,
    selected: Boolean,
    onClick: () -> Unit
) {
    NavigationDrawerItem(
        icon = {
            Icon(
                modifier = Modifier.size(20.dp),
                imageVector = drawerItem.icon,
                contentDescription = stringResource(id = drawerItem.title)
            )
        },
        label = { DrawerLabel(drawerItem = drawerItem) },
        selected = selected,
        onClick = onClick,
        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
        colors = NavigationDrawerItemDefaults.colors(
            unselectedContainerColor = Color.Transparent,
            selectedContainerColor = Color.Transparent,
            selectedTextColor = MaterialTheme.colorScheme.secondary,
            unselectedTextColor = MaterialTheme.colorScheme.onBackground,
            selectedIconColor = MaterialTheme.colorScheme.secondary,
            unselectedIconColor = MaterialTheme.colorScheme.tertiary
        )
    )
}

@Composable
private fun DrawerContract.Notifications.DrawerLabel(drawerItem: DrawerItem) {
    Row {
        Text(
            text = stringResource(id = drawerItem.title),
            style = MaterialTheme.typography.bodyMedium
        )
        if (drawerItem is DrawerItem.Orders
            && this@DrawerLabel is DrawerContract.Notifications.Loaded
            && count > 0
        ) {
            Spacer(modifier = Modifier.weight(1f))
            RoundNotification(notifications = count)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NavigationPreview() {
    val drawerState = rememberDrawerState(DrawerValue.Open)
    val scope = rememberCoroutineScope()
    var itemText by rememberSaveable { mutableStateOf("Empty") }
    val context = LocalContext.current

    AppTheme {
        NavigationDrawer(
            drawerState = drawerState,
            state = DrawerContract.State(
                user = DrawerContract.User.Loading,
                notifications = DrawerContract.Notifications.Loading
            ),
            values = listOf(
                DrawerItem.Orders,
                DrawerItem.Archive,
                DrawerItem.Profile
            ),
            onSelectChange = { pickedItem ->
                itemText = context.getString(pickedItem.title)
            },
            content = {
                Column {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = Color.LightGray)
                            .clickable { scope.launch { drawerState.open() } },
                        text = itemText,
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        fontSize = 36.sp
                    )
                    Text(
                        modifier = Modifier.align(Alignment.End),
                        text = drawerState.currentValue.name,
                    )
                }
            },
            selected = DrawerItem.Orders
        )
    }
}