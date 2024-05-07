package com.nulp.labn3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nulp.labn3.adapters.TableRowAdapter
import com.nulp.labn3.db.Appointments
import com.nulp.labn3.db.DatabaseManager
import com.nulp.labn3.db.Hospital
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class db_activity : AppCompatActivity() {

    private lateinit var tableRecyclerView: RecyclerView
    private lateinit var tableRowAdapter: TableRowAdapter


    private lateinit var tableRecyclerView2: RecyclerView
    private lateinit var tableRowAdapter2: TableRowAdapter

    private lateinit var db: Hospital
    private lateinit var dbManager: DatabaseManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_screen)

        dbManager = DatabaseManager.getInstance(this)
        db = dbManager.getDatabase()


        var currentDate = LocalDate.now()
        var currentTime= LocalTime.now()

        var date: TextView = findViewById(R.id.current_date)
        val upcomingEvent:List<Appointments> = db.appointmentsDAO().getFirstUpcoming(currentDate,currentTime)
        date.text = currentDate.toString()

        var time: TextView = findViewById(R.id.txt_appointment_time)
        var type: TextView = findViewById(R.id.txt_appointment_type)
        var patient: TextView = findViewById(R.id.txt_appointment_patient)

        if (!upcomingEvent.isEmpty()){
            time.text= upcomingEvent[0].time
            type.text=upcomingEvent[0].appointmentType.toString()
            patient.text=upcomingEvent[0].fullName.toString()
        }else{
            type.text="No events"
            patient.text=""

        }





        val appointmentsList:List<Appointments> = db.appointmentsDAO().getUpcoming(currentDate,currentTime)
        println(appointmentsList)

        tableRecyclerView = findViewById(R.id.rv_table)
        tableRowAdapter = TableRowAdapter(appointmentsList)

        tableRecyclerView.layoutManager = LinearLayoutManager(this)
        tableRecyclerView.adapter = tableRowAdapter


        val appointmentsList2:List<Appointments> = db.appointmentsDAO().getCancelled()
        println(appointmentsList)

        tableRecyclerView2 = findViewById(R.id.rv_table2)
        tableRowAdapter2 = TableRowAdapter(appointmentsList2)

        tableRecyclerView2.layoutManager = LinearLayoutManager(this)
        tableRecyclerView2.adapter = tableRowAdapter2






    }
}