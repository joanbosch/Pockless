package com.pes.pockles.view.ui.login.register

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.pes.pockles.R
import com.pes.pockles.model.CreateUser
import com.pes.pockles.util.livedata.Event
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class RegisterActivityViewModel @Inject constructor() : ViewModel() {

    init {
        Timber.d("RegisterActivityViewModel init")
    }

    val user = MutableLiveData<CreateUser>()

    private val _nextRegister = MutableLiveData<Event<Boolean>>()
    val nextRegister: LiveData<Event<Boolean>>
        get() = _nextRegister

    private val _nextRegisterError = MutableLiveData<RegisterActivityUiModel>()
    val nextRegisterError: LiveData<RegisterActivityUiModel>
        get() = _nextRegisterError

    init {
        val u = FirebaseAuth.getInstance().currentUser
        u?.let {
            user.value =
                CreateUser(
                    id = it.uid,
                    name = it.displayName,
                    birthDate = null,
                    mail = it.email!!,
                    profileImageUrl = "https://i.guim.co.uk/img/media/7b9255fb199d680ae7f8568bcdb3192b3d8c1e6b/0_35_3720_4649/master/3720.jpg?width=300&quality=85&auto=format&fit=max&s=1557dfc9dcbe713524ef1c302b0a807d",
                    radiusVisibility = 1F, //km
                    accentColor = "#3f51b5"
                )

        }
    }

    fun next(v: View) {
        val u = user.value!!
        var error = false

        if (u.name.isNullOrEmpty()) {
            _nextRegisterError.value =
                RegisterActivityUiModel(
                    key = RegisterActivityUiFields.NAME_FIELD,
                    error = R.string.field_must_not_be_empty
                )
            error = true
        }

        if (u.birthDate.isNullOrEmpty()) {
            _nextRegisterError.value =
                RegisterActivityUiModel(
                    key = RegisterActivityUiFields.BIRTH_DATE_FIELD,
                    error = R.string.field_must_not_be_empty
                )
            error = true
        } else {
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.YEAR, -13)
            val date = sdf.parse(u.birthDate!!)
            if (date != null && date > calendar.time) {
                _nextRegisterError.value =
                    RegisterActivityUiModel(
                        key = RegisterActivityUiFields.BIRTH_DATE_FIELD,
                        error = R.string.too_young
                    )
                error = true
            }
        }

        if (!error) {
            _nextRegister.value = Event(true)
        }

    }

    fun setVisibility(progressFloat: Float) {
        val u = user.value
        u?.let {
            it.radiusVisibility = progressFloat
            user.value = it
        }
    }

    fun setColor(color: Int) {
        val u = user.value
        u?.let {
            it.accentColor = String.format("#%06X", 0xFFFFFF and color)
            user.value = it
        }
    }

    fun setBirthDate(time: Long) {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val u = user.value
        u?.let {
            it.birthDate = sdf.format(time)
            user.value = it
        }
    }

    data class RegisterActivityUiModel(
        val key: RegisterActivityUiFields,
        val error: Int
    )

    enum class RegisterActivityUiFields {
        NAME_FIELD,
        BIRTH_DATE_FIELD
    }
}