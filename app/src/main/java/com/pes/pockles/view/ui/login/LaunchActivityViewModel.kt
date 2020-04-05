package com.pes.pockles.view.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.pes.pockles.data.Resource
import com.pes.pockles.data.repository.UserRepository
import com.pes.pockles.model.CreateUser
import com.pes.pockles.model.User
import com.pes.pockles.util.livedata.Event
import javax.inject.Inject

class LaunchActivityViewModel @Inject constructor(
    private var userRepository: UserRepository
) : ViewModel() {

    fun userExists(uid: String): LiveData<Resource<Boolean>> {
        return userRepository.userExists(uid)
    }
}