package com.github.ojacquemart.adventofcode.plugin.http

import com.github.ojacquemart.adventofcode.plugin.Aoc
import com.intellij.credentialStore.Credentials
import com.intellij.ide.passwordSafe.PasswordSafe
import com.intellij.openapi.diagnostic.logger
import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import kotlinx.coroutines.runBlocking
import org.jetbrains.concurrency.await

object HttpClientProvider {

    private val LOGGER = logger<CredentialsManager>()

    private const val COOKIE_HEADER = "Cookie"

    const val SESSION_COOKIE = "session"

    val httpClient: HttpClient
        get() = runBlocking {
            when (val credentials = PasswordSafe.instance.getAsync(Aoc.CREDENTIAL_ATTRS).await()) {
                is Credentials -> configure(credentials)
                else -> default()
            }
        }

    private fun configure(credentials: Credentials?): HttpClient = credentials?.let(::configureSession) ?: default()

    private fun configureSession(credentials: Credentials): HttpClient {
        LOGGER.debug("Configuring HTTP client with session")

        return default(credentials.password?.toString(clear = false))
    }

    private fun default(sessionCookie: String? = null): HttpClient {
        LOGGER.debug("Configuring default HTTP client")

        return HttpClient().config {
            defaultRequest {
                url(Aoc.URL)
                header(COOKIE_HEADER, "$SESSION_COOKIE=$sessionCookie")
            }
        }
    }

}
