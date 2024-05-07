package com.nulp.labn3.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface NotificationsDAO {

    @Query("UPDATE notifications SET status = :newStatus WHERE id_appointment = :appointmentId")
    fun updateStatus(appointmentId: Int, newStatus: String)

    @Update
    fun update(notification: Notifications)

    @Delete
    fun delete(notification: Notifications)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(notification: Notifications)
}