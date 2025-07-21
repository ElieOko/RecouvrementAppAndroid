package com.client.recouvrementapp.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [], version = 1, exportSchema = false)
abstract class RecouvrementRoomDatabase : RoomDatabase() {
    companion object{


        @Volatile
        private var INSTANCE: RecouvrementRoomDatabase? = null
        fun getDatabase(context: Context): RecouvrementRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RecouvrementRoomDatabase::class.java,
                    "recouvrement"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
    private class RecouvrementDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let {
                scope.launch {
                    // populateDatabase(database.userDao())
                }
            }
        }
    }
}