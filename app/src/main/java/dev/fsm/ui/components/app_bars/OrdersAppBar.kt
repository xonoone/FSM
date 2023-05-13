package dev.fsm.ui.components.app_bars

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.fsm.R
import dev.fsm.presentation.intents.menu.OrdersAppBarIntent
import dev.fsm.ui.components.buttons.FilterButton
import dev.fsm.ui.theme.AppTheme
import dev.fsm.ui.theme.extendedColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrdersAppBar(
    filterCount: Int? = null,
    title: String? = null,
    action: (OrdersAppBarIntent) -> Unit
) {
    TopAppBar(
        modifier = Modifier.wrapContentHeight(),
        colors = MaterialTheme.extendedColors.appBarColors(),
        title = {
            Text(
                text = if (title.isNullOrEmpty()) stringResource(id = android.R.string.unknownName)
                else title,
                modifier = Modifier.padding(start = 8.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            IconButton(onClick = { action.invoke(OrdersAppBarIntent.OpenNavigationDrawer) }) {
                Icon(
                    Icons.Default.Menu,
                    contentDescription = stringResource(id = R.string.icon_nav_menu_description)
                )
            }
        },
        actions = {
            FilterButton(
                value = filterCount ?: 0,
                onClick = {
                    action.invoke(OrdersAppBarIntent.OpenFilter)
                }
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
fun OrdersAppBarPreview() {
    AppTheme {
        Scaffold(
            topBar = {
                OrdersAppBar(
                    filterCount = null,
                    title = stringResource(id = R.string.orders),
                    action = {

                    }
                )
            },
            content = { innerPadding ->
                LazyColumn(
                    contentPadding = innerPadding,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val list = (0..75).map { it.toString() }
                    items(count = list.size) {
                        Text(
                            text = list[it],
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                        )
                    }
                }
            }
        )
    }
}