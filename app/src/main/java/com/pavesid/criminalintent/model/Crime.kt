package com.pavesid.criminalintent.model

import android.text.format.DateFormat
import java.util.*

class Crime {
    private val id: UUID = UUID.randomUUID()
    private lateinit var title: String
    private var date: String
    private var solved = false
    private var requiresPolice = false

    init{
        val dateMillisecond = Calendar.getInstance().time
        date = DateFormat.format("hh:mm:ss, EEEE, MMM dd, yyyy", dateMillisecond).toString()
    }

    fun getId(): UUID {
        return id
    }

    fun getTitle(): String {
        return title
    }

    fun setTitle(title: String) {
        this.title = title
    }

    fun getDate(): String {
        return date
    }

    fun setDate(date: String) {
        this.date = date
    }

    fun isSolved(): Boolean {
        return solved
    }

    fun setSolved(solved: Boolean) {
        this.solved = solved
    }

    fun getRequiresPolice(): Boolean {
        return requiresPolice
    }

    fun setRequiresPolice(key: Boolean) {
        this.requiresPolice = key
    }
}