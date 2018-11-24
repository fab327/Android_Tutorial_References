package com.justfabcodes.captcha

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.github.kittinunf.fuel.httpGet
import com.google.android.gms.safetynet.SafetyNet
import com.google.android.gms.safetynet.SafetyNetClient
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Original tutorial -> https://code.tutsplus.com/tutorials/how-to-add-captchas-to-android-apps--cms-29169
 * Android documentation -> https://developer.android.com/training/safetynet/recaptcha
 */
class MainActivity : AppCompatActivity() {

    val myClient: SafetyNetClient by lazy { SafetyNet.getClient(this) }
    val serverURL: String = "http://10.0.2.2:8000/validate"  //10.0.2.2 -> localhost to PC when accessing from emulator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindUiElements()
    }

    private fun bindUiElements() {
        humanButton.setOnClickListener {
            myClient.verifyWithRecaptcha(getString(R.string.my_site_key))
                .addOnSuccessListener { successEvent ->
                    val token: String = successEvent.tokenResult
                    serverURL.httpGet(listOf("user_token" to token))
                        .responseString { request, response, result ->
                            result.fold({ data ->
                                if (data.contains("PASS")) {
                                    Toast.makeText(this, "You seem to be a human", Toast.LENGTH_LONG).show()
                                } else {
                                    Toast.makeText(this, "You seem to be a bot", Toast.LENGTH_LONG).show()
                                }
                            }, {
                                Toast.makeText(this, "Error connecting to the server: ${it.exception.localizedMessage}", Toast.LENGTH_LONG).show()
                            })
                        }
                }
                .addOnFailureListener {
                    Toast.makeText(this, "There was an error: ${it.localizedMessage}", Toast.LENGTH_LONG).show()
                }
        }
    }
}
