package com.mustafa.alarmapp

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private  lateinit var timePicker:TimePickerDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnAlarm.setOnClickListener {
                openDialogTime(is24Hours = false)
        }

    }

    private  fun openDialogTime(is24Hours:Boolean){
            val calender = Calendar.getInstance()
           timePicker = TimePickerDialog(this,TimePickerDialog.OnTimeSetListener { timePicker, i, i2 ->

               var calNow = Calendar.getInstance()
               var calSet = calNow.clone() as Calendar

               calSet.set(Calendar.HOUR_OF_DAY,i)
               calSet.set(Calendar.MINUTE,i2)
               calSet.set(Calendar.SECOND,0)
               calSet.set(Calendar.MILLISECOND,0)
               if (calSet.compareTo(calNow)<=0){
                   calSet.add(Calendar.DATE,1)
               }
               setAlarm(calSet)

           },calender.get(Calendar.HOUR_OF_DAY),calender.get(Calendar.MINUTE),is24Hours)
        timePicker.setTitle("Alarm Kur")
        timePicker.show()
    }


    private  fun setAlarm(calender:Calendar){
        Toast.makeText(this,"Alarm Ayarlandı",Toast.LENGTH_SHORT).show()
        var intent = Intent(this@MainActivity,AlarmReceiver::class.java)
        var pendingIntent = PendingIntent.getBroadcast(this@MainActivity,1,intent,0)
        var alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,calender.timeInMillis,pendingIntent)
    }




}