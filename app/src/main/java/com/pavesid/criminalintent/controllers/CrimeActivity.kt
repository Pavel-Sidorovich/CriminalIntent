package com.pavesid.criminalintent.controllers

import androidx.fragment.app.Fragment
import com.pavesid.criminalintent.abstractContr.SingleFragmentActivity

class CrimeActivity : SingleFragmentActivity() {

    override fun createFragment(): Fragment {
        return CrimeFragment()
    }
}
