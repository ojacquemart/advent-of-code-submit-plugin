package com.github.ojacquemart.adventofcode.plugin.execution

import com.github.ojacquemart.adventofcode.plugin.Answer
import com.github.ojacquemart.adventofcode.plugin.Aoc
import com.github.ojacquemart.adventofcode.plugin.http.AocGetLevel
import com.github.ojacquemart.adventofcode.plugin.http.AocSubmitAnswer
import com.intellij.notification.NotificationGroupManager

class ProcessOutputSubmitter {

    companion object {
        private const val NOTIFICATION_TITLE = "AOC Submit"
    }

    fun submit(answer: Answer) {
        when (val level = AocGetLevel.getLevel(answer.yearDay)) {
            null -> notify(answer.yearDay, Answer.Feedback.SOLVED)
            else -> doSubmit(answer.copy(level = level.level))
        }
    }

    private fun doSubmit(answer: Answer) {
        val feedback = AocSubmitAnswer.submit(answer)

        notify(answer.yearDay, feedback)
    }

    private fun notify(yearDay: Answer.YearDay, feedback: Answer.Feedback) {
        NotificationGroupManager.getInstance()
            .getNotificationGroup(Aoc.ID)
            .createNotification("$NOTIFICATION_TITLE - $yearDay", feedback.message, feedback.getNotificationType())
            .notify(null)
    }

}
