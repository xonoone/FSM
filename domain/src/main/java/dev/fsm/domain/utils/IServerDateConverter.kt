package dev.fsm.domain.utils

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

/**
 * The interface that is needed to format the server time
 */
interface IServerDateConverter {

    fun toLocalDate(dateISO: String): LocalDate
    fun toLocalDateTime(dateTimeISO: String): LocalDateTime

    fun LocalDate.toISODateString(): String
    fun LocalDateTime.toISODateTimeString(): String

    /**
     * A object that implements the IDateTimeConverter interface.
     *
     * This Object is needed to convert the server date and time,
     * which comes in the form of a String in ISO-8601 format,
     * to LocalDateTime and vice versa, respectively.
     */
    object ServerDateConverter : IServerDateConverter {

        private val dateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME
        private val zoneUTC = ZoneOffset.UTC

        override fun toLocalDate(dateISO: String): LocalDate =
            LocalDate.parse(dateISO, dateTimeFormatter)

        override fun toLocalDateTime(dateTimeISO: String): LocalDateTime =
            LocalDateTime.parse(dateTimeISO, dateTimeFormatter)

        override fun LocalDate.toISODateString(): String {
            return try {
                val dateTime = this.atTime(0, 0, 0)
                OffsetDateTime.of(dateTime, zoneUTC).format(dateTimeFormatter)
            } catch (t: Throwable) {
                throw t
            }
        }

        override fun LocalDateTime.toISODateTimeString(): String {
            return try {
                OffsetDateTime.of(this, zoneUTC).format(dateTimeFormatter)
            } catch (t: Throwable) {
                throw t
            }
        }
    }
}