package com.github.ojacquemart.adventofcode.plugin

import com.intellij.notification.NotificationType
import java.time.Year

data class Answer(
    val yearDay: YearDay,
    val answer: String,
    val level: String? = null,
) {

    data class YearDay(
        val year: Int,
        val day: Int,
    ) {

        override fun toString(): String = "$year - Day $day"

        companion object {
            private const val COMMAND_LINE_SEPARATOR = " "
            private const val PACKAGE_CLASS_SEPARATOR = "."

            fun fromCommandLine(commandLine: String): YearDay {
                val maybePackageAndClass = commandLine
                    .substringAfterLast(COMMAND_LINE_SEPARATOR)
                    .split(PACKAGE_CLASS_SEPARATOR)

                return when (maybePackageAndClass.size) {
                    // no package, we assume it's the current year
                    1 -> YearDay(Year.now().value, extractInt(maybePackageAndClass[0]))
                    // both year and day are provided
                    else -> YearDay(extractInt(maybePackageAndClass[0]), extractInt(maybePackageAndClass[1]))
                }
            }

            private fun extractInt(value: String): Int {
                val regex = Regex("""\d+""")
                val matchResult = regex.find(value)

                return matchResult?.value?.toInt() ?: 0
            }

        }

    }

    enum class Level(val level: String) {
        PART_1("1"),
        PART_2("2"),
    }

    class Feedback(
        val message: String,
    ) {
        companion object {
            private const val INCORRECT_ANSWER_PATTERN = "That's not the right answer"
            private const val THROTTLED_PATTERN = "You gave an answer too recently"

            val SOLVED = Feedback("Both parts of this puzzle are complete!")
        }

        fun getNotificationType() =
            if (message.contains(INCORRECT_ANSWER_PATTERN)) NotificationType.ERROR
            else if (message.contains(THROTTLED_PATTERN)) NotificationType.WARNING
            else NotificationType.INFORMATION
    }

}
