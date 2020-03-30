package com.pes.pockles.model

data class Pock(
    val id: String,
    val message: String,
    val category: String,
    val chatAccess: Boolean,
    val location: Location,
    val dateInserted : Long = 0L
)
