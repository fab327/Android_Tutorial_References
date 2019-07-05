package com.raywenderlich.markme.di

import android.arch.persistence.room.Room
import android.content.Context
import android.content.SharedPreferences
import com.raywenderlich.markme.feature.FeatureContract
import com.raywenderlich.markme.feature.presenter.FeaturePresenter
import com.raywenderlich.markme.main.MainContract
import com.raywenderlich.markme.main.presenter.MainPresenter
import com.raywenderlich.markme.model.Student
import com.raywenderlich.markme.model.database.AppDatabase
import com.raywenderlich.markme.repository.AppRepository
import com.raywenderlich.markme.splash.SplashContract
import com.raywenderlich.markme.splash.presenter.SplashPresenter
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module

/**
 * override = true -> Content will override any other definition
 * factory -> new instance every time
 * single -> singleton
 */
val applicationModule = module(override = true) {
    factory<SplashContract.Presenter> { (view: SplashContract.View) -> SplashPresenter(view) }
    factory<MainContract.Presenter> { (view: MainContract.View) -> MainPresenter(view) }
    factory<FeatureContract.Presenter<Student>> { (view: FeatureContract.View<Student>) -> FeaturePresenter(view) }
    single<FeatureContract.Model<Student>> { AppRepository }
    single<SharedPreferences> { androidContext().getSharedPreferences("SharedPreferences", Context.MODE_PRIVATE) }
    single { Room.databaseBuilder(androidContext(), AppDatabase::class.java, "app-database").build() }
}