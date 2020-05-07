package com.pes.pockles.view.ui.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.pes.pockles.data.Resource
import com.pes.pockles.data.loading
import com.pes.pockles.domain.usecases.ChatMessagesUseCase
import com.pes.pockles.domain.usecases.NewMessageUseCase
import com.pes.pockles.model.Message
import com.pes.pockles.model.NewMessage
import javax.inject.Inject

class ChatViewModel @Inject constructor(
    var useCase: ChatMessagesUseCase,
    var useCaseNewMessage: NewMessageUseCase
) : ViewModel() {

    val messages: LiveData<Resource<List<Message>>>
        get() = _messages

    private val _messages = MediatorLiveData<Resource<List<Message>>>()

    val newMsg: LiveData<Resource<Message>>
        get() = _newMsg

    private val _newMsg = MediatorLiveData<Resource<Message>>()


    fun postMessage(message: NewMessage) {
        var newmessage = useCaseNewMessage.execute(message)
        _newMsg.addSource(newmessage){
            _newMsg.value = it
            if(!it.loading) _newMsg.removeSource(newmessage)
        }
    }

    fun refreshMessages(chatID: String) {
        val source = useCase.execute(chatID)
        _messages.addSource(source){
            _messages.value = it
            if(!it.loading) _messages.removeSource(source)
        }
    }

}