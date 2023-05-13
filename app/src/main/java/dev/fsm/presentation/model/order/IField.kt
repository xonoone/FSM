package dev.fsm.presentation.model.order

import android.os.Parcelable
import dev.fsm.presentation.model.file.FileData
import kotlinx.parcelize.Parcelize
import java.time.LocalDate
import java.time.LocalDateTime

interface IField : Parcelable {
    val id: String
    val name: String
    val value: Any?
    val visible: Boolean
    val required: Boolean

    @Parcelize
    data class Text(
        override val id: String,
        override val name: String,
        override var value: String? = null,
        override val visible: Boolean = false,
        override val required: Boolean = false
    ) : IField

    @Parcelize
    data class Long(
        override val id: String,
        override val name: String,
        override var value: kotlin.Long? = null,
        override val visible: Boolean = false,
        override val required: Boolean = false
    ) : IField

    @Parcelize
    data class Double(
        override val id: String,
        override val name: String,
        override var value: kotlin.Double? = null,
        override val visible: Boolean = false,
        override val required: Boolean = false
    ) : IField

    @Parcelize
    data class Date(
        override val id: String,
        override val name: String,
        override var value: LocalDate? = null,
        override val visible: Boolean = false,
        override val required: Boolean = false
    ) : IField

    @Parcelize
    data class DateTime(
        override val id: String,
        override val name: String,
        override var value: LocalDateTime? = null,
        override val visible: Boolean = false,
        override val required: Boolean = false
    ) : IField

    @Parcelize
    data class File(
        override val id: String,
        override val name: String,
        override val value: kotlin.collections.List<FileData> = listOf(),
        override val visible: Boolean = false,
        override val required: Boolean = false
    ) : IField

    @Parcelize
    data class Photo(
        override val id: String,
        override val name: String,
        override val value: kotlin.collections.List<FileData> = listOf(),
        override val visible: Boolean = false,
        override val required: Boolean = false
    ) : IField

    @Parcelize
    data class Money(
        override val id: String,
        override val name: String,
        override val value: kotlin.Double? = null,
        override val visible: Boolean = false,
        override val required: Boolean = false
    ) : IField

    @Parcelize
    data class List(
        override val id: String,
        override val name: String,
        override val value: OrderList? = null,
        override val visible: Boolean = false,
        override val required: Boolean = false
    ) : IField

    @Parcelize
    data class Link(
        override val id: String,
        override val name: String,
        override val value: String? = null,
        override val visible: Boolean = false,
        override val required: Boolean = false
    ) : IField

    @Parcelize
    data class Condition(
        override val id: String,
        override val name: String,
        override val value: Boolean? = null,
        override val visible: Boolean = false,
        override val required: Boolean = false
    ) : IField
}