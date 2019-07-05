package com.raywenderlich.markme.feature.presenter

import com.raywenderlich.markme.feature.FeatureContract
import com.raywenderlich.markme.model.Student
import com.raywenderlich.markme.utils.ClassSection
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

/**
 * Koin cannot inject to non activity objects so the class has to implement the koinComponent interface
 */
class FeaturePresenter(private var view: FeatureContract.View<Student>?)
    : FeatureContract.Presenter<Student>, KoinComponent {

    private val repository: FeatureContract.Model<Student> by inject()

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
        when (featureType) {
            ClassSection.ATTENDANCE -> repository.fetchFromPrefs(data)
            ClassSection.GRADING -> repository.fetchFromDb(data, { view?.onPersistedDataLoaded(it) })
        }
    }

    override fun onViewDestroyed() {
        view = null
    }
}
