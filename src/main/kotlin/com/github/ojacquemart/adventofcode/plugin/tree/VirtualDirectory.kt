package com.github.ojacquemart.adventofcode.plugin.tree

import com.intellij.openapi.vfs.VirtualFile
import java.io.InputStream
import java.io.OutputStream

abstract class VirtualDirectory(
    private val parent: VirtualFile? = null,
    protected var files: List<VirtualFile> = emptyList(),
) : VirtualFile() {

    override fun getPath(): String {
        return parent?.let { "${it.path}/${name}" } ?: name
    }

    override fun isWritable(): Boolean = false

    override fun isDirectory(): Boolean = true

    override fun isValid(): Boolean = true

    override fun getParent(): VirtualFile? = parent

    override fun getChildren(): Array<VirtualFile> {
        return files.toTypedArray()
    }

    override fun getOutputStream(requestor: Any, newModificationStamp: Long, newTimeStamp: Long): OutputStream =
        OutputStream.nullOutputStream()

    override fun contentsToByteArray(): ByteArray = ByteArray(0)

    override fun getTimeStamp(): Long = 0

    override fun getLength(): Long = 0

    override fun refresh(asynchronous: Boolean, recursive: Boolean, postRunnable: Runnable?) {}

    override fun getInputStream(): InputStream = InputStream.nullInputStream()

}
