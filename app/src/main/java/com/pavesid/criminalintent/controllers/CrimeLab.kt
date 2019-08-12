package com.pavesid.criminalintent.controllers

import android.content.Context
import com.pavesid.criminalintent.model.Crime
import java.util.*
import kotlin.collections.ArrayList

class CrimeLab private constructor(context: Context) {

    companion object {
        private var crimeLab: CrimeLab? = null
        operator fun get(context: Context): CrimeLab {
            return if (crimeLab == null) {
                CrimeLab(context)
            } else {
                crimeLab!!
            }
        }
    }

    private var crimes: ArrayList<Crime> = ArrayList()

    init {
        for (i in 0..100){
            val crime = Crime()
            crime.setTitle("Crime № $i")
            crime.setSolved(i % 2 == 0)
            crimes.add(crime)
        }
    }


    fun getCrime(id: UUID): Crime?{
        for (crime in crimes){
            if(crime.getId() == id){
                return crime
            }
        }
        return null
    }
}
