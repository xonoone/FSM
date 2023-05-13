package dev.fsm.utils

import dev.fsm.domain.models.order.fields.OrderField
import dev.fsm.domain.utils.IServerDateConverter.ServerDateConverter.toLocalDate
import dev.fsm.domain.utils.IServerDateConverter.ServerDateConverter.toLocalDateTime
import dev.fsm.domain.utils.OrderFiledTypes
import dev.fsm.presentation.model.file.FileData.Companion.toFileData
import dev.fsm.presentation.model.order.OrderList.Companion.toOrderList
import dev.fsm.presentation.model.report.OrderList.Companion.toOrderListReport
import dev.fsm.domain.models.files.FileData as DomainFileData
import dev.fsm.domain.models.order.fields.list.OrderList as DomainOrderList
import dev.fsm.domain.models.report.ReportField as DomainReportField
import dev.fsm.presentation.model.file.FileData as AppFileData
import dev.fsm.presentation.model.order.IField as IFieldOrder
import dev.fsm.presentation.model.order.OrderList as AppOrderList
import dev.fsm.presentation.model.report.IField as IFieldReport
import dev.fsm.presentation.model.report.OrderList as AppOrderListReport

object Mapper {
    fun toValue(any: Any?): Any? {
        return when (any) {
            is List<*> -> {
                val list = arrayListOf<Any?>()
                any.forEach {
                    when (it) {
                        is AppFileData -> list.add(it.toFileData())
                        else -> list.add(it)
                    }
                }
                return list
            }

            is AppOrderListReport -> any.toOrderListReport()
            is AppOrderList -> any.toOrderList()
            else -> any
        }
    }

    fun toFields(list: List<OrderField>): List<IFieldOrder> = list.mapNotNull { it.toField() }

    fun OrderField.toField(): IFieldOrder? = when (type) {
        OrderFiledTypes.Text -> IFieldOrder.Text(
            id = id,
            name = name,
            value = value as String?,
            visible = visible,
            required = required
        )

        OrderFiledTypes.Long -> IFieldOrder.Long(
            id = id,
            name = name,
            value = value as Long?,
            visible = visible,
            required = required
        )

        OrderFiledTypes.Double -> IFieldOrder.Double(
            id = id,
            name = name,
            value = value as Double?,
            visible = visible,
            required = required
        )

        OrderFiledTypes.Date -> IFieldOrder.Date(
            id = id,
            name = name,
            value = (value as String?)?.let { toLocalDate(dateISO = it) },
            visible = visible,
            required = required
        )

        OrderFiledTypes.DateTime -> IFieldOrder.DateTime(
            id = id,
            name = name,
            value = (value as String?)?.let { toLocalDateTime(dateTimeISO = it) },
            visible = visible,
            required = required
        )

        OrderFiledTypes.File -> IFieldOrder.File(
            id = id,
            name = name,
            value = (value as List<DomainFileData>?)?.map { it.toFileData() } ?: listOf(),
            visible = visible,
            required = required
        )

        OrderFiledTypes.Photo -> IFieldOrder.Photo(
            id = id,
            name = name,
            value = (value as List<DomainFileData>?)?.map { it.toFileData() } ?: listOf(),
            visible = visible,
            required = required
        )

        OrderFiledTypes.Money -> IFieldOrder.Money(
            id = id,
            name = name,
            value = value as Double?,
            visible = visible,
            required = required
        )

        OrderFiledTypes.List -> IFieldOrder.List(
            id = id,
            name = name,
            value = (value as DomainOrderList?)?.toOrderList(),
            visible = visible,
            required = required
        )

        OrderFiledTypes.Link -> IFieldOrder.Link(
            id = id,
            name = name,
            value = value as String?,
            visible = visible,
            required = required
        )

        OrderFiledTypes.Condition -> IFieldOrder.Condition(
            id = id,
            name = name,
            value = value as Boolean?,
            visible = visible,
            required = required
        )

        else -> null
    }

    fun DomainReportField.toField(): IFieldReport? = when (type) {
        OrderFiledTypes.Text -> IFieldReport.Text(
            id = id,
            name = name,
            value = value as String?,
            visible = visible,
            required = required
        )

        OrderFiledTypes.Link -> IFieldReport.Link(
            id = id,
            name = name,
            value = value as String?,
            visible = visible,
            required = required
        )

        OrderFiledTypes.Long -> IFieldReport.Long(
            id = id,
            name = name,
            value = value as Long?,
            visible = visible,
            required = required
        )

        OrderFiledTypes.Double -> IFieldReport.Double(
            id = id,
            name = name,
            value = value as Double?,
            visible = visible,
            required = required
        )

        OrderFiledTypes.Money -> IFieldReport.Money(
            id = id,
            name = name,
            value = value as Double?,
            visible = visible,
            required = required
        )

        OrderFiledTypes.Date -> IFieldReport.Date(
            id = id,
            name = name,
            value = (value as String?)?.let { toLocalDate(dateISO = it) },
            visible = visible,
            required = required
        )

        OrderFiledTypes.DateTime -> IFieldReport.DateTime(
            id = id,
            name = name,
            value = (value as String?)?.let { toLocalDateTime(dateTimeISO = it) },
            visible = visible,
            required = required
        )

        OrderFiledTypes.File -> IFieldReport.File(
            id = id,
            name = name,
            value = (value as List<*>?)?.map {
                (it as DomainFileData).toFileData()
            },
            visible = visible,
            required = required
        )

        OrderFiledTypes.Photo -> IFieldReport.Photo(
            id = id,
            name = name,
            value = (value as List<*>?)?.map {
                (it as DomainFileData).toFileData()
            },
            visible = visible,
            required = required
        )

        OrderFiledTypes.Condition -> IFieldReport.Condition(
            id = id,
            name = name,
            value = value as Boolean?,
            visible = visible,
            required = required
        )

        OrderFiledTypes.List -> {
            IFieldReport.List(
                id = id,
                name = name,
                value = (value as DomainOrderList).toOrderListReport(),
                visible = visible,
                required = required
            )
        }

        else -> null
    }
}

