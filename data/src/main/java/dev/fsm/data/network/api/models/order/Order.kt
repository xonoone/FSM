package dev.fsm.data.network.api.models.order

import com.google.gson.annotations.SerializedName
import dev.fsm.data.network.api.models.client.Client
import dev.fsm.data.network.api.models.client.Client.Companion.toClient
import dev.fsm.data.network.api.models.client.Contact
import dev.fsm.data.network.api.models.client.Contact.Companion.toContact
import dev.fsm.data.network.api.models.global.AddressData
import dev.fsm.data.network.api.models.global.AddressData.Companion.toAddressData
import dev.fsm.data.network.api.models.global.Region
import dev.fsm.data.network.api.models.global.Region.Companion.toRegion
import dev.fsm.data.network.api.models.order.OrderStatus.Companion.toOrderStatus
import dev.fsm.data.network.api.models.order.ReasonForCancellation.Companion.toReasonForCancellation
import dev.fsm.data.network.api.models.order.fields.OrderField.Companion.toOrderField
import dev.fsm.data.network.api.models.user.User
import dev.fsm.data.network.api.models.user.User.Companion.toUserDomain
import dev.fsm.domain.models.order.Status
import dev.fsm.domain.models.orders.Orders
import dev.fsm.domain.models.global.Region as DomainRegion
import dev.fsm.domain.models.global.client.Contact as DomainContact
import dev.fsm.domain.models.order.Order as DomainOrder

data class Order(
    @SerializedName("id") val id: String? = null,
    @SerializedName("orderNum") val num: Int? = null,
    @SerializedName("orderNumPrefix") val prefix: String? = null,
    @SerializedName("orderName") val name: String? = null,
    @SerializedName("isReadByExecutor") val isChecked: Boolean? = null,
    @SerializedName("plannedDateTime") val plannedDateTime: String? = null,
    @SerializedName("roadTimeStart") val roadTimeStart: String? = null,
    @SerializedName("roadTimeStop") val roadTimeStop: String? = null,
    @SerializedName("startTime") val startTime: String? = null,
    @SerializedName("stopTime") val stopTime: String? = null,
    @SerializedName("regions") val regions: ArrayList<Region>? = arrayListOf(),
    @SerializedName("contacts") val contacts: ArrayList<Contact>? = arrayListOf(),
    @SerializedName("cancellationReason") val cancelReason: ReasonForCancellation? = null,
    @SerializedName("template") val template: OrderTemplate? = null,
    @SerializedName("orderStatus") val status: OrderStatus? = null,
    @SerializedName("client") val client: Client? = null,
    @SerializedName("orderBrigadeExecutor") val responsible: User? = null,
    @SerializedName("orderEmployeeExecutor") val executor: User? = null,
    @SerializedName("address") val address: AddressData? = null
) {
    companion object {
        fun Order.toOrder(): DomainOrder = DomainOrder(
            id = id ?: throw NullPointerException("No ID found"),
            name = name ?: "",
            plannedDateTime = plannedDateTime ?: "",
            roadTimeStart = roadTimeStart,
            roadTimeStop = roadTimeStop,
            startTime = startTime,
            stopTime = stopTime,
            brigadeExecutor = "",
            isChecked = isChecked ?: true,
            regions = regions?.toRegion() as List<DomainRegion>? ?: listOf(),
            contacts = contacts?.toContact() as List<DomainContact>? ?: listOf(),
            fields = template?.orderFields?.toOrderField() ?: emptyList(),
            cancelReason = cancelReason?.toReasonForCancellation(),
            statusCode = status?.code ?: throw NullPointerException("Status not found"),
            client = client?.toClient(),
            executor = executor?.toUserDomain(),
            responsible = responsible?.toUserDomain(),
            address = address?.toAddressData(),
            description = template?.description ?: "",
            duration = template?.duration ?: ""
        )

        fun Order.toOrders(): Orders = Orders(
            id = id ?: throw NullPointerException("No ID found"),
            name = name ?: "",
            type = template?.type ?: "",
            status = status?.toOrderStatus() ?: Status(),
            date = plannedDateTime ?: "",
            duration = template?.duration ?: "",
            address = address?.fullAddress ?: "",
            isChecked = isChecked ?: true
        )

        fun Order.toOrderReport(): OrderReport = OrderReport(
            orderId = id ?: throw NullPointerException("No id found"),
            name = name ?: "",
            description = template?.description ?: "",
            reportFields = template?.reportFields ?: listOf()
        )
    }
}
