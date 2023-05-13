package dev.fsm.domain.models.order

data class OrderChangeStatusParams(
    val orderId     : String,
    val statusCode  : String
)