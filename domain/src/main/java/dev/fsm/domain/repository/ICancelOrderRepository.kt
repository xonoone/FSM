package dev.fsm.domain.repository

import dev.fsm.domain.models.cancel.CancelOrderForReasonParams
import dev.fsm.domain.models.cancel.CancelOrderReason
import dev.fsm.domain.utils.Response

interface ICancelOrderRepository {

    suspend fun getNetReasons(): Response<ArrayList<CancelOrderReason>>
    suspend fun cancelOrder(params: CancelOrderForReasonParams): Response<Unit>
}