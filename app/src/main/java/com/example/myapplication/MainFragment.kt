package com.example.myapplication

import android.app.ActivityManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.myapplication.AppServiceActionsEnum.*
import com.example.myapplication.databinding.FragmentMainBinding


class MainFragment : Fragment(R.layout.fragment_main) {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private var serviceIntent: Intent? = null
    private var imageUrl: String? = null

    private val imageUrlsArray get() = resources.getStringArray(R.array.img_array)
    private var urlIndex = 0


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMainBinding.bind(view)
        serviceIntent = Intent(
            binding.root.context,
            AppService::class.java
        )

        LocalBroadcastManager.getInstance(binding.root.context).registerReceiver(
            serviceReceiver, IntentFilter(
                AppService.IMAGE_INTENT_KEY
            )
        )

        with(binding) {
            btnStart.setOnClickListener { action(START_SERVICE) }
            btnDownload.setOnClickListener { action(DOWNLOAD_IMAGE) }
            btnShow.setOnClickListener { action(SHOW_IMAGE) }
            btnStop.setOnClickListener { action(STOP_SERVICE) }
        }
    }

    private val serviceReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {

            val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent?.getParcelableExtra(AppService.IMAGE_KEY, Bitmap::class.java)
            } else {
                intent?.getParcelableExtra(AppService.IMAGE_KEY) as? Bitmap
            }
            val message = intent?.getStringExtra(AppService.MESSAGE_KEY)
            println(message)
            when {
                bitmap != null -> binding.iv.setImageBitmap(bitmap)
                message != null -> Toast.makeText(binding.root.context, message, Toast.LENGTH_SHORT).show()}
        }
    }

    private fun action(action: AppServiceActionsEnum?) {
        with(binding.root.context) {
            val isRunning = (getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager)
                .getRunningServices(Integer.MAX_VALUE)
                .any { it.service.className == AppService::class.java.name }

            val isStartAction = action == START_SERVICE
            when {
                isStartAction && !isRunning -> {
                    startService(action)
                    Toast.makeText(this, getString(R.string.service_success), Toast.LENGTH_SHORT).show()
                }
                isStartAction && isRunning -> {
                    Toast.makeText(this, getString(R.string.service_is_running_error), Toast.LENGTH_SHORT).show()
                }
                action == DOWNLOAD_IMAGE && isRunning -> {

                    imageUrl = imageUrlsArray[urlIndex]
                    urlIndex++
                    urlIndex %= imageUrlsArray.size
                    serviceIntent?.putExtra(AppService.IMAGE_URL_KEY, imageUrl)
                    startService(action)
                }
                !isStartAction && isRunning -> {
                    startService(action)
                }
                else -> {
                    Toast.makeText(this, getString(R.string.service_not_running_error), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        LocalBroadcastManager.getInstance(binding.root.context).unregisterReceiver(serviceReceiver)
    }

    private fun startService(action: AppServiceActionsEnum?) {
        serviceIntent?.action = action?.name
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            activity?.startForegroundService(serviceIntent)
        } else {
            activity?.startService(serviceIntent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    companion object {
        const val MAIN_FRAGMENT_TAG = "MAIN_FRAGMENT_TAG"
        fun newInstance(bundle: Bundle) =
            MainFragment().apply {
                arguments = Bundle().apply {
                    putAll(bundle)
                }
            }
    }
}