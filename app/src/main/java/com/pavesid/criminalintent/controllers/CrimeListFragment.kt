//package com.pavesid.criminalintent.controllers
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import android.widget.Toast
//import androidx.fragment.app.Fragment
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.pavesid.criminalintent.R
//import com.pavesid.criminalintent.model.Crime
//
//class CrimeListFragment : Fragment() {
//    private lateinit var crimeRecyclerView: RecyclerView
//    private lateinit var adapter: CrimeAdapter
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        val view = inflater.inflate(R.layout.fragment_crime_list, container, false)
//        crimeRecyclerView = view.findViewById(R.id.crime_recycler_view)
//        crimeRecyclerView.layoutManager = LinearLayoutManager(activity)
//        updateUI()
//        return view
//    }
//
//    private fun updateUI() {
//        val crimeLab = CrimeLab[activity!!]
//        val crimes = crimeLab.getCrimes()
//        adapter = CrimeAdapter(crimes)
//        crimeRecyclerView.adapter = adapter
//    }
//
//    private class CrimeHolder(inflater: LayoutInflater, parent: ViewGroup) : //View.OnClickListener,
//        RecyclerView.ViewHolder(inflater.inflate(R.layout.list_item_crime, parent, false)) {
//        private lateinit var crime: Crime
//
//        init{
//            itemView.setOnClickListener {
//                Toast.makeText(parent.context, crime.getTitle() + " clicked!", Toast.LENGTH_SHORT).show()
//            }
//        }
//
//        private var titleTextView: TextView =
//            itemView.findViewById(R.id.crime_title)
//        private var dateTextView: TextView =
//            itemView.findViewById(R.id.crime_date)
//
//        fun bind(crime: Crime) {
//            this.crime = crime
//            titleTextView.text = crime.getTitle()
//            dateTextView.text = crime.getDate().toString()
//        }
//    }
//
//    private class CrimeAdapter(private var crimes: List<Crime>) : RecyclerView.Adapter<CrimeHolder>() {
//        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrimeHolder {
//            val layoutInflater = LayoutInflater.from(parent.context)
//            return CrimeHolder(layoutInflater, parent)
//        }
//
//        override fun getItemCount(): Int {
//            return crimes.size
//        }
//
//        override fun onBindViewHolder(holder: CrimeHolder, position: Int) {
//            val crime = crimes[position]
//            holder.bind(crime)
//        }
//    }
//}


package com.pavesid.criminalintent.controllers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pavesid.criminalintent.R
import com.pavesid.criminalintent.model.Crime


class CrimeListFragment : Fragment() {
    private lateinit var crimeRecyclerView: RecyclerView
    private var adapter: CrimeAdapter? = null

    companion object {
        private const val NOT_POLICE = 0
        private const val POLICE = 1
        private var mX = 0
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_crime_list, container, false)
        crimeRecyclerView = view.findViewById(R.id.crime_recycler_view)
        crimeRecyclerView.layoutManager = LinearLayoutManager(activity)
        updateUI()
        return view
    }

    override fun onResume() {
        super.onResume()
        updateUI(mX)
    }

    private fun updateUI(position: Int = -1) {
        val crimeLab = CrimeLab[activity!!]
        val crimes = crimeLab.getCrimes()
        if(adapter == null) {
            adapter = CrimeAdapter(crimes)
            crimeRecyclerView.adapter = adapter
        } else {
            adapter!!.notifyItemChanged(position)
        }
    }

    private class CrimeHolder(view: View, parent: ViewGroup) :
        RecyclerView.ViewHolder(view) {

        private lateinit var crime: Crime

        init {
            itemView.setOnClickListener {
//                val intent = Intent(parent.context, CrimeActivity::class.java)
                val intent = CrimeActivity.newIntent(parent.context, crime.getId())
                mX = adapterPosition
                parent.context.startActivity(intent)
            }
        }

        private var titleTextView: TextView = itemView.findViewById(R.id.crime_title)
        private var dateTextView: TextView = itemView.findViewById(R.id.crime_date)
        private var solvedImageView: ImageView = itemView.findViewById(R.id.crime_solved)

        fun bind(crime: Crime) {
            this.crime = crime
            titleTextView.text = crime.getTitle()
            dateTextView.text = crime.getDate()
            solvedImageView.visibility = if(crime.isSolved()) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }

    private class CrimeAdapter(private var crimes: List<Crime>) : RecyclerView.Adapter<CrimeHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrimeHolder {
            return when (viewType) {
                POLICE -> CrimeHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.list_item_crime_alarm, parent,false),
                    parent
                )
                else -> CrimeHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.list_item_crime, parent, false),
                    parent
                )
            }
        }

        override fun getItemCount(): Int {
            return crimes.size
        }

        override fun onBindViewHolder(holder: CrimeHolder, position: Int) {
            val crime = crimes[position]
            holder.bind(crime)
        }

        override fun getItemViewType(position: Int): Int {
            return if (crimes[position].getRequiresPolice()) {
                POLICE
            } else {
                NOT_POLICE
            }
        }
    }
}