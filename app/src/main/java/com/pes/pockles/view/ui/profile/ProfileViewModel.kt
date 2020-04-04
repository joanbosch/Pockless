package com.pes.pockles.view.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.pes.pockles.data.repository.implementation.UserRepositoryImpl
import com.pes.pockles.domain.repositories.UserRepository
import com.pes.pockles.model.User

class ProfileViewModel : ViewModel() {

    private val repository: UserRepository by lazy { UserRepositoryImpl() }

    val user: LiveData<User> = repository.getUser()
}