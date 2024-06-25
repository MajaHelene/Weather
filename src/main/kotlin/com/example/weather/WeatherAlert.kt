package com.example.weather

import java.time.ZonedDateTime

data class WeatherAlert(
        var title: String? = null,
        var description: String? = null,
        var link: String? = null,
        var author: String? = null,
        var guid: String? = null,
        var pubDate: ZonedDateTime? = null,
        var geoRssPolygons: MutableList<List<Pair<Double, Double>>> = mutableListOf() // List of polygons( a set of lat/lon pairs)
)
