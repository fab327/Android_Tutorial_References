package com.justfabcodes.background.workers

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.justfabcodes.background.KEY_IMAGE_URI
import java.text.SimpleDateFormat
import java.util.*

/**
 * @since 11/8/18.
 */
class SaveImageToFileWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

    private val TAG by lazy { SaveImageToFileWorker::class.java.simpleName }
    private val title = "Blurred Image"
    private val dateFormatter = SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss Z", Locale.getDefault())

    override fun doWork(): Result {
        makeStatusNotification("Saving image", applicationContext)
        sleep()

        val resolver = applicationContext.contentResolver
        return try {
            val resourceUri = inputData.getString(KEY_IMAGE_URI)
            val bitmap = BitmapFactory.decodeStream(resolver.openInputStream(Uri.parse(resourceUri)))
            val imageUrl = MediaStore.Images.Media.insertImage(resolver, bitmap, title, dateFormatter.format(Date()))
            if (!imageUrl.isNullOrEmpty()) {
                outputData = Data.Builder().putString(KEY_IMAGE_URI, imageUrl).build()
                Result.SUCCESS
            } else {
                Log.e(TAG, "Writing to MediaStore failed")
                Result.FAILURE
            }
        } catch (e: Exception) {
            Log.e(TAG, "Unable to save image to Gallery", e)
            Result.FAILURE
        }
    }

}