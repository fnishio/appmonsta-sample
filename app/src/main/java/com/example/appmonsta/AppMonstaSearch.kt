package com.example.appmonsta

import android.util.JsonReader
import android.util.Log
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class AppMonstaSearch {
    companion object {
        const val APP_DETAILS_API =
                "https://api.appmonsta.com/v1/stores/android/details/"
        const val APP_DETAILS_API_PARAMS = ".json?country=ALL"
    }

    fun search(packageName: String) : String {
        val url = URL(AppMonstaSearch.APP_DETAILS_API + packageName +
                AppMonstaSearch.APP_DETAILS_API_PARAMS)
        val httpConnection = url.openConnection() as HttpsURLConnection
        httpConnection.setRequestProperty("Authorization",
                "Basic " + BuildConfig.ApiKey)
        try {
            val jsonReader = JsonReader(InputStreamReader(httpConnection.inputStream, "utf-8"))
            return getGenres(jsonReader)
        } catch (e: Exception) {
            Log.d("ERROR", e.toString())
        }
        return ""
    }

    private fun getGenres(reader: JsonReader): String {
        var genres = ""
        reader.beginObject()
        while(reader.hasNext()) {
            val name = reader.nextName()
            when (name) {
                "genres" -> {
                    genres = parseGenres(reader)
                }
                else -> {
                    reader.skipValue() // do nothing
                }
            }
        }
        reader.endObject()
        return genres
    }

    private fun parseGenres(reader: JsonReader) : String {
        var genres = ""
        reader.beginArray()
        while(reader.hasNext()) {
            genres += reader.nextString() + " "
        }
        reader.endArray()
        return genres
    }
}
