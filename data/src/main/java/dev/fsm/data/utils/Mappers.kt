package dev.fsm.data.utils

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonArray
import dev.fsm.data.network.api.models.global.FileData
import dev.fsm.data.network.api.models.global.FileData.Companion.toFileData
import dev.fsm.data.network.api.models.order.fields.list.OrderList
import dev.fsm.data.network.api.models.order.fields.list.OrderList.Companion.toOrderList
import dev.fsm.domain.utils.IServerDateConverter.ServerDateConverter.toISODateString
import dev.fsm.domain.utils.IServerDateConverter.ServerDateConverter.toISODateTimeString
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.time.LocalDate
import java.time.LocalDateTime
import dev.fsm.data.utils.Response as DataResponse
import dev.fsm.domain.models.files.FileData as DomainFileData
import dev.fsm.domain.models.order.fields.list.OrderList as DomainOrderList
import dev.fsm.domain.utils.Response as DomainResponse

internal object Mappers {

    /** Responses **/

    fun <FROM, TO> DataResponse<FROM>.toResponse(mapper: (FROM) -> TO): DomainResponse<TO> =
        when (this) {
            is DataResponse.Error -> DomainResponse.Error(exception = exception)
            is DataResponse.Success.Data -> DomainResponse.Success(data = mapper.invoke(data))
            is DataResponse.Success.Empty -> DomainResponse.Success(data = null)
        }

    fun <FROM, TO> DataResponse<FROM>.map(mapper: (FROM) -> TO?): DataResponse<TO> =
        when (this) {
            is DataResponse.Error -> DataResponse.Error(exception = exception)
            is DataResponse.Success.Data -> mapper.invoke(data)?.let {
                DataResponse.Success.Data(data = it)
            } ?: DataResponse.Success.Empty
            is DataResponse.Success.Empty -> DataResponse.Success.Empty
        }

    fun DataResponse<Any>.toDomain(): DomainResponse<Unit> =
        when (this) {
            is DataResponse.Error -> {
                DomainResponse.Error(exception = exception)
            }
            is DataResponse.Success -> {
                DomainResponse.Success(data = null)
            }
        }

    /** Request Body **/

    fun Collection<String>.toRequestBody(): RequestBody {
        val jsonArray = JsonArray()
        this.forEach { jsonArray.add(it) }
        return jsonArray.toString().toRequestBody("application/json".toMediaTypeOrNull())
    }

    fun <T: Any> List<T>.toRequestBody(): RequestBody {
        val jsonString = Gson().toJson(this)
        return jsonString.toRequestBody("application/json".toMediaTypeOrNull())
    }

    fun toValue(any: Any?): Any? {
        return when (any) {
            is List<*> -> {
                val list = arrayListOf<Any?>()
                any.forEach {
                    when (it) {
                        is DomainFileData -> list.add(it.toFileData())
                        is FileData -> list.add(it.toFileData())
                        else -> list.add(it)
                    }
                }
                return list
            }
            is DomainOrderList -> any.toOrderList()
            is OrderList -> any.toOrderList()
            is LocalDateTime -> any.toISODateTimeString()
            is LocalDate -> any.toISODateString()
            else -> any
        }
    }
}