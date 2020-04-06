package com.pes.pockles.di.modules

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.pes.pockles.PocklesApplication
import com.pes.pockles.data.TokenManager
import com.pes.pockles.data.api.ApiManager
import com.pes.pockles.data.api.ApiService
import com.pes.pockles.data.database.AppDatabase
import com.pes.pockles.data.storage.StorageManager
import com.pes.pockles.di.util.ViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideAppDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(app, AppDatabase::class.java, "pocklesDatabase")
            .allowMainThreadQueries()
            .build()
    }

    @Singleton
    @Provides
    fun provideTokenManager(): TokenManager {
        return TokenManager()
    }

    @Singleton
    @Provides
    fun provideStorageManager(): StorageManager {
        return StorageManager()
    }

    @Singleton
    @Provides
    fun provideApi(tokenManager: TokenManager): ApiService {
        return ApiManager(tokenManager).createApi(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun providesApplication(app: Application): PocklesApplication {
        return app as PocklesApplication
    }

    @Singleton
    @Provides
    fun providesContext(app: Application): Context {
        return app
    }
}