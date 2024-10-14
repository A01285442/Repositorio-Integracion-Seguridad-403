package com.example.legalmatch.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException



fun isInternetAvailable(context: Context): Boolean {
    var result = false
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkCapabilities = connectivityManager.activeNetwork ?: return false
    val actNw = connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
    result = when {
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
        else -> false
    }
    return result
}


fun md5(input: String): String {
    // Crear instancia de MessageDigest con el algoritmo MD5
    val md = MessageDigest.getInstance("MD5")

    // Procesar el string de entrada y obtener el hash
    val digest = md.digest(input.toByteArray())

    // Convertir el hash en un string hexadecimal
    return digest.joinToString("") { "%02x".format(it) }
}

fun toSpanish(month: Int): String {
    return if (month == 1){ "ene" }
    else if (month == 2){ "feb" }
    else if (month == 3){ "mar" }
    else if (month == 4){ "abr" }
    else if (month == 5){ "may" }
    else if (month == 6){ "jun" }
    else if (month == 7){ "jul" }
    else if (month == 8){ "ago" }
    else if (month == 9){ "sep" }
    else if (month == 10){ "oct" }
    else if (month == 11){ "nov" }
    else { "dic" }
}