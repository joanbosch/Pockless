package com.pes.pockles.view.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pes.pockles.data.repository.UserRepository
import com.pes.pockles.domain.usecases.LogoutUseCase
import com.pes.pockles.model.User
import com.pes.pockles.util.livedata.Event
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    var repository: UserRepository,
    var logoutUseCase: LogoutUseCase
) : ViewModel() {

    private val _navigateToHistory = MutableLiveData<Event<Boolean>>()
    val navigateToHistory: LiveData<Event<Boolean>>
        get() = _navigateToHistory

    val user: LiveData<User> = repository.getUser()

    fun navigateToHistoryOnClick() {
        _navigateToHistory.value = Event(true)
    }

    fun logout() {
        logoutUseCase.execute()
    }
}