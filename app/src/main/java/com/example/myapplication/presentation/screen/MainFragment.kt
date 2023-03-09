package com.example.myapplication.presentation.screen

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.R
import com.example.myapplication.data.repository.WeatherRepository
import com.example.myapplication.databinding.FragmentMainBinding
import com.google.android.gms.location.*
import kotlinx.coroutines.launch

class MainFragment : Fragment(R.layout.fragment_main) {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private var name: String? = null
    private var temp: String? = null
    private var pressure: String? = null
    private var wind: String? = null
    private var humidity: String? = null
    private var iconID: String? = null

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager = context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMainBinding.bind(view)

        val repository = WeatherRepository()

        val locationPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
                if (granted){
                    //...
                }
            }

        with(binding){
            btnCity.setOnClickListener {
                val cityName = etCity.editText?.text.toString()
                if(cityName.isEmpty()){
                    Toast.makeText(requireContext(), "Введите город, пожалуйста", Toast.LENGTH_SHORT).show()
                } else {
                    lifecycleScope.launch {
                        //val cityName = etCity.editText?.text.toString()
                        progressBar.visibility = View.VISIBLE
                        runCatching {
                            repository.getWeatherInfoByCityName(city = cityName)
                        }.onSuccess {
                            progressBar.visibility = View.GONE
                            tvCity.text = cityName
                            tvTemp.text = getString(R.string.temp_string, it?.main?.temp.toString())

                            name = cityName
                            temp = it?.main?.temp.toString()
                            pressure = it?.main?.pressure.toString()
                            wind = it?.windInfo?.speed.toString()
                            humidity = it?.main?.humidity.toString()
                            iconID = it?.weatherList?.get(0)?.icon.toString()

                        }.onFailure {
                            progressBar.visibility = View.GONE
                            Toast.makeText(requireContext(), "Error: $it", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            }

            fun requestLocationAccess () {
                locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                locationPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
            }

            btnCoord.setOnClickListener {
                val permission1 = Manifest.permission.ACCESS_FINE_LOCATION
                val permission2 = Manifest.permission.ACCESS_COARSE_LOCATION
                val mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

                var latitude: Double?
                var longitude: Double?

                if (ContextCompat.checkSelfPermission(requireContext(), permission1) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(requireContext(), permission2) == PackageManager.PERMISSION_GRANTED
                    ) {
                    Log.i("PermissionGranted", "PermissionGranted")
                    if (isLocationEnabled()) {
                        mFusedLocationClient.lastLocation.addOnCompleteListener(requireActivity()) { task ->
                            val location: Location? = task.result
                            if (location == null) {
                                //requestNewLocationData() не знаю как это сделать
                            } else {
                                lifecycleScope.launch {
                                    latitude = location.latitude
                                    longitude = location.longitude
                                    progressBar.visibility = View.VISIBLE
                                    runCatching {
                                        repository.getWeatherInfoByCoord(latitude = latitude!!, longitude = longitude!!)
                                    }.onSuccess {
                                        progressBar.visibility = View.GONE

                                        tvCity.text = "${it?.cityName}"
                                        tvTemp.text = getString(R.string.temp_string, it?.main?.temp.toString())
                                        name = it?.cityName.toString()
                                        temp = it?.main?.temp.toString()
                                        pressure = it?.main?.pressure.toString()
                                        wind = it?.windInfo?.speed.toString()
                                        humidity = it?.main?.humidity.toString()
                                        iconID = it?.weatherList?.get(0)?.icon.toString()

                                    }.onFailure {
                                        progressBar.visibility = View.GONE
                                        Toast.makeText(requireContext(), "Error: $it", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                        }
                    } else {
                        Toast.makeText(requireContext(), "Включите местоположение", Toast.LENGTH_LONG).show()
                        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                        startActivity(intent)
                    }
                } else {
                    requestLocationAccess()
                }
            }

            tvCity.setOnClickListener {
                childFragmentManager.setFragmentResult("requestKey",
                    bundleOf("nameKey" to name,
                        "tempKey" to temp,
                        "windKey" to wind,
                        "pressureKey" to pressure,
                        "humidityKey" to humidity,
                        "iconKey" to iconID)
                )
                val bottomSheetFragment = WeatherBottomSheetFragment()
                bottomSheetFragment.show(childFragmentManager, WeatherBottomSheetFragment.TAG)
            }
        }

    }

    companion object{
        const val MAIN_FRAGMENT_TAG = "MAIN_FRAGMENT_TAG"
        fun newInstance(bundle: Bundle) =
            MainFragment().apply {
                arguments = Bundle().apply {
                    putAll(bundle)
                }
            }
    }
}