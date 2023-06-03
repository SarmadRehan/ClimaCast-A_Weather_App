//package com.sarmadrehan.climacast.views
//
//import android.annotation.SuppressLint
//import android.content.Context
//import android.content.Intent
//import android.content.IntentSender.SendIntentException
//import android.content.pm.PackageManager
//import android.location.Address
//import android.location.Geocoder
//import android.location.Location
//import android.location.LocationManager
//import android.os.Bundle
//import android.provider.Settings
//import android.widget.TextView
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.app.ActivityCompat
//import androidx.lifecycle.ViewModelProvider
//import com.google.android.gms.common.api.ResolvableApiException
//import com.google.android.gms.location.FusedLocationProviderClient
//import com.google.android.gms.location.LocationRequest
//import com.google.android.gms.location.LocationServices
//import com.google.android.gms.location.LocationSettingsRequest
//import com.google.android.gms.location.LocationSettingsResponse
//import com.google.android.gms.tasks.OnFailureListener
//import com.google.android.gms.tasks.OnSuccessListener
//import com.google.android.material.snackbar.Snackbar
//import com.sarmadrehan.climacast.R
//import com.sarmadrehan.climacast.db.WeatherDatabase
//import com.sarmadrehan.climacast.repository.CurrentWeatherRepository
//import com.sarmadrehan.climacast.viewmodels.CurrentWeatherViewModel
//import java.util.Locale
//
//class WeatherActivity : AppCompatActivity() {
//    private val PERMISSION_ID = 42
//    private val REQUEST_CHECK_SETTINGS = 0x1
//
//
//    lateinit var viewModel: CurrentWeatherViewModel
//    private lateinit var mFusedLocationClient: FusedLocationProviderClient
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_weather)
//
//        val weatherRepository = CurrentWeatherRepository(WeatherDatabase(this))
//        val viewModelProviderFactory = CurrentWeatherViewModelProviderFactory(weatherRepository)
//        viewModel = ViewModelProvider(
//            this,
//            viewModelProviderFactory
//        ).get(CurrentWeatherViewModel::class.java)
//        val weatherDetails = this.findViewById<TextView>(R.id.textView_weatherDetails);
//        weatherDetails.setOnClickListener {
//            startActivity(Intent(this, WeatherDetails::class.java))
//        }
//        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
//        getLocation()
//
//    }
//
//    private fun isLocationEnabled(): Boolean {
//        val locationManager: LocationManager =
//            getSystemService(Context.LOCATION_SERVICE) as LocationManager
//        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
//            LocationManager.NETWORK_PROVIDER
//        )
//    }
//
//    private fun requestPermissions() {
//        ActivityCompat.requestPermissions(
//            this,
//            arrayOf(
//                android.Manifest.permission.ACCESS_COARSE_LOCATION,
//                android.Manifest.permission.ACCESS_FINE_LOCATION
//            ),
//            PERMISSION_ID
//        )
//    }
//
//    private fun checkPermissions(): Boolean {
//        if (
//            ActivityCompat.checkSelfPermission(
//                this,
//                android.Manifest.permission.ACCESS_COARSE_LOCATION
//            ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
//                (
//                this,
//                android.Manifest.permission.ACCESS_FINE_LOCATION
//            ) == PackageManager.PERMISSION_GRANTED
//        ) {
//            return true
//        }
//        return false
//    }
//
//    @SuppressLint("MissingSuperCall")
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<String>,
//        grantResults: IntArray
//    ) {
//        if (requestCode == PERMISSION_ID) {
//            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
//                getLocation()
//            }
//        }
//    }
//
//    @SuppressLint("MissingPermission", "SetTextI18n")
//    private fun getLocation() {
//        if (checkPermissions()) {
//            if (isLocationEnabled()) {
//                mFusedLocationClient.lastLocation.addOnCompleteListener(this) { task ->
//                    val location: Location? = task.result
//                    if (location != null) {
//                        val geocoder = Geocoder(this, Locale.getDefault())
//                        val addresses: List<Address> = geocoder.getFromLocation(
//                            location.latitude,
//                            location.longitude,
//                            1
//                        ) as List<Address>
//                        if (addresses.isNotEmpty()) {
//                            val address: Address = addresses[0]
//                            val city = address.locality
//                            val state = address.adminArea
//                            val postalCode = address.postalCode
//                            val addressLine = address.getAddressLine(0)
//
//                            // Do something with the location details
//                            // For example, display them in a TextView
//                            Toast.makeText(this, "City: ${city}", Toast.LENGTH_LONG).show()
//                        }
//                    }
//                }
//            } else {
////                Toast.makeText(this, "Please turn on location", Toast.LENGTH_LONG).show()
////                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
////                startActivity(intent)
//                enableLocation()
//            }
//        } else {
//            requestPermissions()
//        }
//    }
//    private fun enableLocation() {
//
////        Intent intent = new Intent(getContext(), LocationService.class);
////        requireContext().startService(intent);
//        val locationRequest = LocationRequest.create()
//        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
//        locationRequest.interval = 10000
//        locationRequest.fastestInterval = (10000 / 2).toLong()
//        val locationSettingRequestBuilder = LocationSettingsRequest.Builder()
//        locationSettingRequestBuilder.addLocationRequest(locationRequest)
//        locationSettingRequestBuilder.setAlwaysShow(true)
//        val settingsClient = LocationServices.getSettingsClient(this)
//        val task = settingsClient.checkLocationSettings(locationSettingRequestBuilder.build())
//        //        locationCardView.setVisibility(View.GONE);
//        task.addOnSuccessListener(this,
//            OnSuccessListener<LocationSettingsResponse?> {
////                Snackbar.make(
////                    findViewById(R.layout.activity_weather),
////                    "Location enabled Successfully",
////                    Snackbar.LENGTH_LONG
////                ).show()
//                getLocation()
//            })
//        task.addOnFailureListener(this, OnFailureListener { e ->
//            if (e is ResolvableApiException) {
//                try {
//                    e.startResolutionForResult(
//                        this,
//                        REQUEST_CHECK_SETTINGS
//                    )
//                } catch (ex: SendIntentException) {
//                    ex.printStackTrace()
//                }
//            }
//        })
//    }
//}

package com.sarmadrehan.climacast.views

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsResponse
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.sarmadrehan.climacast.R
import com.sarmadrehan.climacast.db.WeatherDatabase
import com.sarmadrehan.climacast.repository.CurrentWeatherRepository
import com.sarmadrehan.climacast.viewmodels.CurrentWeatherViewModel
import java.io.IOException
import java.util.Locale

class WeatherActivity : AppCompatActivity() {
    private val PERMISSION_ID = 42
    private val REQUEST_CHECK_SETTINGS = 0x1

    private lateinit var viewModel: CurrentWeatherViewModel
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        val weatherRepository = CurrentWeatherRepository(WeatherDatabase(this))
        val viewModelProviderFactory = CurrentWeatherViewModelProviderFactory(weatherRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory)
            .get(CurrentWeatherViewModel::class.java)

        val weatherDetails = this.findViewById<TextView>(R.id.textView_weatherDetails);
        weatherDetails.setOnClickListener {
            startActivity(Intent(this, WeatherDetails::class.java))
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        getLocation()
    }

    private val mLocationCallback: LocationCallback = object : LocationCallback() {
        @SuppressLint("SetTextI18n")
        override fun onLocationResult(locationResult: LocationResult) {
            val mLastLocation = locationResult.lastLocation!!
            getAddressString(mLastLocation.latitude, mLastLocation.longitude)
            Log.d("TAG1","AfterCallBack");
        }
    }

    private fun Context.isLocationEnabled(): Boolean {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    private fun AppCompatActivity.requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ),
            PERMISSION_ID
        )
    }

    private fun AppCompatActivity.checkPermissions(): Boolean {
        return (ActivityCompat.checkSelfPermission(
            this,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED)
    }

    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_ID && grantResults.isNotEmpty() &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            getLocation()
        }
    }
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CHECK_SETTINGS) {
            if (resultCode == Activity.RESULT_OK) {
                // The user enabled the necessary location settings
                requestLocationUpdates()
            } else {
                // The user did not enable the location settings
                Toast.makeText(this, "Location settings not enabled", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                fusedLocationClient.lastLocation.addOnCompleteListener(this) { task ->
                    val location: Location? = task.result
                    if (location == null) {
                       Log.d("Tag","If Location Null inside getLocation()")
                        enableLocation()
                    } else {
                        Log.d("Tag","Getting Address inside getLocation()")
                        getAddressString(location.latitude, location.longitude)
                    }
                }
            } else {
                enableLocation()
            }
        } else {
            requestPermissions()
        }
    }

    private fun showDialog() {
        val dialog = Dialog(this)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.popup_enable_location)
        val dialogButtonNo = dialog.findViewById<TextView>(R.id.dialog_button_no)
        val dialogButtonYes = dialog.findViewById<TextView>(R.id.dialog_button_yes)

        dialogButtonNo.setOnClickListener {
            dialog.dismiss()
        }
        dialogButtonYes.setOnClickListener {
            Log.d("Tag","Inside ShowDialog())")
            enableLocation()
            dialog.dismiss()
        }
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }

    private fun enableLocation() {
        val locationRequest = LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = 10000
            fastestInterval = (10000 / 2).toLong()
        }

        val locationSettingsRequest = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)
            .setAlwaysShow(true)
            .build()

        val settingsClient = LocationServices.getSettingsClient(this)
        val task = settingsClient.checkLocationSettings(locationSettingsRequest)

        task.addOnSuccessListener(this) {
            // Location settings are satisfied, start requesting location updates
            requestLocationUpdates()
        }

        task.addOnFailureListener(this) { exception ->
            if (exception is ResolvableApiException) {
                try {
                    // Prompt the user to enable location settings
                    exception.startResolutionForResult(this, REQUEST_CHECK_SETTINGS)
                } catch (ex: IntentSender.SendIntentException) {
                    ex.printStackTrace()
                }
            } else {
                // Handle other types of exceptions
                Toast.makeText(this, "Failed to enable location", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun getAddressString(lat: Double, lon: Double) {
        val addresses: List<Address>?
        val geocoder: Geocoder = Geocoder(this, Locale.getDefault())
        try {
            addresses = geocoder.getFromLocation(lat, lon, 1)
            val address = addresses!![0].getAddressLine(0)
            val city = addresses[0].locality
            val state = addresses[0].adminArea
            val country = addresses[0].countryName
            val postalCode = addresses[0].postalCode
            val knownName = addresses[0].featureName // Only if available else return NULL

            if (addresses.isNotEmpty()) {
                // Extract other location details if needed
                Toast.makeText(this, "City: $city", Toast.LENGTH_LONG).show()
                Log.d("TAG","Country: $country")
            }
            Log.d("TAG","City: $city")
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
    private fun requestLocationUpdates() {
        val locationRequest = LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = 10000
            fastestInterval = (10000 / 2).toLong()
        }

        val locationCallback: LocationCallback = object : LocationCallback() {
            @SuppressLint("SetTextI18n")
            override fun onLocationResult(locationResult: LocationResult) {
                val mLastLocation = locationResult.lastLocation!!
                getAddressString(mLastLocation.latitude, mLastLocation.longitude)
                Log.d("TAG1","AfterCallBack");
            }
        }

        val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }
}
