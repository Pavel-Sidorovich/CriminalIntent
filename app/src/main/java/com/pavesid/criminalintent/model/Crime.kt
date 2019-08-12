package com.pavesid.criminalintent.model

import java.util.*

class Crime {
    private val id: UUID = UUID.randomUUID()
    private lateinit var title: String
    private var date: Date = Date()
    private var solved = false

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

    fun isSolved(): Boolean {
        return solved
    }

    fun setSolved(solved: Boolean) {
        this.solved = solved
    }
}