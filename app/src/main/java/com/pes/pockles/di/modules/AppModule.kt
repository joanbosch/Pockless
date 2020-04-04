package com.pes.pockles.di.modules

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.pes.pockles.PocklesApplication
import com.pes.pockles.data.api.ApiService
import com.pes.pockles.data.api.createApi
import com.pes.pockles.data.database.AppDatabase
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
    fun provideApi(): ApiService {
        return createApi(ApiService::class.java)
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