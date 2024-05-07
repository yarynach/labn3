package com.nulp.labn3.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Date

@Entity(tableName = "appointments")
data class Appointments(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "full_name") val fullName: String?,
    @ColumnInfo(name = "age") val age: Int,
    @ColumnInfo(name = "appointment_type") val appointmentType: String?,
    @ColumnInfo(name = "date") val date: LocalDate,
    @ColumnInfo(name = "time") val time: String
)