package com.github.ojacquemart.adventofcode.plugin.toolwindow

import com.github.ojacquemart.adventofcode.plugin.tree.AocTreeCellRenderer
import com.github.ojacquemart.adventofcode.plugin.tree.AocVirtualFileSystem
import com.github.ojacquemart.adventofcode.plugin.tree.Day
import com.intellij.openapi.diagnostic.thisLogger
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.project.Project
import com.intellij.ui.components.JBScrollPane
import com.intellij.ui.treeStructure.Tree
import java.awt.BorderLayout
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.JComponent
import javax.swing.JPanel

class AocViewCreator(
    private val project: Project,
) {

    fun create(): JComponent {
        val tree = Tree(AocVirtualFileSystem())
        tree.isRootVisible = false
        tree.cellRenderer = AocTreeCellRenderer()
        tree.addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(mouseEvent: MouseEvent) {
                tree.selectedDay?.let(::openDay)
            }
        })

        val panel = JPanel(BorderLayout())
        panel.add(JBScrollPane(tree), BorderLayout.CENTER)

        return panel
    }

    private fun openDay(day: Day) {
        thisLogger().debug("Selected day ${day.day} of year ${day.year}")

        FileEditorManager.getInstance(project).openFile(day, true)
    }

    val Tree.selectedDay: Day?
        get() =
            (this.selectionPath?.lastPathComponent as? Day)
                ?.takeIf { day -> day.canBeSelected() }

}
