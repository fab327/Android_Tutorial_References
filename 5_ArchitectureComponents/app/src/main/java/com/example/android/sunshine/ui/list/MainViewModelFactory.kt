package com.example.android.sunshine.ui.list

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.android.sunshine.data.SunshineRepository
import java.util.*

data class MainViewModelFactory(val repository: SunshineRepository) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainActivityViewModel(repository) as T
    }
}