package com.github.ojacquemart.adventofcode.plugin.execution

import com.intellij.execution.RunConfigurationExtension
import com.intellij.execution.configurations.JavaParameters
import com.intellij.execution.configurations.RunConfigurationBase
import com.intellij.execution.configurations.RunnerSettings
import com.intellij.execution.process.ProcessHandler

class AocJavaRunConfigurationExtension : RunConfigurationExtension() {

    companion object {
        const val SERIALIZATION = "AOC_RUNNER"
    }

    private val processResultHandler = ProcessResultHandler()

    override fun getEditorTitle(): String = "AOC Runner"

    override fun getSerializationId(): String = SERIALIZATION

    override fun isApplicableFor(configuration: RunConfigurationBase<*>): Boolean = true

    override fun isEnabledFor(
        applicableConfiguration: RunConfigurationBase<*>,
        runnerSettings: RunnerSettings?,
    ): Boolean {
        val enabled = super.isEnabledFor(applicableConfiguration, runnerSettings)
        val isRunningSettingSupported = runnerSettings is AocJavaRunner.Data

        return enabled && isRunningSettingSupported
    }

    override fun attachToProcess(
        configuration: RunConfigurationBase<*>,
        handler: ProcessHandler,
        runnerSettings: RunnerSettings?,
    ) {
        handler.addProcessListener(processResultHandler)

        super.attachToProcess(configuration, handler, runnerSettings)
    }

    override fun <T : RunConfigurationBase<*>> updateJavaParameters(
        t: T,
        javaParameters: JavaParameters,
        runnerSettings: RunnerSettings?,
    ) {
    }

}
