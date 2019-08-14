package com.pavesid.criminalintent.controllers

import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TimePicker
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.pavesid.criminalintent.R
import java.util.*

class TimePickerFragment : DialogFragment() {
    companion object {
        private const val ARG_TIME = "time"
        const val EXTRA_TIME = "com.pavesid.criminalintent.controllers.time"

        fun newInstance(time: Date?): TimePickerFragment {
            val args = Bundle()
            args.putSerializable(ARG_TIME, time)

            val fragment = TimePickerFragment()
            fragment.arguments = args
            return fragment
        }
    }
    private lateinit var timePicker: TimePicker

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val time = arguments?.getSerializable(ARG_TIME) as Date

        val calendar = Calendar.getInstance()
        calendar.time = time
        val minute = calendar.get(Calendar.MINUTE)
        val hour = calendar.get(Calendar.HOUR)

        val v = LayoutInflater.from(context).inflate(R.layout.dialog_time, null)

        timePicker = v.findViewById(R.id.dialog_time_picker)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            timePicker.hour = hour
            timePicker.minute = minute
        } else {
            timePicker.currentHour = hour
            timePicker.currentMinute = minute
        }

        timePicker.setOnTimeChangedListener(object : TimePicker.OnTimeChangedListener{
            override fun onTimeChanged(p0: TimePicker?, p1: Int, p2: Int) {
//                timeNow = GregorianCalendar(0, 0, 0, p1, p2).time
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    timePicker.hour = p1
                    timePicker.minute = p2
                } else {
                    timePicker.currentHour = p1
                    timePicker.currentMinute = p2
                }
            }
        })


        return AlertDialog.Builder(context!!)
            .setView(v)
            .setTitle(R.string.date_picker_title)
            .setPositiveButton(android.R.string.ok, object : DialogInterface.OnClickListener {
                override fun onClick(p0: DialogInterface?, p1: Int) {
//                    sendResult(Activity.RESULT_OK, timeNow)
                    val cal = Calendar.getInstance()
                    val date = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        cal.set(0, 0, 0, timePicker.hour, timePicker.minute)
                        cal.time
                    } else {
                        cal.set(0, 0, 0, timePicker.currentHour, timePicker.currentMinute)
                        cal.time
                    }
                    sendResult(Activity.RESULT_OK, date)
                }
            })
            .create()
    }

    fun sendResult(resultCode: Int, time: Date) {
        if (targetFragment == null) {
            return
        }
        val intent = Intent()
        intent.putExtra(EXTRA_TIME, time)

        targetFragment!!.onActivityResult(targetRequestCode, resultCode, intent)
    }
}