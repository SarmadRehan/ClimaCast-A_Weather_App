package com.sarmadrehan.climacast.views

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Toast
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
import com.sarmadrehan.climacast.databinding.ActivityWeatherBinding
import com.sarmadrehan.climacast.db.WeatherDatabase
import com.sarmadrehan.climacast.repository.WeatherRepository

import com.sarmadrehan.climacast.utils.AppConstants.PERMISSION_ID
import com.sarmadrehan.climacast.utils.AppConstants.QUERY
import com.sarmadrehan.climacast.utils.AppConstants.REQUEST_CHECK_SETTINGS
import com.sarmadrehan.climacast.utils.Resource
import com.sarmadrehan.climacast.viewmodels.WeatherViewModel
import java.io.IOException
import java.util.Locale
import kotlin.math.ceil

class WeatherActivity : AppCompatActivity() {

    private lateinit var viewModel: WeatherViewModel
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var binding: ActivityWeatherBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeatherBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.textViewWeatherDetails.setOnClickListener {
            startActivity(Intent(this, WeatherDetails::class.java))
        }

        binding.textViewLocationName.text = QUERY

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        getLocation()
    }

    private fun Context.isLocationEnabled(): Boolean {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    private fun AppCompatActivity.requestLocationPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            PERMISSION_ID
        )
    }

    private fun hasLocationPermissions(): Boolean {
        val coarseLocationPermission = ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        val fineLocationPermission = ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        return coarseLocationPermission && fineLocationPermission
    }

    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_ID) {
            handleLocationPermissionResult(grantResults)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CHECK_SETTINGS) {
            if (resultCode == Activity.RESULT_OK) {
                handleLocationSettingsSuccess()
            } else {
                Toast.makeText(this, "Location settings not enabled", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        if (hasLocationPermissions()) {
            if (isLocationEnabled()) {
                fusedLocationClient.lastLocation.addOnCompleteListener(this) { task ->
                    val location: Location? = task.result
                    if (location == null) {
//                        Log.d("Tag","If Location Null inside getLocation()")
                        enableLocation()
                    } else {
//                        Log.d("Tag","Getting Address inside getLocation()")
                        getAddressString(location.latitude, location.longitude)
                    }
                }
            } else {
                enableLocation()
            }
        } else {
            requestLocationPermissions()
        }
    }

    private fun enableLocation() {
        val locationRequest = createLocationRequest()

        val locationSettingsRequest = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)
            .setAlwaysShow(true)
            .build()

        val settingsClient = LocationServices.getSettingsClient(this)
        val task = settingsClient.checkLocationSettings(locationSettingsRequest)

        task.addOnSuccessListener {
            handleLocationSettingsSuccess()
        }

        task.addOnFailureListener { exception ->
            handleLocationSettingsFailure(exception)
        }
    }

    private fun createLocationRequest(): LocationRequest {
        return LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = 10000
            fastestInterval = (10000 / 2).toLong()
        }
    }

    private fun getAddressString(lat: Double, lon: Double) {
        val addresses: List<Address>?
        val geocoder: Geocoder = Geocoder(this, Locale.getDefault())
        try {
            addresses = geocoder.getFromLocation(lat, lon, 1)
            if (addresses != null) {
                if (addresses.isNotEmpty()) {
                    val address = addresses[0].getAddressLine(0)
                    val city = addresses[0].locality
                    val state = addresses[0].adminArea
                    val country = addresses[0].countryName
                    val postalCode = addresses[0].postalCode
                    val knownName = addresses[0].featureName // Only if available else return NULL

                    // Extract other location details if needed
//                    Toast.makeText(this, "City: $city", Toast.LENGTH_LONG).show()
                    var queryParameter = "$lat,$lon"
                    Log.d("TAG","Latitude: $lat")
                    Log.d("TAG","Longitude: $lon")
//                    Toast.makeText(this,"Latitude: $lat \nLongitude: $lon",Toast.LENGTH_LONG).show()
                    QUERY = queryParameter
                    val weatherRepository = WeatherRepository(WeatherDatabase(this))
                    val viewModelProviderFactory = CurrentWeatherViewModelProviderFactory(weatherRepository)
                    viewModel = ViewModelProvider(this, viewModelProviderFactory)
                        .get(WeatherViewModel::class.java)
                    viewModel.currentWeather.observe(this) { resource ->
                        when (resource) {
                            is Resource.Success -> {
                                val currentWeather = resource.data?.location
                                currentWeather?.let {
                                    binding.textViewLocationName.text =
                                        it.name
                                    binding.textViewWeatherTime.text =
                                        it.localtime
                                }
                            }
                            is Resource.Error -> {
                                // Handle error case
                            }

                            is Resource.Loading -> {
                                // Handle loading state
                            }
                        }
                    }


                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestLocationUpdates() {
        val locationRequest = createLocationRequest()

        val locationCallback: LocationCallback = object : LocationCallback() {
            @SuppressLint("SetTextI18n")
            override fun onLocationResult(locationResult: LocationResult) {
                val mLastLocation = locationResult.lastLocation!!
                getAddressString(mLastLocation.latitude, mLastLocation.longitude)
                Log.d("TAG1","AfterCallBack");
            }
        }

        val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        if (hasLocationPermissions()) {
            fusedLocationProviderClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
        }
    }

    private fun handleLocationPermissionResult(grantResults: IntArray) {
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getLocation()
        }
    }

    private fun handleLocationSettingsSuccess() {
        requestLocationUpdates()
    }

    private fun handleLocationSettingsFailure(exception: Exception) {
        if (exception is ResolvableApiException) {
            try {
                exception.startResolutionForResult(this, REQUEST_CHECK_SETTINGS)
            } catch (ex: IntentSender.SendIntentException) {
                ex.printStackTrace()
            }
        } else {
            Toast.makeText(this, "Failed to enable location", Toast.LENGTH_SHORT).show()
        }
    }
}