package com.github.ojacquemart.adventofcode.plugin.http

import com.github.ojacquemart.adventofcode.plugin.Answer
import com.github.ojacquemart.adventofcode.plugin.http.HttpClientProvider.httpClient
import com.intellij.openapi.diagnostic.logger
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.runBlocking

object AocDownloadInput {

    private val LOGGER = logger<AocDownloadInput>()

    fun download(yearDay: Answer.YearDay): String = runBlocking {
        LOGGER.debug("Downloading input for $yearDay")

        httpClient
            .request("${yearDay.year}/day/${yearDay.day}/input")
            .bodyAsText()
    }

}
