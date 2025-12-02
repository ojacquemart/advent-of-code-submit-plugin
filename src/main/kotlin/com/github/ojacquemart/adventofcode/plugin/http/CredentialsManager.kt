package com.github.ojacquemart.adventofcode.plugin.http

import com.github.ojacquemart.adventofcode.plugin.Aoc
import com.intellij.credentialStore.Credentials
import com.intellij.ide.passwordSafe.PasswordSafe
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.Service
import com.intellij.openapi.diagnostic.logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Service
class CredentialsManager(
    private val cs: CoroutineScope,
) {

    companion object {
        private val LOGGER = logger<CredentialsManager>()

        @JvmStatic
        val instance: CredentialsManager
            get() = ApplicationManager.getApplication().getService(CredentialsManager::class.java)

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
        Aoc.State.changeSessionSet(credentials?.password)
    }

    fun clear() {
        LOGGER.debug("Clear credentials")

        PasswordSafe.instance.set(Aoc.CREDENTIAL_ATTRS, null)
        Aoc.State.changeSessionSet(null)
    }

    fun getCredentials(): Credentials? =
        PasswordSafe.instance.get(Aoc.CREDENTIAL_ATTRS)

    // TODO try to use again getAsync after resolution of https://youtrack.jetbrains.com/issue/IJPL-217715/CredentialStoregetAsync-Missing-ApiStatus.Experimental-annotation-in-implementing-classes
    //private suspend fun getCredentials(): Credentials? =
    //  PasswordSafe.instance.getAsync(Aoc.CREDENTIAL_ATTRS).await()

}
