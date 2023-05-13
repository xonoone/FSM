package dev.fsm.data.repository

import dev.fsm.data.network.api.models.order.ReasonForCancellation.Companion.toReasonForCancellation
import dev.fsm.data.storage.models.params.order.CancelOrderForReasonParams.Companion.toDomainCancelOrderForReasonParams
import dev.fsm.data.storage.retrofit.CancelOrderStorageRetrofit
import dev.fsm.data.utils.Mappers.toResponse
import dev.fsm.domain.models.cancel.CancelOrderForReasonParams
import dev.fsm.domain.models.cancel.CancelOrderReason
import dev.fsm.domain.repository.ICancelOrderRepository
import dev.fsm.domain.utils.Response
import javax.inject.Inject

internal class CancelOrderRepositoryImpl @Inject constructor(
    private val network: CancelOrderStorageRetrofit
) : ICancelOrderRepository {

    override suspend fun getNetReasons(): Response<ArrayList<CancelOrderReason>> {
        val response = network.getReasons()
        return response.toResponse { it.toReasonForCancellation() as ArrayList<CancelOrderReason> }
    }

    override suspend fun cancelOrder(params: CancelOrderForReasonParams): Response<Unit> {
        val response = network.cancelOrder(params = params.toDomainCancelOrderForReasonParams())
        return response.toResponse {}
    }

}