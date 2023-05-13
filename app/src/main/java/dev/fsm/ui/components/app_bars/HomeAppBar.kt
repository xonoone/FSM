package dev.fsm.ui.components.app_bars

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.fsm.R
import dev.fsm.ui.theme.AppTheme
import dev.fsm.ui.theme.extendedColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeAppBar(
    @StringRes title: Int? = null,
    action: () -> Unit
) {
    TopAppBar(
        modifier = Modifier.wrapContentHeight(),
        colors = MaterialTheme.extendedColors.appBarColors(),
        title = {
            Text(
                text = stringResource(id = title ?: android.R.string.unknownName),
                modifier = Modifier.padding(start = 8.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            IconButton(onClick = { action.invoke() }) {
                Icon(
                    Icons.Default.Menu,
                    contentDescription = stringResource(id = R.string.icon_nav_menu_description)
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun HomeAppBarPreview() {
    AppTheme {
        HomeAppBar(title = R.string.app_bar_profile, action = {})
    }
}