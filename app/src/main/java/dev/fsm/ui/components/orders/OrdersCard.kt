package dev.fsm.ui.components.orders

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import dev.fsm.R
import dev.fsm.domain.models.order.Status
import dev.fsm.domain.utils.IDateConverter.DateConverter.toDurationString
import dev.fsm.domain.utils.IDateConverter.DateConverter.toLocalDateString
import dev.fsm.presentation.screens.orders.models.Orders
import dev.fsm.ui.components.indicators.RoundStatusIndicator
import dev.fsm.ui.theme.AppTheme
import dev.fsm.ui.theme.corners
import dev.fsm.ui.theme.spacing
import dev.fsm.utils.statuses.IStatusConverter.StatusConverter.codeToName
import dev.fsm.utils.statuses.Statuses
import java.time.LocalDateTime
import java.util.UUID

@Composable
fun OrdersCard(
    order: Orders,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.corners.mediumRoundCornerShape(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        elevation = CardDefaults.cardElevation(MaterialTheme.spacing.extraSmall),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = {
                    onClick.invoke()
                    order.isChecked = true
                })
                .background(color = MaterialTheme.colorScheme.surface)
                .padding(MaterialTheme.spacing.small)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (order.status.code == Statuses.Accepted.code) {
                    Text(
                        text = order.date?.toLocalDateString()
                            ?: stringResource(id = R.string.no_data),
                        modifier = Modifier.weight(1f),
                        style = MaterialTheme.typography.labelMedium,

                        )
                    Text(
                        text = order.date?.toDurationString(duration = order.duration) ?: "",
                        textAlign = TextAlign.End,
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.W400
                    )
                } else {
                    Text(
                        text = order.date?.toLocalDateString()
                            ?: stringResource(id = R.string.no_data),
                        style = MaterialTheme.typography.labelMedium
                    )
                    MaterialTheme.spacing.SpacerExtraSmall()
                    Text(
                        text = order.date?.toDurationString(duration = order.duration) ?: "",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.W400
                    )
                    Row(
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = codeToName(order.status.code)?.let {
                                stringResource(id = it)
                            } ?: order.status.name,
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.W500,
                            color = MaterialTheme.colorScheme.tertiary
                        )
                        MaterialTheme.spacing.SpacerExtraSmall()
                        RoundStatusIndicator(
                            statusCode = order.status.code,
                            isChecked = order.isChecked
                        )
                    }
                }
            }
            MaterialTheme.spacing.SpacerMedium()
            Column(modifier = Modifier.fillMaxWidth()) {
                Row {
                    Text(
                        text = "№${order.name}. ${
                            order.type.ifEmpty { stringResource(id = R.string.no_data) }
                        }",
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.W500
                    )
                }
                Text(
                    text = if (!order.address.isNullOrEmpty()) order.address
                    else stringResource(id = R.string.no_data),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun OrderListPreview() {
    val now = LocalDateTime.now()
    val min = LocalDateTime.MIN
    val address = "г.Самара, ул.Московское шоссе, д.3"

    val new = Orders(
        id = UUID.randomUUID().toString(),
        name = "001",
        type = "Монтаж дверей",
        status = Status(
            code = "NEW",
            name = "Новая"
        ),
        isChecked = true,
        address = address,
        date = now.minusDays(3L),
        duration = min.plusHours(23L)
    )
    val accepted = Orders(
        id = UUID.randomUUID().toString(),
        name = "002",
        type = "Монтаж дверей",
        status = Status(
            code = "ACCEPTED",
            name = "Новая"
        ),
        isChecked = true,
        address = address,
        date = now.minusDays(5L),
        duration = min.plusHours(1L)
    )
    val inRoad = Orders(
        id = UUID.randomUUID().toString(),
        name = "003",
        type = "Монтаж дверей",
        status = Status(
            code = "INROAD",
            name = "Новая"
        ),
        isChecked = true,
        address = address,
        date = now.minusDays(2L),
        duration = min.plusHours(2L)
    )
    val active = Orders(
        id = UUID.randomUUID().toString(),
        name = "003",
        type = "Монтаж дверей",
        status = Status(
            code = "ACTIVE",
            name = "Новая"
        ),
        isChecked = true,
        address = "",
        date = now.minusDays(2L),
        duration = min.plusHours(3L)
    )
    val canceled = Orders(
        id = UUID.randomUUID().toString(),
        name = "004",
        type = "Монтаж дверей",
        status = Status(
            code = "CANCELED",
            name = "Новая"
        ),
        isChecked = true,
        address = address,
        date = now.minusDays(4L),
        duration = min.plusHours(4L)
    )
    val completed = Orders(
        id = UUID.randomUUID().toString(),
        name = "005",
        type = "Монтаж дверей",
        status = Status(
            code = "COMPLETED",
            name = "Новая"
        ),
        isChecked = true,
        address = address,
        date = now.minusDays(1L),
        duration = min.plusHours(2L)
    )
    AppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize()
                    .padding(MaterialTheme.spacing.medium),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium)
            ) {
                OrdersCard(new) {}
                OrdersCard(new.copy(isChecked = false)) {}
                OrdersCard(accepted) {}
                OrdersCard(accepted.copy(isChecked = false)) {}
                OrdersCard(inRoad) {}
                OrdersCard(inRoad.copy(isChecked = false)) {}
                OrdersCard(active) {}
                OrdersCard(active.copy(isChecked = false)) {}
                OrdersCard(canceled) {}
                OrdersCard(canceled.copy(isChecked = false)) {}
                OrdersCard(completed) {}
                OrdersCard(completed.copy(isChecked = false)) {}
                OrdersCard(new) {}
                OrdersCard(accepted) {}
                OrdersCard(inRoad) {}
                OrdersCard(active) {}
                OrdersCard(canceled) {}
                OrdersCard(completed) {}
            }
        }
    }
}