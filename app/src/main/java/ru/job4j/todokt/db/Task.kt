package ru.job4j.todokt.db

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author Dmitry Kolganov (mailto:dmk78@inbox.ru)
 * @version $Id$
 * @since 12.02.2020
 */

@Entity(tableName = "tasks")
data class Task(
    @field:PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var name: String?,
    var desc: String,
    var created: String? = "",
    var closed: String = ""
) {
    init {
        val df: DateFormat = SimpleDateFormat("EEE, d MMM yyyy, HH:mm")
        this.created = df.format(Calendar.getInstance().time)
        this.closed = closed
    }

}