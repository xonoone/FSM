package dev.fsm.ui.components.app_bars

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.fsm.R
import dev.fsm.ui.theme.AppTheme
import dev.fsm.ui.theme.extendedColors
import dev.fsm.ui.theme.spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PageAppBar(
    title: String? = null,
    onBackPressed: () -> Unit
) {
    TopAppBar(
        modifier = Modifier.wrapContentHeight(),
        colors = MaterialTheme.extendedColors.appBarColors(),
        title = {
            Text(
                text = title ?: stringResource(id = android.R.string.unknownName),
                modifier = Modifier.padding(start = MaterialTheme.spacing.small),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleMedium
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackPressed) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = stringResource(id = R.string.icon_nav_menu_description)
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewPageAppBar() {
    var topBarActions by rememberSaveable { mutableStateOf(true) }
    val textColor = if(topBarActions) Color.Black else Color.Red

    AppTheme {
        Scaffold(
            topBar = {
                PageAppBar(title = stringResource(id = R.string.change_pass)) {
                    topBarActions = !topBarActions
                }
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
                            color = textColor,
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