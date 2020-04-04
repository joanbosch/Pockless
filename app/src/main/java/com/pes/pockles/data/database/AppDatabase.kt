package com.pes.pockles.data.database

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.pes.pockles.data.database.dao.UserDao
import com.pes.pockles.model.User

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        var INSTANCE: AppDatabase? = null

        fun initDatabase(application: Application) {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        application,
                        AppDatabase::class.java,
                        "pocklesDatabase"
                    ).build()
                }
            }
        }

        fun getAppDatabase(): AppDatabase? {
            if (INSTANCE == null) {
                throw RuntimeException("Database must be initialized on an Application context")
            }
            return INSTANCE
        }
    }
}