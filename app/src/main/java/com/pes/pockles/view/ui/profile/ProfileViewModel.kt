package com.pes.pockles.view.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pes.pockles.data.repository.UserRepository
import com.pes.pockles.model.User
import com.pes.pockles.util.livedata.Event
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    repository: UserRepository
) : ViewModel() {

    private val _navigateToHistory = MutableLiveData<Event<Boolean>>()
    val navigateToHistory: LiveData<Event<Boolean>>
        get() = _navigateToHistory

    val user: LiveData<User> = repository.getUser()

    fun navigateToHistory() {
        _navigateToHistory.value = Event(true)
    }
}