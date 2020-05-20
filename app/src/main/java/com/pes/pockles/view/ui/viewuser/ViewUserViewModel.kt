package com.pes.pockles.view.ui.viewuser

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pes.pockles.R
import com.pes.pockles.data.Resource
import com.pes.pockles.data.repository.UserRepository
import com.pes.pockles.model.User
import com.pes.pockles.util.livedata.Event
import javax.inject.Inject

class ViewUserViewModel @Inject constructor(
    //private var useCase: ViewUserUseCase,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _internalUser = MediatorLiveData<Resource<User>>()

    val user: LiveData<Resource<User>>
        get() = _internalUser

    private val _errorMsg = MutableLiveData<Int>()
    val errorMsg: LiveData<Int>
        get() = _errorMsg

    init {
        _internalUser.value = Resource.Loading<Nothing>()
        _errorMsg.value = null
    }
/*
    fun loadUser(userId: String) {
        _internalUser.addSource(useCase.execute(userId)) {
            _internalUser.value = it
            if (it.failed) {
                _errorMsg.value = R.string.error_no_user
            }
        }
    }
*/
    fun getUser(): User? {
        return if (_internalUser.value is Resource.Success<User>) {
            (_internalUser.value as Resource.Success<User>).data
        } else {
            null
        }
    }
}