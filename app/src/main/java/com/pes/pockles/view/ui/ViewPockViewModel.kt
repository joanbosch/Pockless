package com.pes.pockles.view.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ViewPockViewModel : ViewModel(){

    private var pockId: String = ""

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

    public fun loadPock(id: String) {
        //GetPock(id)
        pockId = id
        _pockMessage.value = "The message is load"
        _pockAuthor.value = "Pau Dastis"
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