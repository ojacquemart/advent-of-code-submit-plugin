package com.github.ojacquemart.adventofcode.plugin.execution

import com.github.ojacquemart.adventofcode.plugin.Aoc
import com.intellij.codeInsight.daemon.DaemonCodeAnalyzer
import com.intellij.execution.RunConfigurationExtension
import com.intellij.execution.configurations.JavaParameters
import com.intellij.execution.configurations.RunConfigurationBase
import com.intellij.execution.configurations.RunnerSettings
import com.intellij.execution.process.ProcessEvent
import com.intellij.execution.process.ProcessHandler
import com.intellij.execution.process.ProcessListener
import com.intellij.ide.passwordSafe.PasswordSafe
import com.intellij.openapi.util.Key

class AocJavaRunConfigurationExtension : RunConfigurationExtension() {

    companion object {
        const val SERIALIZATION = "AOC_RUNNER"
    }

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
        println("AOC Runner detected 👀")

        val password: String? = PasswordSafe.instance.getPassword(Aoc.CREDENTIAL_ATTRS)
        println("Session 🍪: $password")

        // TODO: only consider the last line before the system exit
        handler.addProcessListener(object : ProcessListener {
            override fun onTextAvailable(event: ProcessEvent, outputType: Key<*>) {
                println(event.text)
            }
        })
        DaemonCodeAnalyzer.getInstance(configuration.project).restart()

        super.attachToProcess(configuration, handler, runnerSettings)
    }

    override fun <T : RunConfigurationBase<*>> updateJavaParameters(
        t: T,
        javaParameters: JavaParameters,
        runnerSettings: RunnerSettings?,
    ) {
    }

}
