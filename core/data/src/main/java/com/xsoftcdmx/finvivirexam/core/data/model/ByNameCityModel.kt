package com.xsoftcdmx.finvivirexam.core.data.model

import com.xsoftcdmx.finvivirexam.core.model.geocoding.GeocodingResponseItem
import com.xsoftcdmx.network.model.geocoding.NetworkGeocodingResponseItem

fun NetworkGeocodingResponseItem.toDomainModel(): GeocodingResponseItem =
    GeocodingResponseItem(
        name = name.orEmpty(),
        lat = lat ?: 0.0,
        lon = lon ?: 0.0,
        country = country.orEmpty(),
        state = state.orEmpty()
    )