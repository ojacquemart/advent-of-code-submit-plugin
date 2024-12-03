package com.github.ojacquemart.adventofcode.plugin.fileeditor

import com.github.ojacquemart.adventofcode.plugin.Aoc
import com.github.ojacquemart.adventofcode.plugin.tree.Day
import com.intellij.ui.jcef.JBCefBrowser
import java.awt.BorderLayout
import javax.swing.JPanel

class AocFileEditorComponent(private val file: Day) : JPanel(BorderLayout()) {

    val browser: JBCefBrowser = JBCefBrowser(url)

    init {
        add(browser.component, BorderLayout.CENTER)
    }

    private val url: String
        get() = java.lang.String.format("${Aoc.URL}/%s/day/%s", file.year, file.day)
}

