package com.pes.pockles.domain.repositories

import androidx.lifecycle.LiveData
import com.bumptech.glide.load.engine.Resource
import com.pes.pockles.model.User

interface UserRepository {
    fun getUser(): LiveData<User>
    fun uploadUser(user: User): LiveData<Resource<Boolean>>
    fun reloadUser(): Unit
}