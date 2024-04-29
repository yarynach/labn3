package com.nulp.labn3

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.DatePicker
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.Calendar


class MainActivity : AppCompatActivity() {
    val appointmentsTypes= arrayOf("Type 1", "Type 2", "Type 3", "Type 4")
    val age= (1..100).toList()



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

       /* //set available hours
        val rootLayout = findViewById<GridLayout>(R.id.root_layout)

        var pickedButton: Button? = null

        for (i in 0 until rootLayout.childCount) {
            val button = rootLayout.getChildAt(i) as Button
            button.setOnClickListener {
                pickedButton?.setBackgroundResource(R.drawable.hour)
                button.setBackgroundResource(R.drawable.hour_picked)
                pickedButton = button
            }
        }*/

        //set pop up
        val setButton: Button = findViewById(R.id.btn_set)
        val popupConfirmation: Dialog = Dialog(this)
        setButton.setOnClickListener{
            popupConfirmation.setContentView(R.layout.popup)
            popupConfirmation.getWindow()?.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
            popupConfirmation.show()

        }
        ///rec view
        recyclerView = findViewById(R.id.my_rv)
        gridLayoutManager = GridLayoutManager(applicationContext,4,LinearLayoutManager.VERTICAL,false)
        recyclerView?.layoutManager=gridLayoutManager
        recyclerView?.setHasFixedSize(true)

        hoursAdapter=HoursAdapter(hours!!)
        recyclerView?.adapter=hoursAdapter

        hoursAdapter!!.onItemClick = { clickedHour ->
            // Deselect all items
            for (hour in hours) {
                hour.selected = false
            }
            // Select the clicked item
            clickedHour.selected = true
            // Notify adapter that data has changed
            hoursAdapter?.notifyDataSetChanged()
        }




    }
    private fun setDataInList(): ArrayList<Hour> {
        var items:ArrayList<Hour> =ArrayList()
        for (hour in hours!!) {
            items.add(hour)
        }
        return items
    }

}
