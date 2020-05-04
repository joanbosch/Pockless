package com.pes.pockles.view.ui.editprofile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pes.pockles.data.storage.StorageManager
import com.pes.pockles.domain.usecases.EditProfileUseCase
import com.pes.pockles.model.EditedUser
import javax.inject.Inject

class EditProfileViewModel @Inject constructor(
    private var useCase: EditProfileUseCase,
    private val storageManager: StorageManager
) : ViewModel() {

    private val _mail = MutableLiveData<String>()
        val mail: LiveData<String>
        get() = _mail

    private val _birthDate = MutableLiveData<String>()
    val birthDate: LiveData<String>
        get() = _birthDate

    private var _editableContent = MutableLiveData<EditedUser>()
    val editableContent: LiveData<EditedUser>
        get() = _editableContent

    fun loadContent(mail: String, birthDate: String, editableContent: EditedUser) {
        _mail.value = mail
        _birthDate.value = birthDate
        _editableContent.value = editableContent
    }

    fun setVisibility(progressFloat: Float) {
        val u = editableContent.value
        u?.let {
            it.radiusVisibility = progressFloat
            _editableContent.value = it
        }
    }

    fun setColor(color: Int) {
        val u = editableContent.value
        u?.let {
            it.accentColor = String.format("#%06X", 0xFFFFFF and color)
            _editableContent.value = it
        }
    }



}