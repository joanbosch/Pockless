package com.pes.pockles.view.ui.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.pes.pockles.data.Resource
import com.pes.pockles.data.loading
import com.pes.pockles.data.repository.UserRepository
import com.pes.pockles.domain.usecases.AllChatsUseCase
import com.pes.pockles.model.Chat
import com.pes.pockles.model.User
import javax.inject.Inject

class AllChatsViewModel @Inject constructor(
    var useCase: AllChatsUseCase,
    var userRespository: UserRepository
) : ViewModel() {

    val chats: LiveData<Resource<List<Chat>>>
        get() = _chats

    private val _chats = MediatorLiveData<Resource<List<Chat>>>()
    /*
    val user: LiveData<Resource<User>>
        get() = _user
    private val _user = MediatorLiveData<Resource<User>>()
    */
    init {
        val source = useCase.execute()
        _chats.addSource(source){
            _chats.value = it
            if(!it.loading) _chats.removeSource(source)
        }
    }





}
