package com.raywenderlich.markme.feature.view.adapter

interface RwAdapter<T> {
    fun getData() : List<T>?
    fun updateData(data: List<T>)
}