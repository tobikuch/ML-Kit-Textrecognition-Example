package com.example.ml_kit_textrecognition_example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ml_kit_textrecognition_example.ui.main.MainFragment
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Timber.plant(Timber.DebugTree())

        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow()
        }
    }
}