package com.justfabcodes.android.sunshine.ui.list

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.justfabcodes.android.sunshine.data.SunshineRepository

data class MainViewModelFactory(val repository: SunshineRepository) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainActivityViewModel(repository) as T
    }
}