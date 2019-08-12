package com.pavesid.criminalintent.controllers

import androidx.fragment.app.Fragment
import com.pavesid.criminalintent.abstractContr.SingleFragmentActivity

class CrimeListActivity: SingleFragmentActivity() {
    override fun createFragment(): Fragment {
        return CrimeFragment()
    }
}