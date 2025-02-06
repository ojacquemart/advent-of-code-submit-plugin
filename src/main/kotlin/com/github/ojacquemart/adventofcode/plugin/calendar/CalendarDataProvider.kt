package com.github.ojacquemart.adventofcode.plugin.calendar

import com.github.ojacquemart.adventofcode.plugin.Aoc
import java.time.LocalDate
import java.time.Month

object CalendarDataProvider {

    data class YearHolder(val year: Int, val days: List<DayHolder>)
    data class DayHolder(val day: Int, val inFuture: Boolean)

    fun getYears(): List<YearHolder> {
        val now = LocalDate.now(Aoc.NY_TIMEZONE_ID)
        val isDecember = now.month == Month.DECEMBER
        val adventYear = if (isDecember) now.year else now.year - 1
        val currentDay = now.dayOfMonth

        val isDayInFuture = { year: Int, day: Int ->
            year == adventYear && isDecember && day > currentDay
        }

        return (adventYear downTo Aoc.FIRST_YEAR)
            .map { year ->
                val days = Aoc.DAYS.map { day -> DayHolder(day, isDayInFuture(year, day)) }

                YearHolder(year, days)
            }
    }

}
