package com.pes.pockles.domain.repositories

import androidx.lifecycle.LiveData
import com.pes.pockles.data.Resource
import com.pes.pockles.model.Location
import com.pes.pockles.model.NewPock
import com.pes.pockles.model.Pock

interface PockRepository {
    fun newPock(pock: NewPock): LiveData<Resource<Pock>>
    fun getPocks(loc: Location): LiveData<Resource<List<Pock>>>
    fun getViewPock(id: String): LiveData<Resource<Pock>>
}