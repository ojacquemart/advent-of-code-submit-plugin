package com.github.ojacquemart.adventofcode.plugin.fileeditor

import com.github.ojacquemart.adventofcode.plugin.tree.Day
import com.intellij.openapi.fileEditor.FileEditor
import com.intellij.openapi.fileEditor.FileEditorPolicy
import com.intellij.openapi.fileEditor.FileEditorProvider
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import org.jetbrains.annotations.NonNls

class AocFileEditorProvider : FileEditorProvider, DumbAware {

    companion object {
        const val EDITOR_TYPE_ID = "aoc-file-editor"
    }

    override fun accept(project: Project, file: VirtualFile): Boolean = file is Day

    override fun createEditor(project: Project, file: VirtualFile): FileEditor = AocFileEditor(file as Day)

    @NonNls
    override fun getEditorTypeId(): String = EDITOR_TYPE_ID

    override fun getPolicy(): FileEditorPolicy = FileEditorPolicy.HIDE_DEFAULT_EDITOR

}
