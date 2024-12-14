package com.github.ojacquemart.adventofcode.plugin.execution

import com.github.ojacquemart.adventofcode.plugin.Answer
import com.intellij.testFramework.fixtures.BasePlatformTestCase

class YearDayTests : BasePlatformTestCase() {

    fun testToString() {
        val yearDay = Answer.YearDay(2024, 1)

        assertEquals("2024 - Day 1", yearDay.toString())
    }

    fun testFromActionEventShouldNotGetYearAndDayIfFilePathIsInvalid() {
        val yearDay = Answer.YearDay.fromActionEvent("/usr/local/foobar/foo/bar/qix")

        assertNull(yearDay)
    }

    fun testFromActionEventShouldGetYearAndDay() {
        val yearDay = Answer.YearDay.fromActionEvent("/usr/local/foobar/src/year2023/Day1.kt")

        assertEquals(2023, yearDay?.year)
        assertEquals(1, yearDay?.day)
    }

    fun testFromActionEventShouldGetDayAndCurrentYearIfNoYearFolder() {
        val yearDay = Answer.YearDay.fromActionEvent("/usr/local/foobar/src/Day24.kt")

        assertTrue((yearDay?.year ?: -1) >= 2024)
        assertEquals(24, yearDay?.day)
    }

    fun testFromCommandLineShouldGetBothPackageAndClass() {
        val yearDay = Answer.YearDay.fromCommandLine("/opt/java/21 year2024.Day1")

        assertEquals(2024, yearDay.year)
        assertEquals(1, yearDay.day)
    }

    fun testFromCommandLineShouldGetCurrentYearAndDayIfNoYearPackageIsDefined() {
        val yearDay = Answer.YearDay.fromCommandLine("/opt/java/21 Day24")

        assertTrue((yearDay.year ?: -1) >= 2024)
        assertEquals(24, yearDay.day)
    }

}
