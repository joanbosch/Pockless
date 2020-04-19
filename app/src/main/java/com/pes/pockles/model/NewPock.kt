package com.pes.pockles.model

data class NewPock(
    val message: String,
    val media: String?,
    val media2: String?,
    val media3: String?,
    val media4: String?,
    val category: String,
    val chatAccess: Boolean,
    val location: Location
)