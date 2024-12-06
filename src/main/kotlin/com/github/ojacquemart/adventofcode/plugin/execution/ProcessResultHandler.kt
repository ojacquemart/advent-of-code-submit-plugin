package com.github.ojacquemart.adventofcode.plugin.execution

import com.intellij.execution.process.ProcessEvent
import com.intellij.execution.process.ProcessListener
import com.intellij.openapi.util.Key

class ProcessResultHandler(
    private val submitter: ProcessOutputSubmitter = ProcessOutputSubmitter(),
) : ProcessListener {

    private val result = mutableListOf<String>()

    override fun processTerminated(event: ProcessEvent) {
        getOutput()?.let { submitter.submit(it) }
    }

    private fun getOutput() = result
        .filter { it.isNotBlank() }
        .takeIf { it.size > 2 }
        // first line contains the command
        ?.drop(1)
        // last line contains the exit code
        ?.dropLast(1)
        ?.last()

    override fun onTextAvailable(event: ProcessEvent, outputType: Key<*>) {
        result.add(event.text)
    }

}
