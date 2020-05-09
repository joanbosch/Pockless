package com.pes.pockles.model

data class Chat(
    val id: String,
    val user1: String,
    val user2: ChatUser,
    val pock: String,
    val lastMessage: String,
    val date: Long
)