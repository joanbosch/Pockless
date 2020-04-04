package com.pes.pockles.data.repository

import androidx.lifecycle.LiveData
import com.pes.pockles.data.api.ApiService
import com.pes.pockles.data.database.AppDatabase
import com.pes.pockles.model.User
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.concurrent.Executors
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    var database: AppDatabase,
    var apiService: ApiService
) :
    BaseRepository(apiService) {

    init {
        Timber.d("Database object in UserRepositoryImpl is $database")
    }

    fun getUser(): LiveData<User> {
        return database.userDao().getUser()
    }

    fun reloadUser() {

        val executor = Executors.newSingleThreadExecutor()
        disposable.add(apiService.getUser()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                { executor.execute { database.userDao().insert(it) } },
                { Timber.e(it) }
            ))
    }
}