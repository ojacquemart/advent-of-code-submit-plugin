package com.github.ojacquemart.adventofcode.plugin.fileeditor

import com.github.ojacquemart.adventofcode.plugin.Aoc
import com.github.ojacquemart.adventofcode.plugin.tree.Day
import com.intellij.ide.FileIconProvider
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import javax.swing.Icon

class AocFileIconProvider : FileIconProvider, DumbAware {
    override fun getIcon(file: VirtualFile, flags: Int, project: Project?): Icon? {
        if (file is Day) {
            return Aoc.ICON
        }
        return null
    }
}

