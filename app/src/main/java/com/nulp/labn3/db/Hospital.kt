package com.nulp.labn3.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Appointments::class, Notifications::class], version=2)
@TypeConverters(Converters::class)
abstract class Hospital:RoomDatabase() {
    abstract fun appointmentsDAO(): AppointmentsDAO
    abstract fun notificationsDAO(): NotificationsDAO



}