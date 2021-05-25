package com.puput.github.settings

import android.content.Context
import com.puput.github.model.AlarmReminder

class ReminderPreference(context: Context) {
    companion object {
        const val REMINDER_PREFS = "reminder_prefs"
        private const val REMINDER = "isAlarmReminder"
    }

    private val preference = context.getSharedPreferences(REMINDER_PREFS, Context.MODE_PRIVATE)

    fun getReminder(): AlarmReminder {
        val model = AlarmReminder()
        model.isAlarmReminder = preference.getBoolean(REMINDER, false)
        return model
    }

    fun setALarmReminder(value: AlarmReminder) {
        val edit = preference.edit()
        edit.putBoolean(REMINDER, value.isAlarmReminder)
        edit.apply()
    }

}