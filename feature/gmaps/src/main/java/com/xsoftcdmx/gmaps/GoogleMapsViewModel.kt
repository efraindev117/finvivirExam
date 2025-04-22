package com.xsoftcdmx.gmaps

import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.model.LatLng
import com.xsoftcdmx.domain.usescase.ByCityNameUseCase
import com.xsoftcdmx.domain.usescase.OpenWeatherByCoordinatesUseCase
import com.xsoftcdmx.finvivirexam.core.common.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class GoogleMapsViewModel @Inject constructor(
    private val getByCoords: OpenWeatherByCoordinatesUseCase,
    private val getByName: ByCityNameUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(GMapsUiState())
    val uiState: StateFlow<GMapsUiState> = _uiState.asStateFlow()

    private val _searchText = MutableStateFlow("")
    val searchText: StateFlow<String> = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching: StateFlow<Boolean> = _isSearching.asStateFlow()

    fun onSearchTextChanged(text: String) {
        _searchText.value = text
    }

    fun setIsSearching(active: Boolean) {
        _isSearching.value = active
    }

    private val _userLocation = mutableStateOf<LatLng?>(null)
    val userLocation: State<LatLng?> = _userLocation

    private val _selectedLocation = MutableStateFlow<LatLng?>(null)
    val selectedLocation: StateFlow<LatLng?> = _selectedLocation.asStateFlow()


    fun fetchUserLocation(context: Context, fusedLocationClient: FusedLocationProviderClient) {
        if (ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            try {
                fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                    location?.let {
                        val userLatLng = LatLng(it.latitude, it.longitude)
                        _userLocation.value = userLatLng
                    }
                }
            } catch (e: SecurityException) {
                Timber.e("Permission for location access was revoked: ${e.localizedMessage}")
            }
        } else {
            Timber.e("Location permission is not granted.")
        }
    }

    fun selectLocation(selectedPlace: String, context: Context) {
        viewModelScope.launch {
            val geocoder = Geocoder(context)
            val addresses = withContext(Dispatchers.IO) {
                geocoder.getFromLocationName(selectedPlace, 1)
            }
            if (!addresses.isNullOrEmpty()) {
                val address = addresses[0]
                val latLng = LatLng(address.latitude, address.longitude)
                _selectedLocation.value = latLng
            } else {
                Timber.tag("MapScreen").e("No location found for the selected place.")
            }
        }
    }

    fun onSearchCity(city: String) = viewModelScope.launch {
        getByName(city)
            .onEach { resource ->
                when (resource) {
                    is Resource.Loading ->
                        _uiState.update { it.copy(isLoading = true, error = null) }

                    is Resource.Success -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                geo = resource.data,
                                error = null
                            )
                        }
                    }

                    is Resource.Failure ->
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                error = resource.msg?.localizedMessage
                            )
                        }
                }
            }
            .launchIn(this)
    }

    fun fetchWeather(latLng: LatLng, units: String = "metric") {
        val lat = latLng.latitude
        val lon = latLng.longitude
        getByCoords(lat, lon, units)
            .onEach { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _uiState.update { it.copy(isLoading = true, error = null) }
                    }
                    is Resource.Success -> {
                        _uiState.update {
                            it.copy(isLoading = false, weather = resource.data, error = null)
                        }
                    }
                    is Resource.Failure -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                error = resource.msg?.localizedMessage
                            )
                        }
                    }
                }
            }
            .catch { e ->
                _uiState.update { it.copy(isLoading = false, error = e.message) }
            }
            .launchIn(viewModelScope)
    }

}