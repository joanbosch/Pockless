package com.pes.pockles.data.repository

import androidx.lifecycle.LiveData
import com.pes.pockles.data.Resource
import com.pes.pockles.data.api.ApiService
import com.pes.pockles.model.Chat
import javax.inject.Inject
import javax.inject.Singleton
import io.reactivex.functions.Function

@Singleton
class ChatRepository @Inject constructor(
    private var apiService: ApiService
) : BaseRepository(apiService) {

    fun getAllChats(): LiveData<Resource<List<Chat>>> {
        return callApi(Function { apiService -> apiService.allChats() })
    }
}