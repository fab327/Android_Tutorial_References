package com.raywenderlich.markme.repository

import com.raywenderlich.markme.feature.FeatureContract
import com.raywenderlich.markme.model.Student

object AppRepository : FeatureContract.Model<Student> {
    override fun add2Db(data: List<Student>, callback: (String) -> Unit) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun add2Prefs(data: List<Student>, callback: (String) -> Unit) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun fetchFromDb(data: List<Student>, callback: (List<Student>) -> Unit) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun fetchFromPrefs(data: List<Student>): List<Student> {
        return listOf()
    }
}