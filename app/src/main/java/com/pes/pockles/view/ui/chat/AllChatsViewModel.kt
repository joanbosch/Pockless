package com.pes.pockles.view.ui.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.pes.pockles.data.Resource
import com.pes.pockles.data.loading
import com.pes.pockles.domain.usecases.AllChatsUseCase
import com.pes.pockles.model.Chat
import javax.inject.Inject

class AllChatsViewModel @Inject constructor(
    private var useCase: AllChatsUseCase
) : ViewModel() {

    val chats: LiveData<Resource<List<Chat>>>
        get() = _chats

    private val _chats = MediatorLiveData<Resource<List<Chat>>>()

    // Executed when RecyclerView must be updated
    /*
    fun refreshInformation() {
        val source = useCase.execute()
        _pocksHistory.addSource(source) {
            _pocksHistory.value = it
            if (!it.loading) _pocksHistory.removeSource(source)
        }
    }
     */
}