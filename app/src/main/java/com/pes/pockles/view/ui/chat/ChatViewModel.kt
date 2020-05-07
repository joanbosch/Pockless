package com.pes.pockles.view.ui.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.pes.pockles.data.Resource
import com.pes.pockles.data.loading
import com.pes.pockles.domain.usecases.AllChatsUseCase
import com.pes.pockles.domain.usecases.ChatMessagesUseCase
import com.pes.pockles.model.Chat
import com.pes.pockles.model.Message
import javax.inject.Inject

class ChatViewModel @Inject constructor(
    var useCase: ChatMessagesUseCase
) : ViewModel() {

    val messages: LiveData<Resource<List<Message>>>
        get() = _messages

    private val _messages = MediatorLiveData<Resource<List<Message>>>()

    init {
        val source = useCase.execute("-M6ef3da7wOmlcKJXprt")
        _messages.addSource(source){
            _messages.value = it
            if(!it.loading) _messages.removeSource(source)
        }
    }

}