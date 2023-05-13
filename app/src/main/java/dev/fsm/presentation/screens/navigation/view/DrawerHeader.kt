package dev.fsm.presentation.screens.navigation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.fsm.ui.components.extensions.modifier.shimmerEffect
import dev.fsm.ui.components.images.UserImage
import dev.fsm.ui.theme.AppTheme
import dev.fsm.ui.theme.corners
import dev.fsm.ui.theme.spacing

@Composable
fun DrawerHeaderShimmer() {
    Container {
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(shape = MaterialTheme.corners.mediumRoundCornerShape())
                .background(color = Color.LightGray)
                .shimmerEffect()
        )
        MaterialTheme.spacing.SpacerMedium()
        Box(
            modifier = Modifier
                .clip(shape = MaterialTheme.corners.mediumRoundCornerShape())
                .fillMaxWidth(0.9F)
                .height(20.dp)
                .shimmerEffect()
        )
        MaterialTheme.spacing.SpacerExtraSmall()
        Box(
            modifier = Modifier
                .clip(shape = MaterialTheme.corners.mediumRoundCornerShape())
                .fillMaxWidth(0.3F)
                .height(16.dp)
                .shimmerEffect()
        )
    }
}

@Composable
fun DrawerHeader(
    profileImageUrl: String?,
    firstName: String,
    lastName: String,
    middleName: String,
    login: String
) {
    Container {
        UserImage(userImageUrl = profileImageUrl, size = 80.dp)
        MaterialTheme.spacing.SpacerMedium()
        Text(
            text = "$lastName $firstName $middleName",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.W500,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        MaterialTheme.spacing.SpacerExtraSmall()
        Text(
            text = login,
            style = MaterialTheme.typography.bodySmall,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun Container(content: @Composable ColumnScope.() -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(color = MaterialTheme.colorScheme.primaryContainer)
            .padding(MaterialTheme.spacing.medium),
        content = content
    )
}

@Preview(showBackground = true)
@Composable
private fun DrawerHeaderShimmerPreview() {
    AppTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(end = 72.dp)
        ) {
            DrawerHeaderShimmer()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DrawerHeaderPreview() {
    val previewUrl =
        "https://s3-alpha-sig.figma.com/img/ef95/5732/04426e8c731252e1deef770a02f50ec5?Expires=1677456000&Signature=eECeQqZmic-Vek2JLfWV6v8scyzt44WFGnOEYbYwjmin7nD4XBv32caObsFrKo45C4g2oxJXnEhfkzn8r61924sd~UObH-zQo2T6~0S5Z11opho6eDbhPcvvPM6zLuOXh8iyT2Wx3XK9p9cqxBtMqGyxXdyr0u133p9y9zz1-FNBm9Q7nhojNWVtUjVxKis1wAcjG~Gr8EptQRk2-g-MCKy2ih7oXCzc-ZOyDOv2xzHVjtqfnRWUuaJJZfdPvZCEvGqgVNfbSlkC69Fpy3VHGDqNwjWNevJHqrr2GHNisjU-0QYN6QvLb8PVyQjjoTaJHch4Eku8LUZGWK8cWuR9uw__&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4"
    AppTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(end = 72.dp)
        ) {
            DrawerHeader(
                profileImageUrl = previewUrl,
                firstName = "Александр",
                lastName = "Петров",
                middleName = "Констатинович",
                login = "petrov.ak879"
            )
        }
    }
}