package com.nulp.labn3.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import java.time.LocalDate
import java.time.LocalTime

@Dao
interface AppointmentsDAO {
    @Query("SELECT * FROM appointments")
    fun getAll(): List<Appointments>

    @Query("SELECT * " +
            "FROM appointments " +
            "JOIN notifications ON appointments.uid = notifications.id_appointment " +
            "WHERE notifications.status = 'upcoming' " +
            "AND time(appointments.time) > time(:currentTime) " +
            "AND date(appointments.date) >= date(:currentDate)" +
            "ORDER BY appointments.date ASC, appointments.time ASC")
    fun getUpcoming(currentDate: LocalDate, currentTime: LocalTime): List<Appointments>

    @Query("SELECT * " +
            "FROM appointments " +
            "JOIN notifications ON appointments.uid = notifications.id_appointment " +
            "WHERE appointments.date = :currentDate" +
            " AND notifications.status = 'upcoming'" +
            "AND time(appointments.time) > time(:currentTime) " +
            "ORDER BY appointments.date DESC, appointments.time ASC limit 1")
    fun getFirstUpcoming(currentDate: LocalDate,  currentTime: LocalTime): List<Appointments>


    @Query("SELECT * " +
            "FROM appointments " +
            "JOIN notifications ON appointments.uid = notifications.id_appointment " +
            "WHERE notifications.status = 'cancelled' " +
            "ORDER BY appointments.date DESC, appointments.time ASC limit 1")
    fun getCancelled(): List<Appointments>


    @Update
    fun update(appointment: Appointments)

    @Delete
    fun delete(appointment: Appointments)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(appointment: Appointments)
}