package com.ihg.roomwithcoroutines.data

import android.arch.lifecycle.LiveData
import android.support.annotation.WorkerThread
import com.ihg.roomwithcoroutines.data.database.WordDao
import com.ihg.roomwithcoroutines.data.model.Word

class WordRepository(private val wordDao: WordDao) {

    val allWords: LiveData<List<Word>> = wordDao.getAllWords()

    @WorkerThread
    suspend fun insert(word: Word) {
        wordDao.insert(word)
    }

}