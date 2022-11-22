package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.fragments.FirstFragment

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
                FirstFragment.newInstance(Bundle()),
                FirstFragment.FIRST_FRAGMENT_TAG
            ).commit()
    }
}