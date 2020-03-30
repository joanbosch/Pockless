package com.pes.pockles.util

import com.pes.pockles.model.Pock
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


object TimeUtils {
    @JvmStatic
    fun getPockTime(pock: Pock): String {
        val df: DateFormat = SimpleDateFormat(
            "dd-MMM-yyyy HH:mm",
            Locale.getDefault()
        )
        return try {
            df.format(pock.dateInserted)
        } catch (ignore: Exception) {
            ""
        }
    }
}