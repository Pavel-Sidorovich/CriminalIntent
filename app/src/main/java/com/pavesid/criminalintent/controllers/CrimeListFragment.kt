package com.pavesid.criminalintent.controllers

import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pavesid.criminalintent.R
import com.pavesid.criminalintent.model.Crime
import com.pavesid.criminalintent.model.CrimeLab


class CrimeListFragment : Fragment() {
    private lateinit var crimeRecyclerView: RecyclerView
    private var adapter: CrimeAdapter? = null
    private var subtitleVisible = false

    companion object {
        private const val NOT_POLICE = 0
        private const val POLICE = 1
        private const val SAVED_SUBTITLE_VISIBLE = "subtitle"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //val isCountEmpty = CrimeLab[context].getCrimes().isEmpty()
        val view = inflater.inflate(R.layout.fragment_crime_list, container, false)
        crimeRecyclerView = view.findViewById(R.id.crime_recycler_view)
        crimeRecyclerView.layoutManager = LinearLayoutManager(activity)
        if (savedInstanceState != null){
            subtitleVisible = savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE)
        }

        //if(isCountEmpty) {
            updateUI()
//        } else {
//            view = inflater.inflate(R.layout.fragment_crime_empty, container, false) //TODO
//        }
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.fragment_crime_list, menu)

        val subtitleItem = menu?.findItem(R.string.show_subtitle)
        if(subtitleVisible){
            subtitleItem?.setTitle(R.string.hide_subtitle)
        } else {
            subtitleItem?.setTitle(R.string.show_subtitle)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId){
            R.id.new_crime -> {
                val crime = Crime()
                CrimeLab[context].addCrime(crime)
                val intent = CrimePagerActivity.newIntent(context!!, crime.getId())
                startActivity(intent)
                true
            }
            R.id.show_subtitle -> {
                subtitleVisible = !subtitleVisible
                activity?.invalidateOptionsMenu()
                updateSubtitle()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d("M_CrimeListFragment", "Я в Резюм и все забыл")
        updateUI()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(SAVED_SUBTITLE_VISIBLE, subtitleVisible)
    }

    private fun updateSubtitle(){
        val crimeLab = CrimeLab[context]
        val crimeCount = crimeLab.getCrimes().size
        var subtitle: String? = crimeCount.takeIf {it != 0
        }?.let { resources.getQuantityString(R.plurals.subtitle_plural, it, it) } ?: resources.getString(R.string.subtitle_zero)
        if(!subtitleVisible){
            subtitle = null
        }
        val act: AppCompatActivity = activity as AppCompatActivity
        act.supportActionBar?.subtitle = subtitle
    }

    private fun updateUI() {
        val crimeLab = CrimeLab[activity!!]
        val crimes = crimeLab.getCrimes()
        if(adapter == null) {
            adapter = CrimeAdapter(crimes)
            crimeRecyclerView.adapter = adapter
        } else {
            adapter!!.notifyDataSetChanged()
        }

        updateSubtitle()
    }

    private class CrimeHolder(view: View, parent: ViewGroup) :
        RecyclerView.ViewHolder(view) {

        private lateinit var crime: Crime

        init {
            itemView.setOnClickListener {
                val intent = CrimePagerActivity.newIntent(parent.context, crime.getId())
                parent.context.startActivity(intent)
            }
        }

        private var titleTextView: TextView = itemView.findViewById(R.id.crime_title)
        private var dateTextView: TextView = itemView.findViewById(R.id.crime_date)
        private var timeTextView: TextView = itemView.findViewById(R.id.crime_time)
        private var solvedImageView: ImageView = itemView.findViewById(R.id.crime_solved)

        fun bind(crime: Crime) {
            this.crime = crime
            titleTextView.text = crime.getTitle()
            dateTextView.text = DateFormat.format("EEEE, MMM dd, yyyy", crime.getDate()).toString()
            timeTextView.text = DateFormat.format("hh:mm", crime.getTime()).toString()
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