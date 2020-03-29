package com.pes.pockles.view.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pes.pockles.domain.usecases.ViewPockUseCase
import com.pes.pockles.model.Pock
import com.pes.pockles.data.Resource

class ViewPockViewModel : ViewModel(){

    private val _pockView = MediatorLiveData<Resource<Pock>>()
    val pockView: LiveData<Resource<Pock>>
    get() = _pockView

    private val _pockMessage = MutableLiveData<String>()
    val pockMessage: LiveData<String>
        get() = _pockMessage

    private val _pockAuthor = MutableLiveData<String>()
    val pockAuthor: LiveData<String>
        get() = _pockAuthor

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
        _pockMessage.value = ""
        _pockAuthor.value = ""
        _goBack.value = false
        _goShare.value = false
        _goReport.value = false
        _goChat.value = false

    }

    public fun loadPock(pock: Pock) {
        val useCase: ViewPockUseCase by lazy {
            ViewPockUseCase();
        }
        _pockMessage.value = pock.message
        //_pockAuthor.value = pock.user (pock.user is not implemented yet)
        //For the moment just to test
        _pockAuthor.value = "Carlos"
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

    fun  onChat() {
        _goChat.value = true
    }

}