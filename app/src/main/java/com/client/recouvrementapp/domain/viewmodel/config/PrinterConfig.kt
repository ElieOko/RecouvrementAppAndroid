package com.client.recouvrementapp.domain.viewmodel.config

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.qs.helper.printer.Device
import com.qs.helper.printer.PrintService

class PrinterConfigViewModel : ViewModel() {
    // Liste observable de périphériques (devices)
    private val _deviceList = mutableStateListOf<Device?>()
    val deviceList:MutableList<Device?> = _deviceList

    fun clearDeviceList() {
        _deviceList.clear()
    }
    fun scan(): MutableList<Device?>? {
        PrintService.pl.scan()
        return PrintService.pl.deviceList as MutableList<Device?>?
    }
//    PrintService.pl.scan()
    fun addDevice(device: Device?) {
        _deviceList.add(device)
    }

    fun setDeviceList(newList: List<Device?>) {
        _deviceList.clear()
        _deviceList.addAll(newList)
    }
}
