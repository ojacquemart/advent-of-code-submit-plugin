package com.github.ojacquemart.adventofcode.plugin.tree

import com.github.ojacquemart.adventofcode.plugin.calendar.CalendarDataProvider
import com.github.ojacquemart.adventofcode.plugin.fileeditor.AocFileType
import com.intellij.openapi.fileTypes.FileType
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.vfs.VirtualFileSystem
import com.intellij.ui.JBColor
import java.io.InputStream
import java.io.OutputStream
import java.util.*
import javax.swing.tree.TreeNode

class Day(
    val year: Year,
    dayHolder: CalendarDataProvider.DayHolder,
) : VirtualFile(), TreeNode {

    private val isInFuture = dayHolder.inFuture

    val day = dayHolder.day

    fun canBeSelected(): Boolean = !isInFuture

    override fun getFileType(): FileType = AocFileType.INSTANCE

    val color: JBColor? = if (isInFuture) JBColor.GRAY else null

    override fun getName(): String = day.toString()

    override fun getFileSystem(): VirtualFileSystem = year.fileSystem

    override fun getPath(): String = "${year.path}/$name"

    override fun isWritable(): Boolean = false

    override fun isDirectory(): Boolean = false

    override fun isValid(): Boolean = true

    override fun getChildAt(childIndex: Int): TreeNode? = null

    override fun getChildCount(): Int = 0

    override fun getParent(): Year = year

    override fun getChildren(): Array<VirtualFile>? = null

    override fun getOutputStream(p0: Any?, p1: Long, p2: Long): OutputStream = OutputStream.nullOutputStream()

    override fun contentsToByteArray(): ByteArray = ByteArray(0)

    override fun getModificationStamp(): Long = 0

    override fun getTimeStamp(): Long = 0

    override fun getLength(): Long = 0

    override fun refresh(p0: Boolean, p1: Boolean, p2: Runnable?) {
    }

    override fun getInputStream(): InputStream = InputStream.nullInputStream()

    override fun getIndex(node: TreeNode?): Int = -1

    override fun getAllowsChildren(): Boolean = false

    override fun isLeaf(): Boolean = true

    override fun children(): Enumeration<out TreeNode>? = null

    override fun hashCode(): Int = day * 31 + year.hashCode()
    override fun equals(other: Any?): Boolean = other is Day && year == other.year && day == other.day
    override fun toString() = name

}
