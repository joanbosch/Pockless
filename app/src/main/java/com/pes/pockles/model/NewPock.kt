package com.pes.pockles.model

data class NewPock(
    val message: String,
    val category: String,
    val chatAccess: Boolean,
    val location: Location
)