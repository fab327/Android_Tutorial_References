package com.raywenderlich.markme.feature.view.adapter

interface RwAdapter<T> {
    fun getData() : List<T>?
}