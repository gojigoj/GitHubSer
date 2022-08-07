package com.gojigo.githubser.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

fun String?.replaceWhenEmpty(): String {
    return if (this.isNullOrEmpty()) "-" else this
}

@SuppressLint("SimpleDateFormat")
fun getDate(): String {
    val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
    val date = Date()
    return parser.format(date)
}