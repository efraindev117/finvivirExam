package com.xsoftcdmx.finvivirexam 

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.google.android.libraries.places.api.Places
import com.xsoftcdmx.finvivirexam.navigation.AppNavHost
import com.xsoftcdmx.finvivirexam.ui.theme.StarterTheme
import com.xsoftcdmx.finvivirexam.utils.ManifestUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val apiKey = ManifestUtils.getApiKeyFromManifest(this)
        if (!Places.isInitialized() && apiKey != null) {
            Places.initialize(applicationContext, "AIzaSyApGKicaer7YVaBzIYyTKubL-rfJm3RtFM")
        }
        enableEdgeToEdge()
        setContent {
            StarterTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppNavHost(Modifier.padding(innerPadding))
                }
            }
        }
    }
}
