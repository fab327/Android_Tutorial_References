package com.justfabcodes.background.workers

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.justfabcodes.background.OUTPUT_PATH
import java.io.File

/**
 * @since 11/8/18.
 */
class CleanupWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

    private val TAG by lazy { CleanupWorker::class.java.simpleName }

    override fun doWork(): Result {
        makeStatusNotification("Cleaning up old temporary files", applicationContext)
        sleep()

        return try {
            val outputDirectory = File(applicationContext.filesDir, OUTPUT_PATH)
            if (outputDirectory.exists()) {
                val entries = outputDirectory.listFiles()
                entries?.forEach {
                    val name = it.name
                    if (name.isNotEmpty() && name.endsWith(".png")) {
                        val deleted = it.delete()
                        Log.i(TAG, String.format("Deleted %s - %s", name, deleted))
                    }
                }
            }

            Result.SUCCESS
        } catch (e: Exception) {
            Log.e(TAG, "Error cleaning up", e)
            Result.FAILURE
        }
    }

}