package com.pes.pockles.view.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.pes.pockles.data.Resource
import com.pes.pockles.data.TokenManager
import com.pes.pockles.data.repository.UserRepository
import timber.log.Timber
import javax.inject.Inject

class LaunchActivityViewModel @Inject constructor(
    private var userRepository: UserRepository,
    private val tokenManager: TokenManager
) : ViewModel() {

    fun userExists(uid: String): LiveData<Resource<Boolean>> {
        return userRepository.userExists(uid)
    }

    fun loadUser() {
        return userRepository.reloadUser()
    }

    fun saveToken() {
        tokenManager.refreshToken()
    }

    fun saveFCMToken() {
        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    return@OnCompleteListener
                }

                // Get new Instance ID token
                val token = task.result?.token
                userRepository.insertFCMToken(token!!)
            })

    }
}