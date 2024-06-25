package com.example.weather

import org.springframework.stereotype.Service

@Service
class WeatherService(private val weatherRepository: WeatherRepository) {

    fun getWeatherData(lat: Double?, lon:Double? ): List<WeatherAlert> {
        val weatherData = weatherRepository.fetchWeatherData()
        if(lat != null && lon != null){
        return weatherData.filter { weatherAlert ->
            weatherAlert.geoRssPolygons.any { polygon ->
                isInsidePolygon(lat, lon, polygon)
            }
        }
        }

        return weatherData
    }

    // Check if a point is inside a polygon using the ray-casting algorithm
    fun isInsidePolygon(lat: Double, lon: Double, polygon: List<Pair<Double, Double>>): Boolean {
        var isInside = false
        var j = polygon.size - 1
        for (i in polygon.indices) {
            val lati = polygon[i].first
            val loni = polygon[i].second
            val latj = polygon[j].first
            val lonj = polygon[j].second
            if (loni < lon && lonj >= lon || lonj < lon && loni >= lon) {
                if (lati + (lon - loni) / (lonj - loni) * (latj - lati) < lat) {
                    isInside = !isInside
                }
            }
            j = i
        }
        return isInside
    }
}