package com.github.ojacquemart.adventofcode.plugin

import com.intellij.openapi.util.IconLoader.getIcon
import java.time.ZoneId
import javax.swing.Icon

object Aoc {
    const val NAME = "Advent of Code"
    const val CODE = "aoc"

    val ICON: Icon = getIcon("/icons/aoc.svg", Aoc::class.java)

    const val URL = "https://adventofcode.com"

    val NY_TIMEZONE_ID: ZoneId = ZoneId.of("America/New_York")

    val DAYS = 1..25
    const val FIRST_YEAR = 2015
}
