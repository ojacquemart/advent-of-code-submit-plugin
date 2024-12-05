package com.github.ojacquemart.adventofcode.plugin.fileeditor

import com.github.ojacquemart.adventofcode.plugin.Aoc
import com.intellij.openapi.fileTypes.FileType
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.vfs.VirtualFile
import org.jetbrains.annotations.NonNls
import javax.swing.Icon
import kotlin.text.Charsets.UTF_8

class AocFileType : FileType, DumbAware {

    @NonNls
    override fun getName(): String = Aoc.NAME

    override fun getDescription(): String = Aoc.NAME

    override fun getDefaultExtension(): String = Aoc.ID

    override fun getIcon(): Icon = Aoc.ICON

    override fun isBinary(): Boolean = false

    @NonNls
    override fun getCharset(file: VirtualFile, content: ByteArray): String? = UTF_8.name()

    companion object {
        val INSTANCE = AocFileType()
    }
}

