package dev.fsm.domain.models.order

import dev.fsm.domain.models.cancel.CancelOrderReason
import dev.fsm.domain.models.global.Address
import dev.fsm.domain.models.global.Region
import dev.fsm.domain.models.global.client.Client
import dev.fsm.domain.models.global.client.Contact
import dev.fsm.domain.models.order.fields.OrderField
import dev.fsm.domain.models.user.User

data class Order(
    val id: String,
    val name: String,
    val description: String,
    val statusCode: String,
    val plannedDateTime: String? = null,
    val duration: String? = null,
    val roadTimeStart: String? = null,
    val roadTimeStop: String? = null,
    val startTime: String? = null,
    val stopTime: String? = null,
    val brigadeExecutor: String? = null,
    val isChecked: Boolean = false,
    val regions: List<Region> = listOf(),
    val contacts: List<Contact> = listOf(),
    val fields: List<OrderField> = listOf(),
    val cancelReason: CancelOrderReason? = null,
    val client          : Client?            = null,
    val executor        : User?              = null,
    val responsible     : User?              = null,
    val address         : Address?           = null
)