package com.pavesid.criminalintent.controllers

import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.DatePicker
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.pavesid.criminalintent.R
import java.util.*

class DatePickerFragment: DialogFragment() {
    companion object{
        private const val ARG_DATE = "date"
        const val EXTRA_DATE = "com.pavesid.criminalintent.controllers.date"

        fun newInstance(date: Date?): DatePickerFragment{
            val args = Bundle()
            args.putSerializable(ARG_DATE, date)

            val fragment = DatePickerFragment()
            fragment.arguments = args
            return fragment
        }
    }
    private lateinit var datePicker: DatePicker

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val date = arguments?.getSerializable(ARG_DATE) as Date

        val calendar = Calendar.getInstance()
        calendar.time = date
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val v = LayoutInflater.from(context).inflate(R.layout.dialog_date, null)

        datePicker = v.findViewById(R.id.dialog_date_picker)
        datePicker.init(year, month, day, null)

        return AlertDialog.Builder(context!!)
            .setView(v)
            .setTitle(R.string.date_picker_title)
            .setPositiveButton(android.R.string.ok) { p0, p1 ->
                val year = datePicker.year
                val month = datePicker.month
                val day = datePicker.dayOfMonth
                val date = GregorianCalendar(year, month, day)
                    .time
                sendResult(Activity.RESULT_OK, date)
            }
            .create()
    }

    private fun sendResult(resultCode: Int, date: Date){
        if(targetFragment == null){
            return
        }
        val intent = Intent()
        intent.putExtra(EXTRA_DATE, date)

        targetFragment!!.onActivityResult(targetRequestCode, resultCode, intent)
    }
}