package de.muensterhack.start

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import de.muensterhack.R
import org.koin.android.viewmodel.ext.android.viewModel

class StartActivity : AppCompatActivity() {

    private val viewModel: StartViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


}
