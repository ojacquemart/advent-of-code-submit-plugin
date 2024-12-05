package com.github.ojacquemart.adventofcode.plugin.execution

import com.github.ojacquemart.adventofcode.plugin.Answer
import com.intellij.execution.process.BaseOSProcessHandler
import com.intellij.execution.process.ProcessEvent
import com.intellij.execution.process.ProcessListener
import com.intellij.execution.process.ProcessOutputType
import com.intellij.openapi.util.Key

class ProcessResultHandler(
    private val submitter: ProcessOutputSubmitter = ProcessOutputSubmitter(),
) : ProcessListener {

    private val result = mutableListOf<String>()

    override fun processTerminated(event: ProcessEvent) {
        val output = getAnswer() ?: return
        val yearDay = getYearDay(event) ?: return

        submitter.submit(Answer(yearDay = yearDay, answer = output))
    }

    private fun getYearDay(event: ProcessEvent): Answer.YearDay? {
        val commandLine = (event.source as BaseOSProcessHandler).commandLine

        return Answer.YearDay.fromCommandLine(commandLine)
    }

    private fun getAnswer() =
        result.lastOrNull { it.isNotBlank() }
            ?.replace("\n", "")

    override fun onTextAvailable(event: ProcessEvent, outputType: Key<*>) {
        val processOutputType = outputType as? ProcessOutputType ?: return

        if (processOutputType.baseOutputType == ProcessOutputType.STDOUT) {
            result.add(event.text)
        }
    }

}
