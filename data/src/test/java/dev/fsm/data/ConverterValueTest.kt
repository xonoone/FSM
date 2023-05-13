package dev.fsm.data

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dev.fsm.data.network.api.models.global.FileData
import dev.fsm.data.network.api.models.order.fields.OrderField
import dev.fsm.data.network.api.models.order.fields.list.OrderList
import dev.fsm.data.network.api.models.order.fields.list.OrderListItem
import dev.fsm.domain.utils.OrderFiledTypes
import org.junit.Test

class ConverterValueTest {

    @Test
    fun `Parser test`() {
        val data = arrayListOf(
            OrderField(
                id = "1",
                name = "2",
                type = "3",
                owner = null,
                value = listOf(
                    FileData(
                        initialName = "1",
                        actualName = "2",
                        size = 1,
                        url = "3"
                    ),
                    FileData(
                        initialName = "4",
                        actualName = "5",
                        size = 1,
                        url = "6"
                    )
                )
            ),
            OrderField(
                id = "1",
                name = "2",
                type = "FTFile",
                owner = null,
                value = listOf(
                    FileData(
                        initialName = "1",
                        actualName = "2",
                        size = 1,
                        url = "3"
                    ),
                    FileData(
                        initialName = "4",
                        actualName = "5",
                        size = 1,
                        url = "6"
                    )
                )
            ),
            OrderField(
                id = "1",
                name = "2",
                type = "FTList",
                owner = null,
                value = OrderList(
                    id = "1",
                    name = "2",
                    isSingle = true,
                    values = listOf(
                        OrderListItem(
                            id = "1",
                            name = "One",
                            isSelected = true
                        ),
                        OrderListItem(
                            id = "2",
                            name = "Two",
                            isSelected = false
                        )
                    )
                )
            )
        )
        val actual = data.parsFields()
        println(data)
    }

    fun ArrayList<OrderField>.parsFields() {
        this.forEach { field ->
            field.value = when (field.type) {
                OrderFiledTypes.Text -> parsedValue<String>(value = field.value)
                OrderFiledTypes.Long -> parsedValue<Long>(value = field.value)
                OrderFiledTypes.Double -> parsedValue<Double>(value = field.value)
                OrderFiledTypes.Date -> parsedValue<String>(value = field.value)
                OrderFiledTypes.DateTime -> parsedValue<String>(value = field.value)
                OrderFiledTypes.File -> parsedValue<List<FileData>>(value = field.value)
                OrderFiledTypes.Photo -> parsedListValue<List<FileData>>(value = field.value)
                OrderFiledTypes.Money -> parsedValue<Double>(value = field.value)
                OrderFiledTypes.List -> parsedValue<OrderList>(value = field.value)
                OrderFiledTypes.Link -> parsedValue<String>(value = field.value)
                OrderFiledTypes.Condition -> parsedValue<Boolean>(value = field.value)
                else -> field.value
            }
        }
    }

    private inline fun <reified T> parsedValue(value: Any?): T? {
        val gson = Gson()
        val json = gson.toJson(value)
        return gson.fromJson(json, T::class.java)
    }

    private inline fun <reified T> parsedListValue(value: Any?): List<T>? {
        val gson = Gson()
        val json = gson.toJson(value)
        val type = object : TypeToken<List<T>>() {}.type
        return gson.fromJson<List<T>>(json, type)
    }
}