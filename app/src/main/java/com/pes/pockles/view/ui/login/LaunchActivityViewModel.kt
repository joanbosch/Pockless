package com.pes.pockles.view.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.pes.pockles.data.Resource
import com.pes.pockles.data.repository.UserRepository
import javax.inject.Inject

class LaunchActivityViewModel @Inject constructor(
    private var userRepository: UserRepository
) : ViewModel() {

    fun userExists(uid: String): LiveData<Resource<Boolean>> {
        return userRepository.userExists(uid)
    }

    fun loadUser() {
        return userRepository.reloadUser()
    }
}