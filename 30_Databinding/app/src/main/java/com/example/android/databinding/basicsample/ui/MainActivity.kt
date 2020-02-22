/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.databinding.basicsample.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.android.databinding.basicsample.R
import com.example.android.databinding.basicsample.databinding.PlainActivityBinding

/**
 * Plain old activity with lots of problems to fix.
 */
class MainActivity : AppCompatActivity() {

    // Obtain ViewModel from ViewModelProviders
    private val viewModel by lazy { ViewModelProviders.of(this).get(SimpleViewModel::class.java) }
    private lateinit var binding: PlainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.plain_activity)

        // TODO: Explicitly setting initial values is a bad pattern. We'll fix that.
        initView()
    }

    /**
     * This method is triggered by the `android:onclick` attribute in the layout. It puts business
     * logic in the activity, which is not ideal. We should do something about that.
     */
    fun onLike(view: View) {
        viewModel.onLike()
    }

    /**
     * So much findViewById! We'll fix that with Data Binding.
     */
    private fun initView() {
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel
    }

}
