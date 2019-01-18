package com.justfabcodes.walkmyandroid

import android.Manifest
import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.google.android.gms.location.*
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.io.IOException
import java.util.*

/**
 * https://codelabs.developers.google.com/codelabs/advanced-android-training-device-location/index.html?index=..%2F..advanced-android-training#0
 */
class MainActivity : AppCompatActivity() {

    companion object {
        val REQUEST_LOCATION_PERMISSION = 1
    }

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var lastLocation: Location
    private lateinit var geocoder: Geocoder
    private lateinit var locationCallback: LocationCallback

    private lateinit var rotateAnim: AnimatorSet
    private var trackingLocation = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        geocoder = Geocoder(this, Locale.getDefault())
        rotateAnim = AnimatorInflater.loadAnimator(this, R.animator.rotate) as AnimatorSet
        rotateAnim.setTarget(imageview_android)

        //Best way to test is by sending values to the emulator
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult?) {
                if (trackingLocation) {
                    textview_location.setText(getString(R.string.location_text, p0?.lastLocation?.latitude, p0?.lastLocation?.longitude, p0?.lastLocation?.time))
                }
            }
        }

        button_location.setOnClickListener {
            getLocation()
        }
        button_continuous_location.setOnClickListener {
            if (!trackingLocation) {
                startTrackingLocation()
            } else {
                stopTrackingLocation()
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            REQUEST_LOCATION_PERMISSION -> {
                // If the permission is granted, get the location; otherwise, show a Toast
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLocation()
                } else {
                    Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_LOCATION_PERMISSION)
        } else {
            trackLocationFuse()
            Log.d(MainActivity::class.java.simpleName, "getLocation: permissions granted")
        }
    }

    /*
     * Method to only get the last known location
     */
    @SuppressLint("MissingPermission")
    private fun trackLocationFuse() {
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            location?.let {
                lastLocation = location

                //Let's first display the coordinates
                textview_location.setText(getString(R.string.location_text, lastLocation.latitude, lastLocation.longitude, lastLocation.time))

                //Let's then reverse geocode
                doAsync {
                    var addresses = emptyList<Address>()
                    var resultMessage = ""

                    try {
                        addresses = geocoder.getFromLocation(lastLocation.latitude, lastLocation.longitude, 1)

                        addresses?.let { addresses ->
                            if (addresses.isEmpty()) {
                                resultMessage = "Address not found"
                            } else {
                                var address: Address = addresses.get(0)
                                var addressParts = arrayListOf<String>()

                                for (i in 0..address.maxAddressLineIndex) {
                                    addressParts.add(address.getAddressLine(i))
                                }

                                resultMessage = TextUtils.join(",", addressParts)

                                Thread.sleep(3000) //Display after 3 seconds
                                uiThread {
                                    textview_location.setText(getString(R.string.address_text, resultMessage, System.currentTimeMillis()))
                                }
                            }
                        }
                    } catch (ioException: IOException) {
                        resultMessage = "Service not available"
                        Log.e(MainActivity::class.java.simpleName, resultMessage, ioException)
                    } catch (illegalArgumentException: IllegalArgumentException) {
                        Log.e(MainActivity::class.java.simpleName, resultMessage, illegalArgumentException)
                    }
                }

            } ?: run {
                textview_location.setText("No location")
            }
        }

    }

    @SuppressLint("MissingPermission")
    private fun startTrackingLocation() {
        trackingLocation = true

        fusedLocationClient.requestLocationUpdates(getLocationRequest(), locationCallback, null)
        textview_location.setText(getString(R.string.address_text, "Loading", System.currentTimeMillis()))
        button_continuous_location.setText("Stop tracking location")

        rotateAnim.start()
    }

    private fun stopTrackingLocation() {
        trackingLocation = false
        fusedLocationClient.removeLocationUpdates(locationCallback)
        button_continuous_location.setText("Start location tracking")
        textview_location.setText(R.string.textview_hint)
        rotateAnim.end()
    }

    private fun getLocationRequest() : LocationRequest {
        return LocationRequest().apply {
            interval = 1000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }

}
