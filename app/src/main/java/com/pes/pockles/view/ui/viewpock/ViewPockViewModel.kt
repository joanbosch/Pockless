package com.pes.pockles.view.ui.viewpock

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pes.pockles.data.Resource
import com.pes.pockles.domain.usecases.ViewPockUseCase
import com.pes.pockles.model.Pock
import javax.inject.Inject

class ViewPockViewModel @Inject constructor(
    private var useCase: ViewPockUseCase
) : ViewModel() {

    private val _goShare = MutableLiveData<Boolean>()
    val goShare: LiveData<Boolean>
        get() = _goShare

    private val _goReport = MutableLiveData<Boolean>()
    val goReport: LiveData<Boolean>
        get() = _goReport

    private val _goChat = MutableLiveData<Boolean>()
    val goChat: LiveData<Boolean>
        get() = _goChat

    private val _goBack = MutableLiveData<Boolean>()
    val goBack: LiveData<Boolean>
        get() = _goBack

    init {
        _goBack.value = false
        _goShare.value = false
        _goReport.value = false
        _goChat.value = false

    }

    fun loadPock(pockId: String): LiveData<Resource<Pock>> {
        return useCase.execute(pockId)
    }

    fun onBack() {
        _goBack.value = true
    }

    fun onShare() {
        _goShare.value = true
    }

    fun onReport() {
        _goReport.value = true
    }

    fun onChat() {
        _goChat.value = true
    }

}