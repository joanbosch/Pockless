package com.pes.pockles.data.repository.implementation

import androidx.lifecycle.LiveData
import com.bumptech.glide.load.engine.Resource
import com.pes.pockles.data.database.AppDatabase
import com.pes.pockles.data.repository.BaseRepository
import com.pes.pockles.domain.repositories.UserRepository
import com.pes.pockles.model.User
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.concurrent.Executors

class UserRepositoryImpl
    (private val database: AppDatabase = AppDatabase.getAppDatabase()!!) :
    UserRepository, BaseRepository() {

    override fun getUser(): LiveData<User> {
        return database.userDao().getUser()
    }

    override fun uploadUser(user: User): LiveData<Resource<Boolean>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun reloadUser() {

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