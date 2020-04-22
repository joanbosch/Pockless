package com.pes.pockles.view.ui.viewpock

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.pes.pockles.data.Resource
import com.pes.pockles.domain.usecases.ViewPockUseCase
import com.pes.pockles.model.Pock
import javax.inject.Inject

class ViewPockViewModel @Inject constructor(
    private var useCase: ViewPockUseCase
) : ViewModel() {

    fun loadPock(pockId: String): LiveData<Resource<Pock>> {
        return useCase.execute(pockId)
    }

}