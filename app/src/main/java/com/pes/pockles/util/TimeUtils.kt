package com.pes.pockles.util

import com.pes.pockles.model.Chat
import com.pes.pockles.model.Message
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
    @JvmStatic
    fun getChatTime(chat: Chat): String {
        val df: DateFormat = SimpleDateFormat(
            "dd-MMM HH:mm",
            Locale.getDefault()
        )
        return try {
            df.format(chat.date)
        } catch (ignore: Exception) {
            ""
        }
    }
    @JvmStatic
    fun getMessageTime(msg: Message): String {
        val df: DateFormat = SimpleDateFormat(
            "HH:mm",
            Locale.getDefault()
        )
        return try {
            df.format(msg.date)
        } catch (ignore: Exception) {
            ""
        }
    }
}