package com.client.recouvrementapp.data.local

import android.icu.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Constantes {
    companion object {
        const val BASE_URL = "https://dummyjson.com/"
        const val HOST_DEV = "192.168.1.84:8000/api"
        const val HOST_PROD = "custom.cadeauparfait1.com/api"
        const val IS_PROD = true
        const val TokenLocal = ""
        const val subscriptionId = 10
        const val cotisationOrdinaireId = 12
        const val cotisationSpesmId = 13
        const val API_INTERNET_MESSAGE="No Internet Connection"
        fun convertMillisToDate(millis: Long): String {
            val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            return formatter.format(Date(millis))
        }
    }

}