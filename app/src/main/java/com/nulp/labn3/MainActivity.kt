package com.nulp.labn3

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.DatePicker
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nulp.labn3.adapters.Hour
import com.nulp.labn3.adapters.HoursAdapter
import com.nulp.labn3.db.Appointments
import com.nulp.labn3.db.DatabaseManager
import com.nulp.labn3.db.Hospital
import com.nulp.labn3.db.Notifications
import java.time.LocalDate
import java.time.LocalTime
import java.util.Calendar


class MainActivity : AppCompatActivity() {
    val appointmentsTypes= arrayOf("Type 1", "Type 2", "Type 3", "Type 4")
    val age= (1..100).toList()
    lateinit var db: Hospital



    //hours
    val hours: ArrayList<Hour> = arrayListOf(
        Hour("09:00"),
        Hour("10:00"),
        Hour("11:00"),
        Hour("12:00"),
        Hour("13:00"),
        Hour("14:00"),
        Hour("15:00"),
        Hour("16:00"),
        Hour("17:00"),
        Hour("18:00")
    )
    private var recyclerView: RecyclerView? = null
    private var gridLayoutManager: GridLayoutManager? = null
    private var hoursAdapter: HoursAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //set spinner for age
        val spinner1:Spinner = findViewById(R.id.spinner_age)
        val arrayAdapter1 = ArrayAdapter<Int>(this,android.R.layout.simple_spinner_dropdown_item,age)
        spinner1.adapter = arrayAdapter1
        spinner1.onItemSelectedListener=object:AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Toast.makeText(applicationContext,"Age:"+age[position], Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                if (appointmentsTypes.isNotEmpty()) {
                    spinner1.setSelection(0)
                }
            }

        }

        //set spinner for appointments
        val spinner2:Spinner = findViewById(R.id.spinner_appointment)
        val arrayAdapter = ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,appointmentsTypes)
        spinner2.adapter = arrayAdapter
        spinner2.onItemSelectedListener=object:AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Toast.makeText(applicationContext,"Appointment type is"+appointmentsTypes[position], Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                if (appointmentsTypes.isNotEmpty()) {
                    spinner2.setSelection(0)
                }
            }

        }
        //calendar
        val btnPickDate: Button = findViewById(R.id.btn_pickDate)
        btnPickDate.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this,
                { view: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                    val pickedDate = "$dayOfMonth/${monthOfYear + 1}/$year"
                    btnPickDate.setText(pickedDate)
                }, year, month, day)

            datePickerDialog.show()
        }

        //  dialog
        val setButton: Button = findViewById(R.id.btn_set)
        val popupConfirmation: Dialog = Dialog(this)

        setButton.setOnClickListener{
            popupConfirmation.setContentView(R.layout.popup)
            popupConfirmation.getWindow()?.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);

            val btnBackToHome: Button = popupConfirmation.findViewById(R.id.btn_home)
            btnBackToHome.setOnClickListener {

                val intent = Intent(this, MainActivity::class.java) // Replace HomeActivity::class.java with your home screen activity
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
                popupConfirmation.dismiss()
            }

            popupConfirmation.show()
        }
        // to db_activity
        val backButton: ImageView=findViewById(R.id.arrow_left)
        backButton.setOnClickListener{
            val intent = Intent(this, db_activity::class.java)
            startActivity(intent)
        }

        ///rec view
        recyclerView = findViewById(R.id.my_rv)
        gridLayoutManager = GridLayoutManager(applicationContext,4,LinearLayoutManager.VERTICAL,false)
        recyclerView?.layoutManager=gridLayoutManager
        recyclerView?.setHasFixedSize(true)

        hoursAdapter= HoursAdapter(hours!!)
        recyclerView?.adapter=hoursAdapter

        hoursAdapter!!.onItemClick = { clickedHour ->
            for (hour in hours) {
                hour.selected = false
            }
            clickedHour.selected = true
            hoursAdapter?.notifyDataSetChanged()
        }

        var dbManager = DatabaseManager.getInstance(this)
        db = dbManager.getDatabase()
        testDB()

    }

    private fun testDB(){
        testInsert()
        testRead()
        testDelete()
        testUpdate()
        testRead()
    }

    fun writeMessage(message:String){
        Log.d("TEST",message)
    }

    private fun testUpdate() {
        writeMessage("Update")
        val dao = db.appointmentsDAO()
        val appointment2 = Appointments(2,"NEW Name Surname2", 24,"Consultation", LocalDate.parse("2024-05-10"),"17:00")
        dao.update(appointment2)
    }

    private fun testDelete() {
        writeMessage("Delete")
        val dao = db.appointmentsDAO()
        val app1 = Appointments(1,"Name Surname", 18,"Consultation", LocalDate.parse("2018-12-12"),"15:00")
        dao.delete(app1)

    }

    private fun testRead() {
        writeMessage("read")
        val dao = db.appointmentsDAO()
        val appointments = dao.getAll()
        for (app in appointments){
            writeMessage("$app")
        }

        val upcoming = dao.getUpcoming( LocalDate.parse("2024-05-07"),LocalTime.parse("18:00"))
        for (app in upcoming){
            writeMessage("$app")
        }
    }

    private fun testInsert() {
        var id: Int
        writeMessage("Insert")
        val dao = db.appointmentsDAO()
        val daoNotif = db.notificationsDAO()
        val appointment1 = Appointments(1,"Name Surname", 18,"Consultation", LocalDate.parse("2024-05-09"),"23:00")

        dao.insert(appointment1)
        val notif1= Notifications(1,1,"upcoming")
        daoNotif.insert(notif1)

        val appointment2 = Appointments(2,"Name Surname2", 24,"Consultation", LocalDate.parse("2024-05-09"),"23:30")
        dao.insert(appointment2)
        val notif2= Notifications(2,2,"cancelled")
        daoNotif.insert(notif2)

        val appointment3 = Appointments(3,"Name Surname3", 24,"Consultation", LocalDate.parse("2024-05-09"),"22:45")
        dao.insert(appointment3)
        val notif3= Notifications(3,3,"upcoming")
        daoNotif.insert(notif3)

        val appointment4 = Appointments(4,"Name Surname4 canc", 24,"Consultation", LocalDate.parse("2024-10-05"),"23:30")
        dao.insert(appointment4)
        val notif4= Notifications(4,4,"upcoming")
        daoNotif.insert(notif4)

        val appointment5 = Appointments(5,"Name Surname3", 24,"Consultation", LocalDate.parse("2024-05-09"),"23:30")
        dao.insert(appointment5)
        val notif5= Notifications(5,5,"upcoming")
        daoNotif.insert(notif5)
    }





}
