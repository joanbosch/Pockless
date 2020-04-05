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

class RegisterActivityViewModel @Inject constructor(
    private var userRepository: UserRepository
) : ViewModel() {

    fun registerUser(color: String, birthDate: Int, radius: Int): LiveData<Event<Boolean>> {
        val mediatorLiveData = MediatorLiveData<Event<Boolean>>()

        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            val createUser = CreateUser(
                id = it.uid,
                name = it.displayName!!,
                birthDate = birthDate,
                mail = it.email!!,
                profileImageUrl = "",
                radiusVisibility = radius, //km
                accentColor = color
            )

            mediatorLiveData.addSource(userRepository.createUser(createUser)) { resource ->
                when (resource) {
                    is Resource.Success<User> -> {
                        userRepository.saveUser(resource.data)
                        mediatorLiveData.value = Event(true)
                    }
                    is Resource.Error -> mediatorLiveData.value = Event(false)
                }
            }
        }

        if (user == null) {
            mediatorLiveData.value = Event(false)
        }

        return mediatorLiveData
    }
}