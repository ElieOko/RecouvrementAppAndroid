package com.client.recouvrementapp.domain.model.printer

import android.bluetooth.BluetoothDevice
import android.os.Handler
import android.os.Looper
import android.content.Context
import android.widget.Toast
import com.client.recouvrementapp.domain.tools.printer.PrinterBluetoothUtils


class BluetoothPrinter(private val device: BluetoothDevice, private val context: Context) : Runnable {

    override fun run() {
        try {
                PrinterBluetoothUtils.printSimple(device, context)
            Handler(Looper.getMainLooper()).post {
                //setLoading(false)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Handler(Looper.getMainLooper()).post {
                //setLoading(false)
                Toast.makeText(context, "Error:" + e.message, Toast.LENGTH_LONG).show()
            }
        }
    }
}
