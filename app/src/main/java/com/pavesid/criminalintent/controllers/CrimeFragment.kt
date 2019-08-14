package com.pavesid.criminalintent.controllers

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.pavesid.criminalintent.R
import com.pavesid.criminalintent.model.Crime
import com.pavesid.criminalintent.model.CrimeLab
import java.util.*

class CrimeFragment : Fragment() {
    companion object{
        private const val ARG_CRIME_ID = "crime_id"
        private const val DIALOG_DATE = "dialog_date"
        private const val DIALOG_TIME = "dialog_time"
        private const val REQUEST_DATE = 0
        private const val REQUEST_TIME = 1

        fun newInstance(crimeID: UUID): CrimeFragment{
            val args = Bundle()
            args.putSerializable(ARG_CRIME_ID, crimeID)

            val fragment = CrimeFragment()
            fragment.arguments = args
            return fragment
        }
    }
    var crime: Crime? = null
    private lateinit var dateButton: Button
    private lateinit var timeButton: Button
    private lateinit var solvedCheckBox: CheckBox
    private lateinit var titleField: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        val crimeId: UUID = activity!!.intent.getSerializableExtra(CrimeActivity.EXTRA_CRIME_ID) as UUID
        val crimeId: UUID = arguments?.getSerializable(ARG_CRIME_ID) as UUID
        crime = CrimeLab[context].getCrime(crimeId)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_crime, container, false)

        titleField = v.findViewById(R.id.et_crime_title)
        titleField.setText(crime?.getTitle())

        titleField.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                crime?.setTitle(p0.toString())
            }
        })

        dateButton = v.findViewById(R.id.btn_crime_date)
        updateDate()
        dateButton.setOnClickListener {
            val fm = fragmentManager
            val dialog = DatePickerFragment.newInstance(crime?.getDate())

            dialog.setTargetFragment(this@CrimeFragment, REQUEST_DATE)
            dialog.show(fm, DIALOG_DATE)
        }

        timeButton = v.findViewById(R.id.btn_crime_time)
        updateTime()
        timeButton.setOnClickListener {
            val fm = fragmentManager
            val dialog = TimePickerFragment.newInstance(crime?.getTime())

            dialog.setTargetFragment(this@CrimeFragment, REQUEST_TIME)
            dialog.show(fm, DIALOG_TIME)
        }

        solvedCheckBox = v.findViewById(R.id.checkbox_crime_solved)
        solvedCheckBox.isChecked = crime?.isSolved() ?: false
        solvedCheckBox.setOnCheckedChangeListener { _, isChecked -> crime?.setSolved(isChecked) }

        return v
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode != Activity.RESULT_OK){
            return
        }
        if(requestCode == REQUEST_DATE){
            val date = data?.getSerializableExtra(DatePickerFragment.EXTRA_DATE) as Date
            crime?.setDate(date)
            updateDate()
        }
        if(requestCode == REQUEST_TIME){
            val time = data?.getSerializableExtra(TimePickerFragment.EXTRA_TIME) as Date

            crime?.setTime(time)
            updateTime()
        }
    }

    private fun updateDate() {
        dateButton.text = DateFormat.format("EEEE, MMM dd, yyyy", crime?.getDate()).toString()
    }

    private fun updateTime() {
        timeButton.text = DateFormat.format("hh:mm", crime?.getTime()).toString()
    }
}