package com.pes.pockles.view.ui.viewuser

import android.provider.Settings.Global.getString
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.snackbar.Snackbar
import com.pes.pockles.R
import com.pes.pockles.data.Resource
import com.pes.pockles.data.failed
import com.pes.pockles.data.repository.UserRepository
import com.pes.pockles.domain.usecases.ViewUserUseCase
import com.pes.pockles.model.User
import com.pes.pockles.model.ViewUser
import com.pes.pockles.util.livedata.Event
import javax.inject.Inject

class ViewUserViewModel @Inject constructor(
    private var useCase: ViewUserUseCase,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _user = MutableLiveData<ViewUser>()
    val user: LiveData<ViewUser>
        get() = _user

    fun getUser(userId: String): LiveData<Resource<ViewUser>>{
        return useCase.execute(userId)
    }

    fun setUser(data: ViewUser?) {
        _user.value = data
    }

}