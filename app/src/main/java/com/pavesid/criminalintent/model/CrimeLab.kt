package com.pavesid.criminalintent.model

import android.content.Context
import java.util.*
import kotlin.collections.ArrayList

class CrimeLab private constructor(context: Context?) {

    companion object {
        private var crimeLab: CrimeLab? = null
        operator fun get(context: Context?): CrimeLab {
            if (crimeLab == null) {
                crimeLab =
                    CrimeLab(context)
            }
            return crimeLab!!
        }
    }

    private var crimes: ArrayList<Crime> = ArrayList()

    init {
        for (i in 0..100) {
            val crime = Crime()
            crime.setTitle("Crime № $i")
            crime.setSolved(i % 2 == 0)
            crime.setRequiresPolice(i % 2 == 1)
            crimes.add(crime)
        }
    }

    fun getCrimes(): List<Crime> {
        return crimes
    }

    fun getCrime(id: UUID): Crime? {
        for (crime in crimes) {
            if (crime.getId() == id) {
                return crime
            }
        }
        return null
    }
}
