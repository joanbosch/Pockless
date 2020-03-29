package com.pes.pockles.domain.repositories

import androidx.lifecycle.LiveData
import com.pes.pockles.data.Resource
import com.pes.pockles.model.NewPock
import com.pes.pockles.model.Pock

interface PockRepository {
    fun newPock(pock: NewPock): LiveData<Resource<Pock>>
}