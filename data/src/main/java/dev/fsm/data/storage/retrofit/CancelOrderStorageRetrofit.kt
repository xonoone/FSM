package dev.fsm.data.storage.retrofit

import dev.fsm.data.network.api.ApiService
import dev.fsm.data.network.api.models.order.ReasonForCancellation
import dev.fsm.data.storage.ICancelOrderStorage
import dev.fsm.data.storage.models.params.order.CancelOrderForReasonParams
import dev.fsm.data.utils.Mappers.map
import dev.fsm.data.utils.Request
import dev.fsm.data.utils.Response
import javax.inject.Inject

internal class CancelOrderStorageRetrofit @Inject constructor(
    private val api: ApiService,
    private val request: Request
) : ICancelOrderStorage {
    override suspend fun getReasons(): Response<ArrayList<ReasonForCancellation>> =
        request.safeApiCallWithAuth {
            api.getReasonsForCancellation()
        }.map { it }

    override suspend fun cancelOrder(params: CancelOrderForReasonParams): Response<Unit> =
        request.safeApiCallWithAuth {
            api.cancelOrder(
                orderId = params.orderId,
                reasonId = params.reasonId,
                note = params.note
            )
        }.map {}

}