package com.pes.pockles.domain.usecases

import androidx.lifecycle.LiveData
import com.pes.pockles.data.Resource
import com.pes.pockles.data.repository.UserRepository
import com.pes.pockles.model.EditedUser
import com.pes.pockles.model.User
import javax.inject.Inject

class EditProfileUseCase @Inject constructor(var pockRepository: UserRepository) {
    fun execute(profile: EditedUser): LiveData<Resource<User>> {
        return pockRepository.editProfile(profile)
    }
}