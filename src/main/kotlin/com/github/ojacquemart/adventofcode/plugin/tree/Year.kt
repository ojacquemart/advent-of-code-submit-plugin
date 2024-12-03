package com.github.ojacquemart.adventofcode.plugin.tree

import com.github.ojacquemart.adventofcode.plugin.data.CalendarDataProvider
import com.intellij.openapi.vfs.VirtualFileSystem
import com.intellij.ui.JBColor
import java.awt.Color
import java.util.*
import javax.swing.tree.TreeNode

class Year(
    private val parent: AocMainVirtualDirectory,
    year: CalendarDataProvider.YearHolder,
) : VirtualDirectory(), TreeNode {

    companion object {
        val GREEN: JBColor = JBColor(Color(0, 204, 0), Color(0, 204, 0))
    }

    private val year = year.year

    private val days = year.days.map { Day(this, it) }

    init {
        // TODO: improve this
        files = days.filter { it.canBeSelected() }
    }

    override fun getName(): String = year.toString()

    override fun getFileSystem(): VirtualFileSystem = parent.root

    override fun getChildAt(childIndex: Int): TreeNode = days[childIndex]

    override fun getChildCount(): Int = days.size

    override fun getParent(): AocMainVirtualDirectory = parent

    override fun getIndex(node: TreeNode?): Int = days.indexOf(node)

    override fun getAllowsChildren(): Boolean = true

    override fun isLeaf(): Boolean = false

    override fun children(): Enumeration<out TreeNode> = Vector(days).elements()

    override fun hashCode(): Int = year
    override fun equals(other: Any?): Boolean = if (other is Year) year == other.year else false
    override fun toString() = name

}
