package com.example.weather
import okhttp3.OkHttpClient
import okhttp3.Request
import org.springframework.stereotype.Repository
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.StringReader
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale


@Repository
class WeatherRepository {

    private val client = OkHttpClient()

    fun fetchWeatherData(): List<WeatherAlert> {
        val request = Request.Builder()
                .url("https://api.met.no/weatherapi/metalerts/2.0/current")
                .build()

        client.newCall(request).execute().use { response ->
            val rss = response.body!!.string()
            return parseWeatherData(rss)
        }
    }

    fun parseWeatherData(data: String): List<WeatherAlert> {
        val factory = XmlPullParserFactory.newInstance()
        factory.isNamespaceAware = true
        val parser = factory.newPullParser()

        parser.setInput(StringReader(data))

        val weatherAlerts = mutableListOf<WeatherAlert>()
        var currentWeatherAlert: WeatherAlert? = null
        var text = ""

        var eventType = parser.eventType
        while (eventType != XmlPullParser.END_DOCUMENT) {
            val tagName = parser.name
            when (eventType) {
                XmlPullParser.START_TAG -> {
                    if (tagName == "item") {
                        currentWeatherAlert = WeatherAlert()
                    }
                }

                XmlPullParser.TEXT -> {
                    text = parser.text
                }

                XmlPullParser.END_TAG -> {
                    if (currentWeatherAlert != null) {
                        when (tagName) {
                            "title" -> currentWeatherAlert.title = text
                            "description" -> currentWeatherAlert.description = text
                            "link" -> currentWeatherAlert.link = text
                            "author" -> currentWeatherAlert.author = text
                            "guid" -> currentWeatherAlert.guid = text
                            "pubDate" -> currentWeatherAlert.pubDate = parseTimestamp(text)
                            "polygon" -> {
                                val points = text.split(" ")
                                        .chunked(2)
                                        .map { Pair(it[0].toDouble(), it[1].toDouble()) }
                                currentWeatherAlert.geoRssPolygons.add(points)
                            }
                            "item" -> {
                                weatherAlerts.add(currentWeatherAlert)
                                currentWeatherAlert = null
                            }
                        }
                    }
                }
            }
            eventType = parser.next()
        }

        return weatherAlerts
    }

    fun parseTimestamp(timestamp: String): ZonedDateTime {
        val formatter = DateTimeFormatter.ofPattern("EEE, d MMM yyyy HH:mm:ss Z", Locale.ENGLISH)
        val trimmedTimestamp = timestamp.replace("  ", " ")
        return ZonedDateTime.parse(trimmedTimestamp, formatter)
    }
}