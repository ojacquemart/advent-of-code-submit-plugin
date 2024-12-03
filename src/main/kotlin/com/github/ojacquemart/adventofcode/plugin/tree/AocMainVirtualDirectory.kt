package com.github.ojacquemart.adventofcode.plugin.tree

import com.github.ojacquemart.adventofcode.plugin.Aoc
import com.github.ojacquemart.adventofcode.plugin.data.CalendarDataProvider
import com.intellij.openapi.vfs.VirtualFileSystem
import java.util.*
import javax.swing.tree.TreeNode

class AocMainVirtualDirectory(
    val root: AocVirtualFileSystem,
    yearsHolder: List<CalendarDataProvider.YearHolder>,
) : VirtualDirectory(), TreeNode {

    private val years = yearsHolder.map { Year(this, it) }

    init {
        // TODO: rework this
        files = years
    }

    override fun getName(): String = Aoc.NAME

    override fun getChildAt(childIndex: Int): TreeNode = years[childIndex]

    override fun getChildCount(): Int = years.size

    override fun getParent(): AocMainVirtualDirectory? = null

    override fun getIndex(node: TreeNode?): Int = years.indexOf(node)

    override fun getAllowsChildren(): Boolean = true

    override fun isLeaf(): Boolean = false

    override fun children(): Enumeration<out TreeNode> = Vector(years).elements()

    override fun getFileSystem(): VirtualFileSystem = root

    override fun toString(): String = name

}
