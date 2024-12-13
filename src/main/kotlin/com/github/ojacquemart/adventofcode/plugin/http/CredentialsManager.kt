package com.github.ojacquemart.adventofcode.plugin.http

import com.github.ojacquemart.adventofcode.plugin.Aoc
import com.intellij.credentialStore.Credentials
import com.intellij.ide.passwordSafe.PasswordSafe
import com.intellij.openapi.components.Service
import com.intellij.openapi.diagnostic.logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.concurrency.await

@Service
class CredentialsManager(
    private val cs: CoroutineScope,
) {

    companion object {
        private val LOGGER = logger<CredentialsManager>()
    }

    fun init() {
        cs.launch {
            val credentials = getCredentials()

            init(credentials)
        }
    }

    private fun init(credentials: Credentials?) {
        LOGGER.debug("Init credentials")

        updateState(credentials)
    }

    fun update(credentials: Credentials) {
        LOGGER.debug("Update credentials")

        PasswordSafe.instance.set(Aoc.CREDENTIAL_ATTRS, credentials)
        updateState(credentials)
    }

    private fun updateState(credentials: Credentials?) {
        HttpClientProvider.configure(credentials)
        Aoc.State.changeSessionSet(credentials?.password)
    }

    fun clear() {
        LOGGER.debug("Clear credentials")

        PasswordSafe.instance.set(Aoc.CREDENTIAL_ATTRS, null)
        HttpClientProvider.configure(null)
        Aoc.State.changeSessionSet(null)
    }

    private suspend fun getCredentials(): Credentials? =
        PasswordSafe.instance.getAsync(Aoc.CREDENTIAL_ATTRS).await()

}
