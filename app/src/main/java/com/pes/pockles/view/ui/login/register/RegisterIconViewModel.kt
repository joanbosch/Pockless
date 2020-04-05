package com.pes.pockles.view.ui.login.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pes.pockles.data.Resource
import com.pes.pockles.data.repository.UserRepository
import com.pes.pockles.model.CreateUser
import com.pes.pockles.model.User
import com.pes.pockles.util.livedata.Event
import javax.inject.Inject

class RegisterIconViewModel @Inject constructor(
    private var userRepository: UserRepository
) : ViewModel() {

    val user = MutableLiveData<CreateUser>()

    fun setUser(user: CreateUser) {
        this.user.value = user
    }

    fun registerUser(): LiveData<Event<Boolean>> {
        val mediatorLiveData = MediatorLiveData<Event<Boolean>>()

        user.value?.let {
            mediatorLiveData.addSource(userRepository.createUser(it)) { resource ->
                when (resource) {
                    is Resource.Success<User> -> {
                        userRepository.saveUser(resource.data)
                        mediatorLiveData.value = Event(true)
                    }
                    is Resource.Error -> mediatorLiveData.value = Event(false)
                }
            }
        }
        return mediatorLiveData
    }
}