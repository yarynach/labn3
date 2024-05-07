package com.nulp.labn3.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "notifications", foreignKeys = [ForeignKey(entity = Appointments::class, parentColumns = ["uid"], childColumns = ["id_appointment"], onDelete = ForeignKey.CASCADE)])
data class Notifications(
    @PrimaryKey(autoGenerate = true) val uid:Int,
    @ColumnInfo(name="id_appointment") val idAppointment: Int,
    @ColumnInfo(name="status") val status: String

    )
