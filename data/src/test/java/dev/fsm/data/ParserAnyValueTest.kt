package dev.fsm.data

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import dev.fsm.data.network.api.models.global.FileData
import dev.fsm.domain.models.order.fields.OrderField
import org.junit.Assert.assertEquals
import org.junit.Test

class ParserAnyValueTest {

    private val gson = GsonBuilder().create()

    @Test
    fun `Is data object`() {
        val data = OrderField(
            id = "1",
            name = "Foo",
            type = "FTFile",
            value = FileData(
                initialName = "Foo",
                actualName = "Bar",
                size = 10L,
                url = "https://vk.com/image_1234567890"
            ),
            visible = true,
            required = false
        )
        val actual: FileData? = parsedValue(data.value)
        val expected = FileData(
            initialName = "Foo",
            actualName = "Bar",
            size = 10L,
            url = "https://vk.com/image_1234567890"
        )
        assertEquals(expected, actual)
    }

    @Test
    fun `Is array data object`() {
        val data = OrderField(
            id = "1",
            name = "Foo",
            type = "FTFile",
            value = listOf(
                FileData(
                    initialName = "Foo",
                    actualName = "Bar",
                    size = 10L,
                    url = "https://vk.com/image_1234567890"
                ),
                FileData(
                    initialName = "Foo",
                    actualName = "Bar",
                    size = 10L,
                    url = "https://vk.com/image_1234567890"
                ),
                FileData(
                    initialName = "Foo",
                    actualName = "Bar",
                    size = 10L,
                    url = "https://vk.com/image_1234567890"
                )
            ),
            visible = true,
            required = false
        )
        val actual: List<FileData>? = parsedListValue(value = data.value)
        val expected = listOf(
            FileData(
                initialName = "Foo",
                actualName = "Bar",
                size = 10L,
                url = "https://vk.com/image_1234567890"
            ),
            FileData(
                initialName = "Foo",
                actualName = "Bar",
                size = 10L,
                url = "https://vk.com/image_1234567890"
            ),
            FileData(
                initialName = "Foo",
                actualName = "Bar",
                size = 10L,
                url = "https://vk.com/image_1234567890"
            )
        )
        assertEquals(expected, actual)
    }

    private inline fun <reified T> parsedValue(
        value: Any?,
    ): T? {
        val json = gson.toJson(value)
        return gson.fromJson(json, T::class.java)
    }

    private inline fun <reified T> parsedListValue(
        value: Any?,
    ): List<T>? {
        val json = gson.toJson(value)
        val type = object : TypeToken<List<T>>(){}.type
        return gson.fromJson<List<T>>(json, type)
    }
}