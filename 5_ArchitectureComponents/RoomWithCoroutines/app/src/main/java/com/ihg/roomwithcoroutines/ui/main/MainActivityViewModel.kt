package com.ihg.roomwithcoroutines.ui.main

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.ihg.roomwithcoroutines.data.WordRepository
import com.ihg.roomwithcoroutines.data.database.AppDatabase
import com.ihg.roomwithcoroutines.data.model.Word
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)
    private val scope2 = CoroutineScope(Job() + Dispatchers.Main)

    private val repository: WordRepository
    val allWords: LiveData<List<Word>>

    init {
        val wordsDao = AppDatabase.getDatabase(application, scope).wordDao()
        repository = WordRepository(wordsDao)
        allWords = repository.allWords
    }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }

    fun insert(word: Word) {
        scope.launch(Dispatchers.IO) {
            repository.insert(word)
        }
    }

}