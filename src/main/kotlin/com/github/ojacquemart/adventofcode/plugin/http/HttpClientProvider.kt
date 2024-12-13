package com.github.ojacquemart.adventofcode.plugin.http

import com.intellij.credentialStore.Credentials
import com.intellij.openapi.diagnostic.logger
import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*

object HttpClientProvider {

    private val LOGGER = logger<CredentialsManager>()
    const val SESSION_COOKIE = "session"

    lateinit var httpClient: HttpClient

    fun configure(credentials: Credentials?) {
        credentials?.let(::configureSession) ?: default()
    }

    private fun configureSession(credentials: Credentials) {
        LOGGER.debug("Configuring HTTP client with session")

        httpClient = HttpClient().config {
            defaultRequest {
                header("Cookie", "$SESSION_COOKIE=${credentials.password?.toString(false)}")
            }
        }
    }

    private fun default() {
        LOGGER.debug("Configuring default HTTP client")

        httpClient = HttpClient()
    }
}
