package com.example.philm.core.utils
import com.example.philm.BuildConfig

object AppConstants {

    const val CONNECT_TIMEOUT = 20L
    const val READ_TIMEOUT = 60L
    const val WRITE_TIMEOUT = 120L

    const val NETWORK_PAGE_SIZE =10
    const val STARTING_PAGE_INDEX = 1

    const val BASE_URL = "https://api.themoviedb.org/3/"

    const val STS_DEFAULT = "Something went wrong, Please try again!"
    const val NETWORK_FAILURE = "You are not connected to the internet. Make sure your network connection and try again."
    private const val TMDB_IMAGES_BASE_URL = "https://image.tmdb.org/t/p/"
    const val TMDB_POSTER_PREFIX = "${TMDB_IMAGES_BASE_URL}w500"
    const val CONVERSION_FAILURE = "Conversion Error"

    const val API_KEY = BuildConfig.TMDB_API_KEY
}

enum class TimeWindow {
    day,
    week,
}