package dev.fsm.domain.usecase.cancel

import dev.fsm.domain.models.cancel.CancelOrderForReasonParams
import dev.fsm.domain.models.cancel.CancelOrderReason
import dev.fsm.domain.repository.ICancelOrderRepository
import dev.fsm.domain.utils.Response

class CancelOrderForReasonById(
    private val repository: ICancelOrderRepository
) {

    suspend fun getReasons(): Response<List<CancelOrderReason>> =
        repository.getNetReasons()

    suspend fun cancel(params: CancelOrderForReasonParams): Response<Unit> =
        repository.cancelOrder(params = params)
}