package dev.fsm.presentation.screens.user.info.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Grade
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import dev.fsm.R
import dev.fsm.destinations.ErrorDialogDestination
import dev.fsm.domain.utils.INullabilityChecker.NullabilityChecker.notNull
import dev.fsm.presentation.screens.user.info.contract.UserContract
import dev.fsm.presentation.screens.user.info.contract.UserViewModel
import dev.fsm.presentation.screens.user.info.models.Region
import dev.fsm.presentation.screens.user.info.models.User
import dev.fsm.ui.components.app_bars.HomeAppBar
import dev.fsm.ui.components.images.UserImage
import dev.fsm.ui.components.states.LoadingScreen
import dev.fsm.ui.components.user.UserNoPhoto
import dev.fsm.ui.theme.AppTheme
import dev.fsm.ui.theme.Roboto
import dev.fsm.ui.theme.spacing
import dev.fsm.utils.ILocalizeExceptionMessage.LocalizeExceptionMessage.localized

@Destination
@Composable
fun ScreenUser(
    viewModel: UserViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
    drawerState: DrawerState
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            HomeAppBar(
                title = R.string.app_bar_profile,
                action = {
                    viewModel.handleIntent(event = UserContract.Intent.OpenDrawer)
            })
        },
        content = { innerPadding ->
            UserContent(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                state = state,
                onIntent = viewModel::handleIntent
            )
        }
    )

    LaunchedEffect(key1 = true) {
        viewModel.effect.collect { event ->
            when (event) {
                is UserContract.Effect.Navigate -> navigator.navigate(event.direction)
                is UserContract.Effect.NavigateBackStack -> navigator.navigateUp()
                is UserContract.Effect.ShowErrorDialog -> navigator.navigate(
                    ErrorDialogDestination.invoke(message = event.exception.localized(context))
                )

                is UserContract.Effect.OpenDrawer -> if (drawerState.isClosed) drawerState.open()
            }
        }
    }
}

@Composable
private fun UserContent(
    modifier: Modifier,
    state: UserContract.State,
    onIntent: (UserContract.Intent) -> Unit
) {
    Surface(modifier = modifier) {
        when (state) {
            is UserContract.State.Loaded -> ScreenUserContent(
                user = state.data,
                onIntent = onIntent
            )
            is UserContract.State.Loading -> LoadingScreen()
            else -> Unit
        }
    }
}

@Composable
private fun ScreenUserContent(
    user: User,
    onIntent: (UserContract.Intent) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        UserTop(
            userUrl = user.photo?.url,
            name = user.firstName,
            lastname = user.lastName,
            middleName = user.middleName,
            rating = user.rating
        )
        Column(modifier = Modifier.padding(MaterialTheme.spacing.medium)) {
            UserContent(
                modifier = Modifier.weight(1f),
                login = user.login,
                phoneNumber = user.phone,
                regions = user.regions ?: emptyList()
            )
            UserButtons(
                onChangePassword = { onIntent.invoke(UserContract.Intent.ChangePassword) },
                onLogOut = { onIntent.invoke(UserContract.Intent.Logout) }
            )
        }
    }
}

@Composable
private fun UserTop(
    modifier: Modifier = Modifier,
    userUrl: String?,
    name: String,
    lastname: String,
    middleName: String,
    rating: Double
) {
    Row(
        modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.primaryContainer)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (userUrl != null ) UserImage(userImageUrl = userUrl, isClickable = true, onClick = {})
        else UserNoPhoto()
        MaterialTheme.spacing.SpacerMedium()
        Column {
            Text(
                text = "$lastname $name $middleName",
                style = MaterialTheme.typography.bodyLarge
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    tint = MaterialTheme.colorScheme.tertiary,
                    imageVector = Icons.Outlined.Grade,
                    modifier = Modifier.size(17.dp),
                    contentDescription = "Рейтинг"
                )
                Text(
                    text = rating.toString(),
                    color = MaterialTheme.colorScheme.tertiary,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Composable
private fun UserContent(
    modifier: Modifier = Modifier,
    login: String,
    phoneNumber: String,
    regions: List<Region>
) {
    Column(
        modifier = modifier.verticalScroll(rememberScrollState())
    ) {
        Text(
            text = stringResource(id = R.string.login),
            color = MaterialTheme.colorScheme.tertiary,
            style = MaterialTheme.typography.titleSmall
        )
        MaterialTheme.spacing.SpacerExtraSmall()
        Text(
            text = login,
            style = MaterialTheme.typography.bodyMedium
        )
        MaterialTheme.spacing.SpacerMedium()
        Text(
            text = stringResource(id = R.string.phone_number),
            color = MaterialTheme.colorScheme.tertiary,
            style = MaterialTheme.typography.titleSmall
        )
        MaterialTheme.spacing.SpacerExtraSmall()
        Text(
            text = phoneNumber,
            style = MaterialTheme.typography.bodyMedium
        )
        if (regions.notNull() and regions.isNotEmpty()) {
            MaterialTheme.spacing.SpacerMedium()
            Text(
                text = stringResource(id = R.string.region_of_work),
                color = MaterialTheme.colorScheme.tertiary,
                style = MaterialTheme.typography.titleSmall
            )
            MaterialTheme.spacing.SpacerExtraSmall()
            regions.forEach { region ->
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = region.parentRegion,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    MaterialTheme.spacing.SpacerExtraSmall()
                    region.childRegions.forEach { subRegion ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            MaterialTheme.spacing.SpacerSmall()
                            Box(
                                modifier = Modifier
                                    .background(
                                        color = Color.Black,
                                        shape = AbsoluteRoundedCornerShape(4.dp)
                                    )
                                    .size(4.dp)
                            )
                            MaterialTheme.spacing.SpacerSmall()
                            Text(
                                text = subRegion,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun UserButtons(
    onChangePassword: () -> Unit,
    onLogOut: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        val buttonStyle = TextStyle(
            fontFamily = Roboto,
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.W500,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.secondary
        )
        TextButton(
            onClick = onChangePassword
        ) {
            Text(
                text = stringResource(id = R.string.user_buttom_change_pass),
                style = buttonStyle
            )
        }
        MaterialTheme.spacing.SpacerSmall()
        TextButton(
            onClick = onLogOut
        ) {
            Text(
                text = stringResource(id = R.string.user_buttom_exit),
                style = buttonStyle
            )
        }
        MaterialTheme.spacing.SpacerMedium()
    }
}

@Preview(showBackground = true)
@Composable
private fun ScreenUserContentPreview() {
    AppTheme {
        ScreenUserContent(
            user = User(
                id = "",
                firstName = "Иван",
                lastName = "Иванов",
                middleName = "Иванович",
                phone = "+7 (999) 888-77-66",
                email = "test@yandex.ru",
                login = "test",
                rating = 10.0
            ),
            onIntent = {}
        )
    }
}
