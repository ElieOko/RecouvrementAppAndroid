package com.client.recouvrementapp.domain.viewmodel

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.client.recouvrementapp.core.permissions.PermissionAndroid.BLUETOOTH_CONNECT
import com.client.recouvrementapp.domain.model.core.printer.BluetoothPrinter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PrinterViewModel : ViewModel() {

    private val _loading = mutableStateOf(false)
    val loading = mutableStateOf(_loading)

    @SuppressLint("ServiceCast")
    fun onPrintClicked(context: Context, bluetoothAddress: String?) {
        val bluetoothAdapter = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val bluetoothManager =
                context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
            bluetoothManager.adapter
        } else {
            @Suppress("DEPRECATION")
            BluetoothAdapter.getDefaultAdapter()
        }
        val hasPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                context,
                BLUETOOTH_CONNECT
            ) == PackageManager.PERMISSION_GRANTED
        } else true

        if (bluetoothAdapter != null && bluetoothAddress != null && hasPermission) {
            if (!bluetoothAdapter.isEnabled) bluetoothAdapter.enable()
            bluetoothAdapter.cancelDiscovery()
            _loading.value = true
            val device = bluetoothAdapter.getRemoteDevice(bluetoothAddress)
            viewModelScope.launch(Dispatchers.IO) {
                BluetoothPrinter(device, context).run()
                _loading.value = false
            }
        }
    }
}
