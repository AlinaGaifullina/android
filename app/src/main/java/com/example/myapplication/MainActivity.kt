package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val containerID = R.id.container

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(savedInstanceState !=null) {
            return
        }
        supportFragmentManager.beginTransaction()
            .add(
                containerID,
                FirstFragment.newInstance(Bundle()),
                FirstFragment.FIRST_FRAGMENT_TAG
            ).commit()
    }
}