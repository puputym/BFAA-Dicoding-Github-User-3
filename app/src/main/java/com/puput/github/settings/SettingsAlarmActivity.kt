package com.puput.github.settings

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.puput.github.R
import com.puput.github.databinding.ActivitySettingsAlarmBinding
import com.puput.github.model.AlarmReminder

class SettingsAlarmActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivitySettingsAlarmBinding
    //private lateinit var alarmReceiver: AlarmReceiver
    private lateinit var reciverAlarm: ReciverAlarm
    private lateinit var alarmReminder: AlarmReminder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsAlarmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //alarmReceiver = AlarmReceiver()
        reciverAlarm = ReciverAlarm()
        switchAlarm()
        binding.tvBahasa.setOnClickListener(this)
        binding.tvBack.setOnClickListener(this)
    }

    private fun switchAlarm() {
        val reminderPreference = ReminderPreference(this)
        if (reminderPreference.getReminder().isAlarmReminder) {
            binding.switchAlarm.isChecked = true
        } else {
            binding.switchAlarm.isChecked = false
        }

        binding.switchAlarm.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                saveReminder(true)
                reciverAlarm.setRepeatingAlarm(
                    this
                )
            } else {
                saveReminder(false)
                reciverAlarm.cancelAlarm(this)
            }
        }
    }

    private fun saveReminder(b: Boolean) {
        val reminderPreference = ReminderPreference(this)
        alarmReminder = AlarmReminder()

        alarmReminder.isAlarmReminder = b
        reminderPreference.setALarmReminder(alarmReminder)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.tv_bahasa -> {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
            }
            R.id.tv_back -> finish()
        }
    }
}