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

package com.justfabcodes.background

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.work.*
import com.justfabcodes.background.workers.BlurWorker
import com.justfabcodes.background.workers.CleanupWorker
import com.justfabcodes.background.workers.SaveImageToFileWorker


class BlurViewModel : ViewModel() {

    private val workManager: WorkManager = WorkManager.getInstance()

    internal var outputStatus: LiveData<List<WorkStatus>>
    internal var imageUri: Uri? = null
    internal var outputUri: Uri? = null

    init {
        //Allows for reporting of status change to the UI
        outputStatus = workManager.getStatusesByTagLiveData(TAG_OUTPUT)
    }

    /*
     * OneTimeWorkRequestBuilder<T>() <=> OneTimeWorkRequest.Builder(T)
     */
    internal fun applyBlur(blurLeveL: Int) {
        //Create charging constraint
        val constraints = Constraints.Builder()
            .setRequiresStorageNotLow(true)
            .build()

        //Add WorkRequest to cleanup temporary images
        var continuation = workManager.beginUniqueWork(
            IMAGE_MANIPULATION_WORK_NAME,
            ExistingWorkPolicy.REPLACE,
            OneTimeWorkRequest.from(CleanupWorker::class.java)
        )
//        PeriodicWorkRequestBuilder<CleanupWorker>(12, TimeUnit.HOURS) -> For periodic work


        //Simple version
//        val blurRequest = OneTimeWorkRequest.Builder(BlurWorker::class.java)
//            .setInputData(createInputeDataForUri())
//            .build()
//        continuation = continuation.then(blurRequest)

        // Add WorkRequests to blur the image the number of times requested
        for (i in 0 until blurLeveL) {
            val blurBuilder = OneTimeWorkRequestBuilder<BlurWorker>()

            // Input the Uri if this is the first blur operation
            // After the fist blur operation the input will be the output of the previous blur operations
            if (i == 0) {
                blurBuilder.setInputData(createInputeDataForUri())
            }

            continuation = continuation.then(blurBuilder.build())
        }


        val save = OneTimeWorkRequestBuilder<SaveImageToFileWorker>()
            .setConstraints(constraints)
            .addTag(TAG_OUTPUT)
            .build()
        continuation = continuation.then(save)

        continuation.enqueue()


        // More example on how we can leverage parallelism and chaining
//        WorkManager.getInstance()
//            // First, run all the A tasks (in parallel):
//            .beginWith(workA1, workA2, workA3)
//            // ...when all A tasks are finished, run the single B task:
//            .then(workB)
//            // ...then run the C tasks (in any order):
//            .then(workC1, workC2)
//            .enqueue()

//        val chain1 = WorkManager.getInstance()
//            .beginWith(workA)
//            .then(workB)
//        val chain2 = WorkManager.getInstance()
//            .beginWith(workC)
//            .then(workD)
//        val chain3 = WorkContinuation
//            .combine(chain1, chain2)
//            .then(workE)
//        chain3.enqueue()
    }

    internal fun cancelWork() {
        workManager.cancelUniqueWork(IMAGE_MANIPULATION_WORK_NAME)
//        workManager.cancelAllWorkByTag(TAG_OUTPUT) -> Using tag can group requests by group and cancel a category of requests at once
    }

    /**
     * Setters
     */
    internal fun setImageUri(uri: String?) {
        imageUri = uriOrNull(uri)
    }

    internal fun setOutputUri(outputImageUri: String?) {
        outputUri = uriOrNull(outputImageUri)
    }

    private fun uriOrNull(uriString: String?): Uri? {
        return if (!uriString.isNullOrEmpty()) {
            Uri.parse(uriString)
        } else {
            null
        }
    }

    /**
     *  Creates the input data bundle which includes the Uri to operate on
     *  @return Data which contains the Image Uri as a String
     */
    private fun createInputeDataForUri(): Data {
        val builder = Data.Builder()
        imageUri?.let {
            builder.putString(KEY_IMAGE_URI, imageUri?.toString())
        }
        return builder.build()
    }

}
