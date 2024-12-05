package com.github.ojacquemart.adventofcode.plugin.execution

import com.github.ojacquemart.adventofcode.plugin.Aoc
import com.intellij.execution.Executor
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindowId
import javax.swing.Icon

class AocExecutor : Executor() {

    companion object {
        const val ID = Aoc.ID
        const val NAME = "Submit to AOC"
        const val DESCRIPTION = "Submit your solution to ${Aoc.NAME}"
        const val ACTION_ID = "AocExecutor"
    }

    // TODO: use message bundle?
    override fun getStartActionText(): String = actionName

    override fun getStartActionText(configurationName: String): String = "Submit $configurationName to AOC"

    override fun getToolWindowId(): String = ToolWindowId.RUN

    override fun getToolWindowIcon(): Icon = Aoc.ICON

    override fun getIcon(): Icon = Aoc.ICON

    override fun getDisabledIcon(): Icon? = null

    override fun isSupportedOnTarget(): Boolean = true

    override fun isApplicable(project: Project): Boolean = true

    override fun getDescription(): String = DESCRIPTION

    override fun getActionName(): String = NAME

    override fun getId(): String = ID

    override fun getContextActionId(): String = ACTION_ID

    override fun getHelpId(): String? = null

}
