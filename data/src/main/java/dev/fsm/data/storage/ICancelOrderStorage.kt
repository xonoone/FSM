package dev.fsm.data.storage

import dev.fsm.data.network.api.models.order.ReasonForCancellation
import dev.fsm.data.storage.models.params.order.CancelOrderForReasonParams
import dev.fsm.data.utils.Response

interface ICancelOrderStorage {

    suspend fun getReasons(): Response<ArrayList<ReasonForCancellation>>
    suspend fun cancelOrder(params: CancelOrderForReasonParams): Response<Unit>
}