package com.justfabcodes.biometrics

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.security.crypto.EncryptedSharedPreferences
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var encryptedSharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupListeners()
    }

    override fun onStart() {
        super.onStart()

        if (BiometricManager.from(this).canAuthenticate() != BiometricManager.BIOMETRIC_SUCCESS) {
            Toast.makeText(this, "It doesn't seem like this device is setup with Biometric", Toast.LENGTH_LONG).show()
        }
    }

    override fun onStop() {
        super.onStop()

        if (::sharedPreferences.isInitialized) sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
        if (::encryptedSharedPreferences.isInitialized) sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        encryptionDisplayTextView.text = sharedPreferences?.getString(key, "")
    }

    private fun setupListeners() {
        biometricButton.setOnClickListener {
            getPrompt().authenticate(getPromptInfo())
        }

        encryptionSaveButton.setOnClickListener {
            saveData(
                encryptionTextInputLayout.editText?.text.toString(),
                encryptionCheckbox.isChecked
            )
        }
    }


    //////////////////////////////////////// Biometrics ///////////////////////////////////////////
    private fun getPrompt(): BiometricPrompt {
        val executor = Executors.newSingleThreadExecutor()
        return BiometricPrompt(this, executor, object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                if (errorCode == BiometricPrompt.ERROR_NEGATIVE_BUTTON) {
                    showToast("Negative button selected")
                } else {
                    showToast(errString.toString())     // Hardware not available, no finger enrolled or other errors
                }
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)

                showToast(result.toString())
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()

                showToast("Authentication failed")  // Unauthenticated finger or something
            }
        })
    }

    private fun getPromptInfo(): BiometricPrompt.PromptInfo {
        return BiometricPrompt.PromptInfo.Builder()
            .setTitle("Title")
            .setSubtitle("Subtitle")
            .setDescription("Description")
            .setNegativeButtonText("Negative Button")
            .build()
    }

    private fun showToast(msg: String) {
        runOnUiThread {
            Toast.makeText(this@MainActivity, msg, Toast.LENGTH_LONG)
                .show()
        }
    }

    /////////////////////////////////////////// Encryption /////////////////////////////////////////
    private fun saveData(input: String, encrypted: Boolean) {
        getPreferences(encrypted)
            .edit()
            .putString("KEY", input)
            .apply()
    }


    private fun getPreferences(encrypted: Boolean): SharedPreferences {
        sharedPreferences = getSharedPreferences("FileName", Context.MODE_PRIVATE).also {
            it.registerOnSharedPreferenceChangeListener(this)
        }

        //The content difference will be visible through the DeviceFileExplorer
        encryptedSharedPreferences = EncryptedSharedPreferences.create(
            "EncryptedFileName",
            "masterKeyAlias",
            this,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        ).also {
            it.registerOnSharedPreferenceChangeListener(this)
        }

        return if (encrypted) encryptedSharedPreferences else sharedPreferences
    }

}
