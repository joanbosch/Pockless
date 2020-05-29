package com.pes.pockles.view.ui.achievements

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.pes.pockles.data.Resource
import com.pes.pockles.data.loading
import com.pes.pockles.data.repository.AchievementsRepository
import com.pes.pockles.model.Achievement
import javax.inject.Inject

class AchievementsViewModel @Inject constructor(
        private var repository: AchievementsRepository
    ) : ViewModel() {
        val notifications: LiveData<Resource<List<Achievement>>>
            get() = _notifications

        private val _notifications = MediatorLiveData<Resource<List<Achievement>>>()

        fun refreshNotifications() {
                val data = repository.getNotifications()
                _notifications.addSource(data) {
                        _notifications.value = it
                        if (!it.loading) _notifications.removeSource(data)
                   }
            }
    }