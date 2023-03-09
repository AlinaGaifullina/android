package com.example.myapplication.presentation.screen


import android.graphics.drawable.Drawable
import com.bumptech.glide.request.target.Target
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.FragmentResultListener
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentWeatherBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class WeatherBottomSheetFragment : BottomSheetDialogFragment(R.layout.fragment_weather_bottom_sheet) {
    private var _binding: FragmentWeatherBottomSheetBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentWeatherBottomSheetBinding.bind(view)
        parentFragmentManager.setFragmentResultListener("requestKey", this, FragmentResultListener { requestKey2, bundle ->
            with(binding){
                tvCityName.text = bundle.getString("nameKey")
                tvHumidity.text = getString(R.string.humidity_string, bundle.getString("humidityKey"))
                tvPressure.text = getString(R.string.pressure_string, bundle.getString("pressureKey"))
                tvTemp.text = getString(R.string.temp_string, bundle.getString("tempKey"))
                tvWindSpeed.text = getString(R.string.wind_string, bundle.getString("windKey"))
                Glide.with(root)
                    .load("https://openweathermap.org/img/wn/${bundle.getString("iconKey")}@2x.png")
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(p0: GlideException?, p1: Any?, p2: Target<Drawable>?, p3: Boolean): Boolean {
                            Log.e(TAG, "onLoadFailed")
                            progressBar.visibility = View.GONE;
                            return false
                        }
                        override fun onResourceReady(p0: Drawable?, p1: Any?, p2: Target<Drawable>?, p3: DataSource?, p4: Boolean): Boolean {
                            Log.d(TAG, "OnResourceReady")
                            progressBar.visibility = View.GONE;
                            return false
                        }
                    })
                    .into(ivWeather)
            }
        })

        }

    companion object {
        const val TAG = "WEATHER_BOTTOM_SHEET_FRAGMENT"
    }
}