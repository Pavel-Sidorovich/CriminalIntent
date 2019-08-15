package com.pavesid.criminalintent.controllers

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.pavesid.criminalintent.R
import com.pavesid.criminalintent.model.Crime
import com.pavesid.criminalintent.model.CrimeLab
import java.util.*


class CrimePagerActivity : AppCompatActivity() {
    companion object {
        private const val EXTRA_CRIME_ID = "com.pavesid.criminalintent.controllers.crime_id"

        fun newIntent(packageContext: Context, crimeId: UUID): Intent {
            val intent = Intent(packageContext, CrimePagerActivity::class.java)
            intent.putExtra(EXTRA_CRIME_ID, crimeId)
            return intent
        }
    }

    private lateinit var viewPager: ViewPager
    private lateinit var crimes: List<Crime>
    private lateinit var start: Button
    private lateinit var end: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crime_pager)

        val crimeId = intent.getSerializableExtra(EXTRA_CRIME_ID) as UUID

        viewPager = findViewById(R.id.crime_view_pager)

        crimes = CrimeLab[this].getCrimes()

        val fragmentManager = supportFragmentManager
        viewPager.adapter = object : FragmentStatePagerAdapter(fragmentManager) {
            override fun getItem(position: Int): Fragment {
                val crime = crimes[position]
                return CrimeFragment.newInstance(crime.getId())
            }

            override fun getCount(): Int {
                return crimes.size
            }
        }

        start = findViewById(R.id.btn_start)
        end = findViewById(R.id.btn_end)

        if(viewPager.currentItem == 0){
            start.isEnabled = false
        } else if(viewPager.currentItem == crimes.size){
            end.isEnabled = false
        }

        start.setOnClickListener {
            viewPager.currentItem = 0
            start.isEnabled = false
            end.isEnabled = true
        }
        end.setOnClickListener {
            viewPager.currentItem = crimes.size
            start.isEnabled = true
            end.isEnabled = false
        }

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(i: Int, v: Float, i1: Int) {

            }

            override fun onPageSelected(position: Int) {
                start.isEnabled = position != 0
                end.isEnabled = position < crimes.size - 1
            }

            override fun onPageScrollStateChanged(i: Int) {

            }
        })

        for (i in 0 until crimes.size) {
            if (crimes[i].getId() == crimeId) {
                viewPager.currentItem = i
                break
            }
        }
    }

}