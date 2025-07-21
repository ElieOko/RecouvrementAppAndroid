package com.client.recouvrementapp.domain.model.core.printer

import android.bluetooth.BluetoothDevice
import android.content.Context
import android.os.Handler
import android.os.Looper
import com.client.recouvrementapp.data.shared.StoreData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.UUID

suspend fun OpenPrinter(
    device: BluetoothDevice,
    context: Context,
    onFinish: () -> Unit,
    onError: (String) -> Unit
)  {
     try {
         val socket = device.createInsecureRfcommSocketToServiceRecord(
             UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
         )
         if (!socket.isConnected) {
             socket.connect()
         }
         val outputStream = socket.outputStream
         outputStream.write("\n".toByteArray())
         outputStream.flush()
         socket.close()
         // Back to main thread for UI interaction
             val localData = StoreData(context)
             CoroutineScope(Dispatchers.Main).launch {
                 localData.setBluetoothPrinter(device.address)
                 onFinish()
             }
             // Callback to notify success
     } catch (e: IOException) {
         e.printStackTrace()
         Handler(Looper.getMainLooper()).post {
             onError("Erreur: ${e.message}")
         }
     }
}
