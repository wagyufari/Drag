package com.mayburger.drag.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mayburger.drag.data.dao.FlyerDao
import com.mayburger.drag.model.Flyer

@Database(
    entities = [Flyer::class],
    version = 1
)
abstract class PersistenceDatabase : RoomDatabase() {

    abstract fun flyerDao(): FlyerDao

    companion object {
        @Volatile
        private var instance: PersistenceDatabase? = null

        fun getDatabase(context: Context): PersistenceDatabase =
            instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }

        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(appContext, PersistenceDatabase::class.java, "goals")
                .fallbackToDestructiveMigration()
                .build()
    }
}