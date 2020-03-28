package com.pes.pockles.model

import java.util.*

data class Pock(
    val message: String,
    val category: String,
    val chatAccess: Boolean,
    val location: Location,
    val dateInserted : Long = 0L
)
