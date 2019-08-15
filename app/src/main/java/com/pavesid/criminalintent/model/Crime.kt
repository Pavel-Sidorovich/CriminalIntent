package com.pavesid.criminalintent.model

import android.text.format.DateFormat
import java.time.ZoneId
import java.util.*

class Crime {
    private val id: UUID = UUID.randomUUID()
    private var title: String = ""
    private var date: Date = Date()//GregorianCalendar.getInstance(TimeZone.getTimeZone("Europe/Moscow")).time
    private var time: Date = Date()
    private var solved = false
    private var requiresPolice = false

    init{
//        val dateMillisecond = Calendar.getInstance().time
//        date = DateFormat.format("hh:mm:ss, EEEE, MMM dd, yyyy", dateMillisecond)
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

    fun getDate(): Date {
        return date
    }

    fun setDate(date: Date) {
        this.date = date
    }

    fun getTime(): Date {
        return time
    }

    fun setTime(time: Date) {
        this.time = time
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