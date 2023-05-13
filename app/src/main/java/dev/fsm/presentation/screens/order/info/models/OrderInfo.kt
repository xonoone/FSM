package dev.fsm.presentation.screens.order.info.models

import android.util.Log
import dev.fsm.domain.models.cancel.CancelOrderReason
import dev.fsm.domain.models.global.Address
import dev.fsm.domain.models.global.Region
import dev.fsm.domain.models.global.client.Client
import dev.fsm.domain.models.global.client.Contact
import dev.fsm.domain.models.order.Order
import dev.fsm.domain.models.order.fields.OrderField
import dev.fsm.domain.models.user.User
import dev.fsm.domain.utils.IServerDateConverter
import dev.fsm.presentation.model.order.IField
import dev.fsm.utils.Mapper
import dev.fsm.utils.statuses.IStatusConverter
import dev.fsm.utils.statuses.Statuses
import java.time.LocalDateTime

data class OrderInfo( // TODO: Доделать замену всех моделек
    val id: String,
    val name: String,
    val description: String,
    val plannedDateTime: LocalDateTime?,
    val duration: LocalDateTime?,
    val status: Statuses,
    val roadTimeStart: LocalDateTime? = null,
    val roadTimeStop: LocalDateTime? = null,
    val startTime: LocalDateTime? = null,
    val stopTime: LocalDateTime? = null,
    val brigadeExecutor: String? = null,
    val isChecked: Boolean = false,
    val regions: List<Region> = listOf(),
    val contacts: List<Contact> = listOf(),
    val fields: List<IField> = listOf(),
    val cancelReason: CancelOrderReason? = null,
    val client: Client? = null,
    val executor: User? = null,
    val responsible: User? = null,
    val address: Address? = null
) {
    companion object {
        fun Order.toOrderInfo(
            toField: List<OrderField>.() -> List<IField> = Mapper::toFields
        ): OrderInfo = OrderInfo(
            id = id,
            name = name,
            description = description,
            plannedDateTime = try {
                plannedDateTime?.let {
                    IServerDateConverter.ServerDateConverter.toLocalDateTime(dateTimeISO = it)
                }
            } catch (e: Exception) {
                Log.e("Error", "OrderInfo -> toOrderInfo: planned date time parse exception ", e)
                null
            },
            duration = try {
                duration?.let {
                    IServerDateConverter.ServerDateConverter.toLocalDateTime(dateTimeISO = it)
                }
            } catch (e: Exception) {
                Log.e("Error", "OrderInfo -> toOrderInfo: duration parse exception ", e)
                null
            },
            status = IStatusConverter.StatusConverter.codeToStatus(statusCode)
                ?: throw NullPointerException("Order without status"),
            roadTimeStart = roadTimeStart?.let {
                IServerDateConverter.ServerDateConverter.toLocalDateTime(
                    dateTimeISO = it
                )
            },
            roadTimeStop = roadTimeStop?.let {
                IServerDateConverter.ServerDateConverter.toLocalDateTime(
                    dateTimeISO = it
                )
            },
            startTime = startTime?.let {
                IServerDateConverter.ServerDateConverter.toLocalDateTime(
                    dateTimeISO = it
                )
            },
            stopTime = stopTime?.let {
                IServerDateConverter.ServerDateConverter.toLocalDateTime(
                    dateTimeISO = it
                )
            },
            brigadeExecutor = brigadeExecutor,
            isChecked = isChecked,
            regions = regions,
            contacts = contacts,
            fields = toField.invoke(fields),
            cancelReason = cancelReason,
            client = client,
            executor = executor,
            responsible = responsible,
            address = address,
        )
    }
}