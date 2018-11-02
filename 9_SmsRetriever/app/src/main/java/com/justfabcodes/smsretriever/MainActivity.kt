package com.justfabcodes.smsretriever

import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.telephony.SmsManager
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

/**
 * The magic happens in smsManager.createAppSpecificSmsToken("PendingIntent")
 * on reception of the output, the Pending intent will be enacted, in this case open the confirmation activity
 *
 * Test by running the app in the emulator and sending the value generated via SMS
 */
class MainActivity : AppCompatActivity() {

    private val REQUEST_CODE = 1234

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val smsManager: SmsManager = SmsManager.getDefault()
        val smsToken = smsManager.createAppSpecificSmsToken(createSmsTokenPendingIntent())

        token_tv.setText(smsToken)
        Log.i("SMS_RETRIEVER", "Sms Token $smsToken")
    }

    private fun createSmsTokenPendingIntent() : PendingIntent {
        return PendingIntent.getActivity(this, REQUEST_CODE, Intent(this, SmsTokenResultActivity::class.java), 0)
    }

}