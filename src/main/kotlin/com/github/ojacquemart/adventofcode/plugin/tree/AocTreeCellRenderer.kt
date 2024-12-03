package com.github.ojacquemart.adventofcode.plugin.tree

import com.intellij.ui.ColoredTreeCellRenderer
import com.intellij.ui.SimpleTextAttributes
import javax.swing.JTree

class AocTreeCellRenderer : ColoredTreeCellRenderer() {

    override fun customizeCellRenderer(
        tree: JTree,
        value: Any?,
        selected: Boolean,
        expanded: Boolean,
        leaf: Boolean,
        row: Int,
        hasFocus: Boolean,
    ) {
        when (value) {
            is Day -> append(
                value.day.toString(),
                SimpleTextAttributes(SimpleTextAttributes.STYLE_PLAIN, value.color)
            )

            is Year -> append(
                value.toString(),
                SimpleTextAttributes(SimpleTextAttributes.STYLE_PLAIN, Year.GREEN)
            )
        }

    }

}
