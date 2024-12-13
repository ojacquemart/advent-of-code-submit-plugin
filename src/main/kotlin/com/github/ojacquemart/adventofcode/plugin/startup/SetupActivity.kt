package com.github.ojacquemart.adventofcode.plugin.startup

import com.github.ojacquemart.adventofcode.plugin.http.CredentialsManager
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.ProjectActivity

class SetupActivity : ProjectActivity {
    override suspend fun execute(project: Project) {
        val credentialsManager = ApplicationManager.getApplication().getService(CredentialsManager::class.java)
        credentialsManager.init()
    }

}
