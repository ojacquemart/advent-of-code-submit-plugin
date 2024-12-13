package com.github.ojacquemart.adventofcode.plugin.execution

import com.github.ojacquemart.adventofcode.plugin.Aoc
import com.intellij.execution.configurations.ConfigurationInfoProvider
import com.intellij.execution.configurations.RunProfile
import com.intellij.execution.configurations.RunnerSettings
import com.intellij.execution.impl.DefaultJavaProgramRunner
import com.intellij.openapi.util.InvalidDataException
import com.intellij.openapi.util.WriteExternalException
import org.jdom.Element

class AocJavaRunner : DefaultJavaProgramRunner() {

    companion object {
        const val RUNNER_ID = "AOCRunner"
    }

    internal class Data : RunnerSettings {
        @Throws(InvalidDataException::class)
        override fun readExternal(element: Element) {
        }

        @Throws(WriteExternalException::class)
        override fun writeExternal(element: Element) {
        }
    }

    override fun getRunnerId(): String = RUNNER_ID

    override fun canRun(executorId: String, profile: RunProfile): Boolean =
        Aoc.State.isSessionSet && executorId == AocExecutor.ID

    override fun createConfigurationData(settingsProvider: ConfigurationInfoProvider): RunnerSettings = Data()

}
