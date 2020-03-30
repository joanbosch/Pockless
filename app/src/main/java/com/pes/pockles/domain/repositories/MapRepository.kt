package com.pes.pockles.domain.repositories

import androidx.lifecycle.LiveData
import com.pes.pockles.data.Resource
import com.pes.pockles.model.Location
import com.pes.pockles.model.Pock

interface MapRepository {
    fun getPocks(loc:Location): LiveData<Resource<List<Pock>>>
}