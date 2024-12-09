package com.github.ojacquemart.adventofcode.plugin.http

import com.github.ojacquemart.adventofcode.plugin.Answer
import com.github.ojacquemart.adventofcode.plugin.Aoc
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.runBlocking

object AocDownloadInput {

    fun download(yearDay: Answer.YearDay): String = runBlocking {
        val body = HttpClient()
            .request("${Aoc.URL}/${yearDay.year}/day/${yearDay.day}/input") {
                headers {
                    append("Cookie", "session=${Aoc.State.session}")
                }
            }.bodyAsText()
        body
    }

}
