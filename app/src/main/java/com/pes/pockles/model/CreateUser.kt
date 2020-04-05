package com.pes.pockles.model

data class CreateUser(
    val id: String,
    val name: String,
    val birthDate: Int,
    val mail: String,
    val profileImageUrl: String,
    val radiusVisibility: Int,
    val accentColor: String
)