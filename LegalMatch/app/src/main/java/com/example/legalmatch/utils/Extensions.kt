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