package com.example.myapplication.fragments

import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentSecondBinding
import java.io.File


class SecondFragment : Fragment(R.layout.fragment_second) {
    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!

    private val resultLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success) {
            //Do nothing
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSecondBinding.bind(view)


        // Тут старый способ запроса разрешений, потому что я не разобралась с новым...
        // Просто чтобы хоть что-то было...
        with(binding) {
            val permission = android.Manifest.permission.CAMERA
            button.setOnClickListener {
                if (ContextCompat.checkSelfPermission(requireContext(), permission) == PackageManager.PERMISSION_GRANTED
                ) {
                    permissionGranted(permission)
                } else {
                    requestPermissions(arrayOf(android.Manifest.permission.CAMERA), REQUEST_CODE_CAMERA)
                }
            }
        }
    }

    private fun initTempUri(): Uri {
        val tempImagesDir = File(
            requireContext().filesDir,
            getString(R.string.temp_images_dir))

        tempImagesDir.mkdir()

        val tempImage = File(
            tempImagesDir,
            getString(R.string.temp_image))

        return FileProvider.getUriForFile(
            requireContext(),
            getString(R.string.authorities),
            tempImage)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CODE_CAMERA -> {
                val permission = if (permissions.isNotEmpty()) permissions.first() else return
                if (grantResults.first() == PackageManager.PERMISSION_GRANTED) {
                    permissionGranted(permission)
                } else {
                    Toast.makeText(requireContext(), "Разрешение отклонено", Toast.LENGTH_SHORT).show()
                    if (shouldShowRequestPermissionRationale(permission)) {
                        // Пользователь отклонин 1 раз

                    } else {
                        showOpenSettingsAlert()
                    }
                }
            }
            else -> {
                // Do nothing
            }
        }
    }

    private fun permissionGranted(permission: String) {
        //Как-будто достаточно было сделать так:
        //val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        //startActivityForResult(intent, 1)

        //но раз надо было с TakePicture, то вот:
        val tempImageUri = initTempUri()
        resultLauncher.launch(tempImageUri)
        //по ощущениям, я перемудрила
    }

    private fun showOpenSettingsAlert() {
        val settingsIntent = Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", requireContext().packageName, null)
        )
        if (requireActivity().packageManager.resolveActivity(
                settingsIntent, PackageManager.MATCH_DEFAULT_ONLY
            ) == null
        ) {

        } else {
            AlertDialog.Builder(requireContext())
                .setTitle("Вы отклонили разрешение на использование камеры")
                .setMessage("Это разрешение очень нужно. Предоставьте доступ, пожалуйста.")
                .setPositiveButton("Перейти к настройкам") { _, _ ->
                    startActivity(settingsIntent)
                }
                .create()
                .show()
        }
    }

    companion object{
        private const val REQUEST_CODE_CAMERA = 12101
        const val SECOND_FRAGMENT_TAG = "SECOND_FRAGMENT_TAG"
        fun newInstance(bundle: Bundle) =
            SecondFragment().apply {
                arguments = Bundle().apply {
                    putAll(bundle)
                }
            }
    }
}