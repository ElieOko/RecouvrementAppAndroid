package com.client.recouvrementapp.core.permissions

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat


object PermissionAndroid {
    const val BLUETOOTH = Manifest.permission.BLUETOOTH
    const val BLUETOOTH_CONNECT = Manifest.permission.BLUETOOTH_CONNECT
    const val BLUETOOTH_ADMIN = Manifest.permission.BLUETOOTH_ADMIN
    const val BLUETOOTH_SCAN = Manifest.permission.BLUETOOTH_SCAN
    const val BLUETOOTH_ADVERTISE = Manifest.permission.BLUETOOTH_ADVERTISE
    const val ACCESS_NETWORK_STATE = Manifest.permission.ACCESS_NETWORK_STATE
    const val VIBRATE = Manifest.permission.VIBRATE
//    const val ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION
//    const val ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    const val NOTIFICATION = Manifest.permission.POST_NOTIFICATIONS
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    val permissionsToRequest = listOf(BLUETOOTH_CONNECT,NOTIFICATION)
}

/**
 * @author Elie Oko
 */
fun isPermissionGranted(name: String, context: Context):Boolean =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
        ContextCompat.checkSelfPermission(
            context, name
        ) == PackageManager.PERMISSION_GRANTED else true