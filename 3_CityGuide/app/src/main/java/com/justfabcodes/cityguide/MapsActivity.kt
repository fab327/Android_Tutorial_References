package com.justfabcodes.cityguide

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_maps.*


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private val LOCATION_REQUEST = 1;

    private lateinit var mapFragment: SupportMapFragment
    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        mapFragment = map as SupportMapFragment

        checkPermissionAndStart()
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        //Add a marker in Sydney and move the camera
        val sydneyLatLng = LatLng(-34.0, 151.0)
        val sydneyMarker = MarkerOptions().position(sydneyLatLng).title("Marker in Sydney")
        mMap.addMarker(sydneyMarker)
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydneyLatLng))
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            LOCATION_REQUEST -> {
                if (grantResults.isNotEmpty() &&
                        permissions[0] == ACCESS_FINE_LOCATION &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    instantiateMap()
                } else {
                    mapFragment.view?.let {
                        Snackbar.make(it, "Permission hasn't been granted", Snackbar.LENGTH_SHORT).show()
                    }
                }
            }

            else -> {
                // No other permission request for now
            }
        }
    }

    private fun instantiateMap() {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment.getMapAsync(this)
        mapFragment.view
    }

    private fun checkPermissionAndStart() {
        if (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //If permission has been denied once before
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, ACCESS_FINE_LOCATION)) {
                AlertDialog.Builder(this)
                        .setTitle("Location Request")
                        .setMessage("We need access to your location")
                        .setPositiveButton("Ok", { dialog, which ->
                            ActivityCompat.requestPermissions(this, arrayOf(ACCESS_FINE_LOCATION), LOCATION_REQUEST)
                        })
                        .setNegativeButton("No thanks", null)
                        .create()
                        .show()
            } else {
                ActivityCompat.requestPermissions(this, arrayOf(ACCESS_FINE_LOCATION), LOCATION_REQUEST)
            }
        } else {
            instantiateMap()
        }
    }

}
