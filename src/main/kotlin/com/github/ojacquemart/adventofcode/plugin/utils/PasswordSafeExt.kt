package com.github.ojacquemart.adventofcode.plugin.utils

import com.github.ojacquemart.adventofcode.plugin.Aoc
import com.intellij.ide.passwordSafe.PasswordSafe

fun PasswordSafe.hasPassword(): Boolean = getPassword(Aoc.CREDENTIAL_ATTRS) != null
