package com.auto.surelabs.ltimonitoring.utils

import android.util.Log
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


object HourToMillis {

    fun millis(): Long {
        val time = Calendar.getInstance(Locale.ENGLISH)
        return time.timeInMillis
    }

    fun millisKurangBerapaHari(kurang: Int?): Long {
        val time = Calendar.getInstance(Locale.ENGLISH)
        kurang?.let { time.add(Calendar.DAY_OF_MONTH, it) }
        return time.timeInMillis
    }

    fun addExpired(expiredOn: Int?): Long {
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.ENGLISH)
        val now = sdf.format(Date())

        val date = sdf.parse(now)
        val c = Calendar.getInstance()
        Log.i("simple date format", date.toString())
        c.time = date
        expiredOn?.let { c.add(Calendar.MINUTE, it) }

        Log.i("Time", c.time.toString())

        return c.timeInMillis
    }

    fun millisToDate(milis: Long, dateFormat: String = "dd/MM/yyyy"): String {
        val sdf = SimpleDateFormat(dateFormat, Locale.ENGLISH)
        val c = Calendar.getInstance()
        c.timeInMillis = milis
        return sdf.format(c.time)
    }

    fun millisToDateHour(milis: Long): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.ENGLISH)
        val c = Calendar.getInstance()
        c.timeInMillis = milis
        return sdf.format(c.time)
    }

    fun millisToMonth(milis: Long): String {
        val sdf = SimpleDateFormat("MM", Locale.ENGLISH)
        val c = Calendar.getInstance()
        c.timeInMillis = milis
        return sdf.format(c.time)
    }

    fun millisToHour(milis: Long): String {
        val sdf = SimpleDateFormat("HH:mm", Locale.ENGLISH)
        val c = Calendar.getInstance()
        c.timeInMillis = milis
        return sdf.format(c.time)
    }

    fun dateHourToMillist(date: String): Long? {
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.ENGLISH)
        var milist: Long? = null
        try {
            val mDate = sdf.parse(date)
            milist = mDate.time
            Log.d("DATE", milist.toString())
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return milist
    }
}