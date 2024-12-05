package com.github.ojacquemart.adventofcode.plugin

import com.intellij.credentialStore.CredentialAttributes
import com.intellij.credentialStore.generateServiceName
import com.intellij.openapi.util.IconLoader.getIcon
import java.time.ZoneId
import javax.swing.Icon

object Aoc {
    const val NAME = "Advent of Code"
    const val ID = "aoc"

    val ICON: Icon = getIcon("/icons/aoc.svg", Aoc::class.java)

    const val DOMAIN = "adventofcode.com"
    const val URL = "https://$DOMAIN"

    val NY_TIMEZONE_ID: ZoneId = ZoneId.of("America/New_York")

    val DAYS = 1..25
    const val FIRST_YEAR = 2015

    val CREDENTIAL_ATTRS = CredentialAttributes(generateServiceName("aoc-submit", ID))

    object State {
        var session: String? = null

        fun isSessionSet() = session != null
    }
}
