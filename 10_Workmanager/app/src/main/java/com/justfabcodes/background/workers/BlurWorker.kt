package com.justfabcodes.background.workers

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.text.TextUtils
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.justfabcodes.background.KEY_IMAGE_URI

/**
 * @since 11/7/18.
 */
class BlurWorker(context: Context, params: WorkerParameters): Worker(context, params) {

    private val TAG by lazy { BlurWorker::class.java.simpleName }

    override fun doWork(): Result {

        makeStatusNotification("Blurring image", applicationContext)

        val resourceUri: String? = inputData.getString(KEY_IMAGE_URI)

        try {
            if (TextUtils.isEmpty(resourceUri)) {
                Log.e(TAG, "Invalid input uri")
                throw IllegalArgumentException("Invalid input uri")
            }
            val resolver = applicationContext.contentResolver

//            val picture = BitmapFactory.decodeResource(applicationContext.resources, R.drawable.test)
            val picture = BitmapFactory.decodeStream(resolver.openInputStream(Uri.parse(resourceUri)))
            val blurredPicture = blurBitmap(picture, applicationContext)
            val blurredUri = writeBitmapToFile(applicationContext, blurredPicture)

            outputData = Data.Builder().putString(KEY_IMAGE_URI, blurredUri.toString()).build()

            return Result.SUCCESS
        } catch (t: Throwable) {
            Log.e(TAG, "Error applying blur", t);
            return Result.FAILURE
        }

    }

}