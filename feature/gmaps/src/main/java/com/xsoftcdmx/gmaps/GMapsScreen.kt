package com.xsoftcdmx.gmaps

import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.xsoftcdmx.gmaps.composable.GMapsSearchBar
import kotlinx.coroutines.CoroutineScope
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GmapsScreen(
    mViewModel: GoogleMapsViewModel = hiltViewModel()
) {
    val uiState by mViewModel.uiState.collectAsState()
    var cityInput by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()
    BottomSheetScaffold(
        modifier = Modifier,
        scaffoldState = scaffoldState,
        sheetContent = {
            Column(modifier = Modifier.padding(16.dp).fillMaxSize()) {
                Text("Finvivir Exam", fontStyle = FontStyle.Normal, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleLarge)
                if (uiState.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                }

                uiState.error?.let { msg ->
                    Text(
                        text = "Error: $msg",
                        color = Color.Red,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }

                uiState.geo?.let { geo ->

                    Text(
                        text = "Ciudad $geo.e",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Spacer(Modifier.height(8.dp))

                // 5) Clima (weather)
                uiState.weather?.let { w ->
                    Column(modifier = Modifier) {
                        Text(
                            text = "Clima en ${w.cityName}",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(Modifier.height(8.dp))
                        Text("Temperatura: ${w.temperature}°C")
                        Text("Sensación térmica: ${w.feelsLike}°C")
                        Text("Mínima: ${w.minTemp}°C")
                        Text("Máxima: ${w.maxTemp}°C")
                        Spacer(Modifier.height(8.dp))
                        Text("Presión atmosférica: ${w.pressure} hPa")
                        Text("Humedad: ${w.humidity}%")
                        Text("Nivel del mar: ${w.seaLevel} hPa")
                        Text("Nivel del suelo: ${w.groundLevel} hPa")
                    }
                }
            }
        },
        sheetPeekHeight = 230.dp,
        content = {
            GMapsScreenContent(
                modifier = Modifier,
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GMapsScreenContent(
    modifier: Modifier = Modifier,
    mViewModel: GoogleMapsViewModel = hiltViewModel()
) {
    val cameraPositionState = rememberCameraPositionState()
    val context = LocalContext.current
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    val selectedLocation by mViewModel.selectedLocation.collectAsState(initial = null)
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            mViewModel.fetchUserLocation(context, fusedLocationClient)
        } else {
            Timber.e("Location permission was denied by the user.")
        }
    }

    LaunchedEffect(selectedLocation) {
        selectedLocation?.let { latLng ->
            cameraPositionState.animate(update = CameraUpdateFactory.newLatLngZoom(latLng, 5f))
            mViewModel.fetchWeather(latLng)
        }
    }

    LaunchedEffect(Unit) {
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) -> {
                mViewModel.fetchUserLocation(context, fusedLocationClient)
            }

            else -> {
                permissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }

    Column(modifier = modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(18.dp))
        GMapsSearchBar(
            onPlaceSelected = { place ->
                mViewModel.selectLocation(place, context)
            }
        )
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            selectedLocation?.let { latLng ->
                Marker(
                    state   = MarkerState(latLng),
                    title   = "Selected Location",
                    snippet = "Lat: ${latLng.latitude}, Lon: ${latLng.longitude}"
                )
            }
        }
    }
}
