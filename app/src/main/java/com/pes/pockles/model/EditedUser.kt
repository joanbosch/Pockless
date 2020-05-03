package com.pes.pockles.model

import java.io.Serializable

data class EditedUser(
    val name: String,
    val profileImage: String,
    val radiusVisibility: Float,
    val accentColor: String
): Serializable