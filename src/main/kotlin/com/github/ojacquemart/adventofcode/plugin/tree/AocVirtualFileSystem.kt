package com.github.ojacquemart.adventofcode.plugin.tree

import com.github.ojacquemart.adventofcode.plugin.data.CalendarDataProvider
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.vfs.VirtualFileListener
import com.intellij.openapi.vfs.VirtualFileSystem
import javax.swing.event.TreeModelListener
import javax.swing.tree.TreeModel
import javax.swing.tree.TreeNode
import javax.swing.tree.TreePath

class AocVirtualFileSystem : VirtualFileSystem(), TreeModel {

    private val mainDirectory = AocMainVirtualDirectory(this, CalendarDataProvider.getYears())

    companion object {
        const val PROTOCOL = "aoc-submit"
    }

    override fun getProtocol() = PROTOCOL

    override fun getRoot(): TreeNode = mainDirectory

    override fun isReadOnly() = true

    override fun getIndexOfChild(parent: Any?, child: Any?): Int =
        (parent as TreeNode?)?.getIndex(child as TreeNode?) ?: -1

    override fun getChildCount(parent: Any?) = (parent as TreeNode?)?.childCount ?: 0
    override fun isLeaf(node: Any?) = (node as TreeNode).isLeaf

    override fun getChild(parent: Any?, index: Int): Any? {
        return (parent as TreeNode?)?.getChildAt(index)
    }

    override fun findFileByPath(p0: String): VirtualFile? = null
    override fun refreshAndFindFileByPath(p0: String): VirtualFile? = null

    override fun refresh(p0: Boolean) {}

    override fun addVirtualFileListener(p0: VirtualFileListener) {}
    override fun removeVirtualFileListener(p0: VirtualFileListener) {}

    override fun deleteFile(p0: Any?, p1: VirtualFile) {}
    override fun moveFile(p0: Any?, p1: VirtualFile, p2: VirtualFile) {}
    override fun renameFile(p0: Any?, p1: VirtualFile, p2: String) {}
    override fun createChildFile(p0: Any?, p1: VirtualFile, p2: String): VirtualFile = TODO("Not yet implemented")
    override fun createChildDirectory(p0: Any?, p1: VirtualFile, p2: String): VirtualFile = TODO("Not yet implemented")
    override fun copyFile(p0: Any?, p1: VirtualFile, p2: VirtualFile, p3: String): VirtualFile =
        TODO("Not yet implemented")


    override fun valueForPathChanged(path: TreePath?, newValue: Any?) = TODO("Not yet implemented")
    override fun addTreeModelListener(l: TreeModelListener?) {}
    override fun removeTreeModelListener(l: TreeModelListener?) {}

}
