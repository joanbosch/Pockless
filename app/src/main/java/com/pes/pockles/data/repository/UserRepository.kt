package com.pes.pockles.data.repository

import androidx.lifecycle.LiveData
import com.pes.pockles.data.Resource
import com.pes.pockles.data.api.ApiService
import com.pes.pockles.data.database.AppDatabase
import com.pes.pockles.model.CreateUser
import com.pes.pockles.model.User
import com.pes.pockles.util.AppExecutors
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private var database: AppDatabase,
    private var apiService: ApiService,
    private var executors: AppExecutors
) : BaseRepository(apiService) {

    fun getUser(): LiveData<User> {
        return database.userDao().getUser()
    }

    fun reloadUser() {
        disposable.add(apiService.getUser()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                ::saveUser
            ) { Timber.e(it) })
    }

    fun userExists(uid: String): LiveData<Resource<Boolean>> {
        return callApi(Function { apiService -> apiService.userExists(uid) })
    }

    fun createUser(createUser: CreateUser): LiveData<Resource<User>> {
        return callApi(Function { apiService -> apiService.createUser(createUser) })
    }

    fun saveUser(user: User) {
        executors.diskIO().execute { database.userDao().insert(user) }
    }

}