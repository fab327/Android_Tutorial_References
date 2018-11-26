
package com.justfabcodes.cheesefinder

import android.text.Editable
import android.text.TextWatcher
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_cheeses.*
import java.util.concurrent.TimeUnit

/**
 * Original tutorial -> https://www.raywenderlich.com/384-reactive-programming-with-rxandroid-in-kotlin-an-introduction
 */
class CheeseActivity : BaseSearchActivity() {

    private lateinit var disposable: Disposable //ends a subscription and especially forces emitter.setCancellable to fire
    private lateinit var subscriptions: CompositeDisposable

    override fun onStart() {
        super.onStart()

        val buttonClickStream = createButtonClickObservable()
        val textChangeStream = createTextChangeObservable()
        val searchTextObservable = Observable.merge(buttonClickStream, textChangeStream)

//        subscriptions.add()
        disposable = searchTextObservable
                .observeOn(AndroidSchedulers.mainThread())      // specify which thread to start with
                .doOnNext { showProgress() }                    //
                .observeOn(Schedulers.io())                     // specify which thread the next operator should be called on
                .map { cheeseSearchEngine.search(it) }          // operator
                .observeOn(AndroidSchedulers.mainThread())      // switch back to the main thread
                .subscribe ({                                    // update the UI
                    hideProgress()
                    showResult(it)
                }, { /* onError */ })
    }

    override fun onStop() {
        super.onStop()

        if (!disposable.isDisposed) {
            disposable.dispose()
        }
//        subscriptions.clear()
    }

    private fun createButtonClickObservable(): Observable<String> {
        return Observable.create { emitter ->
            searchButton.setOnClickListener { emitter.onNext(queryEditText.text.toString()) }
            emitter.setCancellable { searchButton.setOnClickListener(null) }
        }
    }

    private fun createTextChangeObservable(): Observable<String> {

        val textChangeObservable = Observable.create<String> { emitter ->
            val textWatcher = object : TextWatcher {

                override fun afterTextChanged(s: Editable?) = Unit

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

                override fun onTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    s?.toString()?.let { emitter.onNext(it) }
                }
            }

            queryEditText.addTextChangedListener(textWatcher)

            emitter.setCancellable {
                queryEditText.removeTextChangedListener(textWatcher)
            }
        }

        return textChangeObservable
                .filter { it.length >= 2 }  // Only act on elements with length of 2 or more
                .debounce(1000, TimeUnit.MILLISECONDS)  // Give it 1 second interval between changes
    }

}