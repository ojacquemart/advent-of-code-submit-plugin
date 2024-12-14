package com.github.ojacquemart.adventofcode.plugin.input

import com.github.ojacquemart.adventofcode.plugin.Answer
import com.github.ojacquemart.adventofcode.plugin.Aoc
import com.github.ojacquemart.adventofcode.plugin.http.AocDownloadInput
import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.application.WriteAction
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.openapi.vfs.VirtualFile
import java.io.File

class DownloadInputAction : AnAction() {

    companion object {
        private const val INPUT_DIRECTORY = ".aoc"

        private const val NOTIFICATION_TITLE = "Download Input"

        private val SUPPORTED_FILE_TYPES = setOf(
            "kt",
            "java",
        )
    }

    override fun getActionUpdateThread(): ActionUpdateThread = ActionUpdateThread.BGT

    override fun update(event: AnActionEvent) {
        event.presentation.isVisible = isActionApplicable(event)
    }

    private fun isActionApplicable(e: AnActionEvent): Boolean {
        val file = e.dataContext.getData(CommonDataKeys.VIRTUAL_FILE)

        return file?.let { isSupportedFileType(it) } ?: false
    }

    private fun isSupportedFileType(file: VirtualFile): Boolean =
        SUPPORTED_FILE_TYPES.contains(file.fileType.defaultExtension)

    override fun actionPerformed(event: AnActionEvent) =
        if (!Aoc.State.isSessionSet) notifyNoSession() else performAction(event)

    private fun performAction(event: AnActionEvent) {
        val file = event.getData(CommonDataKeys.VIRTUAL_FILE) ?: return
        val filePath = file.canonicalPath ?: return

        val yearDay = Answer.YearDay.fromActionEvent(filePath) ?: return

        val project = event.getData(CommonDataKeys.PROJECT) ?: return

        val inputFile = File("${project.basePath}/$INPUT_DIRECTORY/${yearDay.year}/${yearDay.day}")
        if (inputFile.exists()) {
            notifyExistingFile(yearDay)

            return
        }

        createDirectoryIfMissing(project, yearDay)
        downloadAndWriteInput(yearDay, inputFile)
    }

    private fun downloadAndWriteInput(yearDay: Answer.YearDay, inputFile: File) {
        val input = AocDownloadInput.download(yearDay)

        WriteAction.run<RuntimeException> {
            inputFile.writeText(input)

            LocalFileSystem.getInstance().refreshAndFindFileByIoFile(inputFile)
        }
    }

    private fun createDirectoryIfMissing(project: Project, yearDay: Answer.YearDay) {
        val basePath = project.basePath ?: return
        val rootProject = LocalFileSystem.getInstance().findFileByPath(basePath) ?: return

        WriteAction.compute<VirtualFile?, RuntimeException> {
            VfsUtil.createDirectoryIfMissing(
                rootProject,
                "$INPUT_DIRECTORY/${yearDay.year}"
            )
        }
    }

    private fun notifyNoSession() {
        NotificationGroupManager.getInstance()
            .getNotificationGroup(Aoc.ID)
            .createNotification(
                NOTIFICATION_TITLE,
                "You are not logged in. Please log in to the Advent of Code website using the AOC view.",
                NotificationType.INFORMATION
            )
            .notify(null)
    }

    private fun notifyExistingFile(yearDay: Answer.YearDay) {
        NotificationGroupManager.getInstance()
            .getNotificationGroup(Aoc.ID)
            .createNotification(
                "$NOTIFICATION_TITLE - $yearDay",
                "The input was already downloaded!",
                NotificationType.INFORMATION
            )
            .notify(null)
    }

}
