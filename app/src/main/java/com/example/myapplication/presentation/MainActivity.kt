package com.example.myapplication.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.presentation.screen.MainFragment
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private  var viewBinding: ActivityMainBinding?= null
    private val containerID = R.id.container

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding?.root)


        if(savedInstanceState !=null) {
            return
        }

        supportFragmentManager.beginTransaction()
            .add(
                containerID,
                MainFragment.newInstance(Bundle()),
                MainFragment.MAIN_FRAGMENT_TAG
            ).commit()
    }
}