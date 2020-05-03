package com.pes.pockles.view.ui.editprofile

import androidx.lifecycle.ViewModel
import com.pes.pockles.data.storage.StorageManager
import com.pes.pockles.domain.usecases.EditProfileUseCase
import javax.inject.Inject

class EditProfileViewModel @Inject constructor(
    private var useCase: EditProfileUseCase,
    private val storageManager: StorageManager
) : ViewModel() {

}