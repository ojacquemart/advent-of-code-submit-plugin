package com.github.ojacquemart.adventofcode.plugin.http

import com.github.ojacquemart.adventofcode.plugin.Answer
import com.github.ojacquemart.adventofcode.plugin.Aoc
import com.intellij.openapi.diagnostic.logger
import com.intellij.platform.util.http.httpClient
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.util.*
import kotlinx.coroutines.runBlocking
import org.jsoup.Jsoup

object AocSubmitAnswer {

    private const val MESSAGE_SELECTOR = "main article p"
    private const val STRIP_RETURN_PART = "["

    private val LOGGER = logger<AocSubmitAnswer>()

    fun submit(answer: Answer): Answer.Feedback = runBlocking {
        LOGGER.debug("Submitting answer ${answer.yearDay} - level ${answer.level}")

        val body = httpClient
            .post("${Aoc.URL}/${answer.yearDay.year}/day/${answer.yearDay.day}/answer") {
                headers {
                    append("Content-Type", ContentType.Application.FormUrlEncoded)
                }
                setBody("level=${answer.level}&answer=${answer.answer}")
            }.bodyAsText()

        createFeedback(body)
    }

    private fun createFeedback(body: String): Answer.Feedback {
        val message = Jsoup.parse(body)
            .select(MESSAGE_SELECTOR).text()
            .substringBefore(STRIP_RETURN_PART).trim()

        return Answer.Feedback(message)
    }

    private fun StringValuesBuilder.append(key: String, value: Any?) = value?.let { append(key, it.toString()) }

}
