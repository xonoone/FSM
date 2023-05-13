package dev.fsm.utils

/**
 * Refactoring strings to the required format
 */

interface IStringRefactor {

    fun String.removeCarriageSpaceSymbols(): String

    fun String.refactorToNumPass(): String

    fun String.refactorToInt(): String

    fun String.refactorToLong(): String

    fun String.refactorToDouble(): String

    object StringRefactor : IStringRefactor {
        private const val doubleMaxValueLength = 40
        private const val doubleMinValueLength = 41
        private const val longMaxValueLength = Long.MAX_VALUE.toString().length - 1
        private const val longMinValueLength = Long.MIN_VALUE.toString().length - 1
        private const val intMaxValueLength = Int.MAX_VALUE.toString().length - 1
        private const val intMinValueLength = Int.MIN_VALUE.toString().length - 1

        override fun String.removeCarriageSpaceSymbols(): String {
            return this.replace("[\n ]".toRegex(), "")
        }

        override fun String.refactorToNumPass(): String {
            return this.replace("[^1234567890]".toRegex(), "")
        }

        override fun String.refactorToInt(): String {
            var formattedString = this.replace("[^1234567890-]".toRegex(), "")

            val maxLength = if (formattedString.startsWith("-")) intMinValueLength else intMaxValueLength
            formattedString = if (formattedString.length > maxLength) {
                formattedString.substring(0 until maxLength)
            } else {
                formattedString
            }

            formattedString = if (!formattedString.startsWith("-")) {
                formattedString.replace("-", "")
            } else {
                formattedString.replaceAfter("-", formattedString.replace("-", ""))
            }
            return formattedString
        }

        override fun String.refactorToLong(): String {
            var formattedString = this.replace("[^1234567890-]".toRegex(), "")

            val maxLength = if (formattedString.startsWith("-")) longMinValueLength
            else longMaxValueLength

            formattedString = if (formattedString.length > maxLength) {
                formattedString.substring(0 until maxLength)
            } else {
                formattedString
            }

            formattedString = if (!formattedString.startsWith("-")) {
                formattedString.replace("-", "")
            } else {
                formattedString.replaceAfter("-", formattedString.replace("-", ""))
            }
            return formattedString
        }

        override fun String.refactorToDouble(): String {
            var formattedString = this.replace("[^1234567890.,-]".toRegex(), "")
                .replace(",", ".")

            var withNotMinus = formattedString.replace("-", "")

            if (withNotMinus.startsWith(".")) {
                withNotMinus = withNotMinus
                    .replaceFirst(".", "0.")
            }

            withNotMinus = withNotMinus.replaceAfter(
                ".",
                withNotMinus.replaceBefore(".", "").replace(".", "")
            )

            formattedString = if (!formattedString.startsWith("-")) {
                withNotMinus
            } else {
                formattedString.replaceAfter("-", withNotMinus)
            }

            val maxLength = if (formattedString.startsWith("-")) doubleMinValueLength
            else doubleMaxValueLength

            formattedString = if (formattedString.length > maxLength) {
                formattedString.substring(0 until maxLength)
            } else {
                formattedString
            }

            return formattedString
        }
    }
}