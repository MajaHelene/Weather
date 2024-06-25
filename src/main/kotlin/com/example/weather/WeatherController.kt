package com.example.weather
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class WeatherController(private val weatherService: WeatherService) {
    @GetMapping("/alerts")
    fun getWeather(): List<WeatherAlert> {
        return weatherService.getWeatherData(null, null)
    }

    @GetMapping("/nearby")
    fun getNearby(lat: Double, lon: Double): List<WeatherAlert> {
        return weatherService.getWeatherData(lat, lon)
    }
}