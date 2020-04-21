package com.auto.surelabs.ltimonitoring.utils

import android.content.Context
import android.util.DisplayMetrics


object Constant {
    fun dateFormat(input: Int): String {
        return String.format("%02d", input)
    }

    fun calculateNoOfColumns(
        context: Context,
        columnWidthDp: Float
    ): Int { // For example columnWidthdp=180
        val displayMetrics: DisplayMetrics = context.resources.displayMetrics
        val screenWidthDp = displayMetrics.widthPixels / displayMetrics.density
        return (screenWidthDp / columnWidthDp + 0.5).toInt()
    }
}