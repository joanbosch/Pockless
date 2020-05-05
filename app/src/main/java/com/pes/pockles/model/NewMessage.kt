package com.pes.pockles.model

data class NewMessage(
    val text: String,
    val chatId: String?,
    val pockId: String?
)