package dev.fsm.presentation.intents.menu

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class OrdersAppBarIntent: Parcelable {
    object OpenNavigationDrawer : OrdersAppBarIntent()
    object OpenFilter : OrdersAppBarIntent()
}
