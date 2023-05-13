package dev.fsm.data.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dev.fsm.data.network.api.models.global.FileData
import dev.fsm.data.network.api.models.order.fields.OrderField
import dev.fsm.data.network.api.models.order.fields.list.OrderList
import dev.fsm.domain.utils.OrderFiledTypes
import java.lang.reflect.Type

object Converter {
    @TypeConverter
    fun fromString(value: String?): ArrayList<String> {
        val listType: Type = object : TypeToken<ArrayList<String?>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: ArrayList<String?>): String {
        val type = object: TypeToken<ArrayList<Int>>() {}.type
        return Gson().toJson(list, type)
    }

    fun ArrayList<OrderField>.parsFields() {
        this.forEach { field->
            field.value = when (field.type) {
                OrderFiledTypes.Text -> parsedValue<String>(value = field.value)
                OrderFiledTypes.Long -> parsedValue<Long>(value = field.value)
                OrderFiledTypes.Double -> parsedValue<Double>(value = field.value)
                OrderFiledTypes.Date -> parsedValue<String>(value = field.value)
                OrderFiledTypes.DateTime -> parsedValue<String>(value = field.value)
                OrderFiledTypes.File -> parsedListValue<FileData>(value = field.value)
                OrderFiledTypes.Photo -> parsedListValue<FileData>(value = field.value)
                OrderFiledTypes.Money -> parsedValue<Double>(value = field.value)
                OrderFiledTypes.List -> parsedValue<OrderList>(value = field.value)
                OrderFiledTypes.Link -> parsedValue<String>(value = field.value)
                OrderFiledTypes.Condition -> parsedValue<Boolean>(value = field.value)
                else -> field.value
            }
            Result
        }
    }


    private inline fun <reified T> parsedValue(value: Any?): T? {
        val json = Gson().toJson(value)
        return Gson().fromJson(json, T::class.java)
    }

    private inline fun <reified T> parsedListValue(value: Any?): List<T>? {
        val json = Gson().toJson(value)
        val type = object : TypeToken<List<T>>(){}.type
        return Gson().fromJson<List<T>>(json, type)
    }
}