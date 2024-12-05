package com.github.ojacquemart.adventofcode.plugin.startup

import com.github.ojacquemart.adventofcode.plugin.Aoc
import com.intellij.ide.passwordSafe.PasswordSafe
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.ProjectActivity

class SetupActivity : ProjectActivity {
    override suspend fun execute(project: Project) {
        PasswordSafe.instance.getAsync(Aoc.CREDENTIAL_ATTRS)
            .onSuccess {
                Aoc.State.session = it?.password?.toString(false)
            }
    }
}
