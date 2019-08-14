package com.pavesid.criminalintent.controllers

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.fragment.app.Fragment
import com.pavesid.criminalintent.abstractContr.SingleFragmentActivity
import java.util.*

class CrimeActivity : SingleFragmentActivity() {

    companion object{
        const val EXTRA_CRIME_ID = "com.pavesid.criminalintent.crime_id"

        fun newIntent(packageContext: Context, crimeId: UUID): Intent{
            val intent = Intent(packageContext, CrimeActivity::class.java)
            intent.putExtra(EXTRA_CRIME_ID, crimeId)
            return intent
        }
    }

    override fun createFragment(): Fragment {
        val crimeId = intent.getSerializableExtra(EXTRA_CRIME_ID) as UUID
        return CrimeFragment.newInstance(crimeId)
    }
}
