package com.github.ojacquemart.adventofcode.plugin.execution

import com.github.ojacquemart.adventofcode.plugin.Answer
import com.intellij.testFramework.fixtures.BasePlatformTestCase

class YearDayTests : BasePlatformTestCase() {

    fun testToString() {
        val yearDay = Answer.YearDay(2024, 1)

        assertEquals("2024 - Day 1", yearDay.toString())
    }

    fun testFromShouldGetBothPackageAndClass() {
        val yearDay = Answer.YearDay.fromCommandLine("/opt/java/21 year2024.Day1")

        assertEquals(2024, yearDay.year)
        assertEquals(1, yearDay.day)
    }

    fun testFromShouldGetCurrentYearAndDayIfNoYearPackageIsDefined() {
        val yearDay = Answer.YearDay.fromCommandLine("/opt/java/21 Day24")

        assertTrue(yearDay.year >= 2024)
        assertEquals(24, yearDay.day)
    }

}
