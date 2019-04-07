package dev.bibuti.greenlight.database

import android.app.Application

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dev.bibuti.greenlight.database.daos.UserDao
import dev.bibuti.greenlight.models.Users

@Database(entities = [Users::class], version = 1)
abstract class GreenLightDB : RoomDatabase() {
    companion object {

        @Volatile
        private var databaseInstance: GreenLightDB? = null

        internal fun getInstance(application: Application): GreenLightDB? {
            if (databaseInstance == null) {
                synchronized(GreenLightDB::class.java) {
                    if (databaseInstance == null) {
                        databaseInstance = Room.databaseBuilder(application.applicationContext, GreenLightDB::class.java, "greenlight")
                                .fallbackToDestructiveMigration()
                                //.allowMainThreadQueries()//this is only for development
                                .build()
                    }
                }
            }
            return databaseInstance
        }
    }

    abstract fun userDao(): UserDao

}
