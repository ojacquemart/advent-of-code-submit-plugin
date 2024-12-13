package com.github.ojacquemart.adventofcode.plugin.http

import com.github.ojacquemart.adventofcode.plugin.Answer
import com.github.ojacquemart.adventofcode.plugin.Aoc
import com.github.ojacquemart.adventofcode.plugin.http.HttpClientProvider.httpClient
import com.intellij.openapi.diagnostic.logger
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.runBlocking

object AocGetLevel {

    private const val DONE_PATTERN = "They provide two gold stars"
    private const val PART_1_PATTERN = "It provides one gold star"

    private val LOGGER = logger<AocGetLevel>()

    fun getLevel(yearDay: Answer.YearDay): Answer.Level? = runBlocking {
        LOGGER.debug("Getting level for $yearDay")

        val body = httpClient
            .request("${Aoc.URL}/${yearDay.year}/day/${yearDay.day}")
            .bodyAsText()

        resolveLevel(body)
    }

    private fun resolveLevel(body: String) =
        if (body.contains(DONE_PATTERN)) null
        else if (body.contains(PART_1_PATTERN)) Answer.Level.PART_2
        else Answer.Level.PART_1
}
