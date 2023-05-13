package dev.fsm.presentation.screens.orders.tablayout.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.fsm.presentation.screens.orders.tablayout.models.TabItem

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TabsContent(
    modifier: Modifier = Modifier,
    tabs: List<TabItem>,
    pagerState: PagerState
) {
    HorizontalPager(
        modifier = modifier,
        state = pagerState,
        pageCount = tabs.size
    ) { page ->
        tabs[page].screen()
    }
}