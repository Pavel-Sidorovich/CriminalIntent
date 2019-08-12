package com.pavesid.criminalintent.controllers

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.fragment.app.Fragment //TODO замена на v4
import com.pavesid.criminalintent.R
import com.pavesid.criminalintent.model.Crime

class CrimeFragment: Fragment(){
    lateinit var crime: Crime
    lateinit var dateButton: Button
    lateinit var solvedCheckBox: CheckBox
    lateinit var titleField: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        crime = Crime()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //return super.onCreateView(inflater, container, savedInstanceState)
        var v = inflater.inflate(R.layout.fragment_crime, container, false)

        titleField = v.findViewById(R.id.et_crime_title)

        titleField.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                crime.setTitle(p0.toString())
            }
        })

        dateButton = v.findViewById(R.id.btn_crime_date)
        dateButton.text = crime.getDate().toString()
        dateButton.isEnabled = false

        solvedCheckBox = v.findViewById(R.id.checkbox_crime_solved)
        solvedCheckBox.setOnCheckedChangeListener { _, isChecked -> crime.setSolved(isChecked) }

        return v
    }
}