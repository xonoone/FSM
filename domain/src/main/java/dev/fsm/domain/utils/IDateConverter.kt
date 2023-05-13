package dev.fsm.domain.utils

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * Interface describing the functions of converting the date Time into a suitable format for screen forms
 */
interface IDateConverter {

    fun LocalDateTime.toDurationString(duration: LocalDateTime?): String
    fun LocalDate.toLocalDateString(): String
    fun LocalDateTime.toLocalDateString(): String
    fun LocalDateTime.toLocalDateTimeString(): String

    /**
     * A object that implements the IDateConverter interface.
     *
     * This Object is responsible for converting the LocalDateTime to a readable
     * string in the format provided by the application's design.
     */
    object DateConverter : IDateConverter {

        private val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
        private val shortDateTimeFormatter = DateTimeFormatter.ofPattern("dd MMMM HH:mm")
        private val localDateFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")
        private val localDateTimeFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm")

        override fun LocalDateTime.toDurationString(duration: LocalDateTime?): String {
            return try {
                var localDateTimeTo = duration
                    ?.plusHours(this.hour.toLong())
                    ?.plusMinutes(this.minute.toLong())
                    ?.plusSeconds(this.second.toLong())

                val timeFrom = this.format(timeFormatter)
                val timeTo = if (localDateTimeTo != null && localDateTimeTo.dayOfMonth > 1) {
                    localDateTimeTo = localDateTimeTo
                        .plusDays(this.dayOfMonth.toLong())
                        .minusDays(1)
                        .plusMonths(this.monthValue.toLong())
                        .minusMonths(1)
                    localDateTimeTo.format(shortDateTimeFormatter)
                } else {
                    localDateTimeTo?.format(timeFormatter)
                }
                if (timeTo != null) "$timeFrom-$timeTo" else timeFrom
            } catch (_: Exception) {
                ""
            }
        }

        override fun LocalDate.toLocalDateString(): String = format(localDateFormatter)

        override fun LocalDateTime.toLocalDateString(): String = format(localDateFormatter)

        override fun LocalDateTime.toLocalDateTimeString(): String = format(localDateTimeFormatter)

    }
}