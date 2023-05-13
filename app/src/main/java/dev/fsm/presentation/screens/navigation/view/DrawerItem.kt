package dev.fsm.presentation.screens.navigation.view

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Inventory2
import androidx.compose.material.icons.outlined.ListAlt
import androidx.compose.ui.graphics.vector.ImageVector
import com.ramcosta.composedestinations.spec.Direction
import dev.fsm.R
import dev.fsm.destinations.OrdersArchiveScreenDestination
import dev.fsm.destinations.OrdersScreenDestination
import dev.fsm.destinations.ScreenUserDestination

sealed class DrawerItem(
    @StringRes val title: Int,
    val icon: ImageVector,
    val direction: Direction? = null
) {
    object Orders : DrawerItem(
        icon = Icons.Outlined.ListAlt,
        title = R.string.orders,
        direction = OrdersScreenDestination
    )

    object Archive : DrawerItem(
        icon = Icons.Outlined.Inventory2,
        title = R.string.archive,
        direction = OrdersArchiveScreenDestination
    )

    object Profile : DrawerItem(
        icon = Icons.Outlined.AccountCircle,
        title = R.string.profile,
        direction = ScreenUserDestination
    )
}