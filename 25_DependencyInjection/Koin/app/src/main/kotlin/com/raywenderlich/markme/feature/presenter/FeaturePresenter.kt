package com.raywenderlich.markme.feature.presenter

import com.raywenderlich.markme.feature.FeatureContract
import com.raywenderlich.markme.model.Student
import com.raywenderlich.markme.repository.AppRepository
import com.raywenderlich.markme.utils.ClassSection

class FeaturePresenter(private var view: FeatureContract.View<Student>?) : FeatureContract.Presenter<Student> {

    private val repository: FeatureContract.Model<Student> by lazy { AppRepository }

    override fun onSave2PrefsClick(data: List<Student>?) {
        data?.let {
            repository.add2Prefs(data = data, callback = { msg ->
                view?.showToastMessage(msg)
            })
        }
    }

    override fun onSave2DbClick(data: List<Student>?) {
        data?.let {
            repository.add2Db(data = data, callback = { msg ->
                view?.showToastMessage(msg)
            })
        }
    }

    override fun loadPersistedData(data: List<Student>, featureType: ClassSection) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onViewDestroyed() {
        view = null
    }
}
