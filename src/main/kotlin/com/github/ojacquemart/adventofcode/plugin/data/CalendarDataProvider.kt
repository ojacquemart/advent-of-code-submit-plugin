package com.github.ojacquemart.adventofcode.plugin.data

import com.github.ojacquemart.adventofcode.plugin.Aoc
import java.time.LocalDateTime

object CalendarDataProvider {

    data class YearHolder(val year: Int, val days: List<DayHolder>)
    data class DayHolder(val day: Int, val inFuture: Boolean)

    fun getYears(): List<YearHolder> {
        val now = LocalDateTime.now(Aoc.NY_TIMEZONE_ID)
        val currentYear = now.year
        val currentDay = now.dayOfMonth

        val isDayInFuture = { year: Int, day: Int -> year == currentYear && day > currentDay }

        return (currentYear downTo Aoc.FIRST_YEAR).map { year ->
            val days = Aoc.DAYS.map { day -> DayHolder(day, isDayInFuture(year, day)) }

            YearHolder(year, days)
        }
    }

}
