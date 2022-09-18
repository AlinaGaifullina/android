package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity()  {

    private lateinit var binding: ActivityMainBinding
    var counterValue = 0
    var color = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(savedInstanceState == null){
            val fragment = ButtonsFragment.newInstance(
                counterValue = counterValue,
                color = color
            )
            supportFragmentManager
                .beginTransaction()
                .add(R.id.main_fragments_container, fragment)
                .commit()
        }
    }
}