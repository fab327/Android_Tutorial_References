package com.ihg.roomwithcoroutines.data.database

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.ihg.roomwithcoroutines.data.model.Word
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Database(
    entities = [Word::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun wordDao(): WordDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "AppDatabase")
                        .addCallback(AppDatabaseCallback(scope))
                        .build()
                }
            }
            return INSTANCE!!
        }
    }

    private class AppDatabaseCallback(val scope: CoroutineScope) : RoomDatabase.Callback() {
        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { appDatabase ->
                scope.launch(Dispatchers.IO) {
                    populateDatabase(appDatabase.wordDao())
                }
            }
        }

        fun populateDatabase(wordDao: WordDao) {
            wordDao.deleteAll()

            wordDao.insert(Word("Hello"))
            wordDao.insert(Word("World!"))
        }
    }
}