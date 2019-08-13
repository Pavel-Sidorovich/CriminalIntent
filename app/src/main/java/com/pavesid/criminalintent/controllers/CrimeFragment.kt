package com.pavesid.criminalintent.controllers

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.fragment.app.Fragment //TODO замена на v4
import androidx.fragment.app.FragmentActivity
import com.pavesid.criminalintent.R
import com.pavesid.criminalintent.model.Crime
import java.util.*

class CrimeFragment : Fragment() {
    companion object{
        private const val ARG_CRIME_ID = "crime_id"

        fun newInstante(crimeID: UUID): CrimeFragment{
            val args = Bundle()
            args.putSerializable(ARG_CRIME_ID, crimeID)

            val fragment = CrimeFragment()
            fragment.arguments = args
            return fragment
        }
    }
    var crime: Crime? = null
    lateinit var dateButton: Button
    lateinit var solvedCheckBox: CheckBox
    lateinit var titleField: EditText

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
        dateButton.text = crime?.getDate()
        dateButton.isEnabled = false

        solvedCheckBox = v.findViewById(R.id.checkbox_crime_solved)
        solvedCheckBox.isChecked = crime?.isSolved() ?: false
        solvedCheckBox.setOnCheckedChangeListener { _, isChecked -> crime?.setSolved(isChecked) }

        return v
    }
}