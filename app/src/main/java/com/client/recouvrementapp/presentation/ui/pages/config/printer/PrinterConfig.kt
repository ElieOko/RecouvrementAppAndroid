package com.client.recouvrementapp.presentation.ui.pages.config.printer

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.client.recouvrementapp.data.shared.StoreData
import com.client.recouvrementapp.domain.viewmodel.ApplicationViewModel
import com.client.recouvrementapp.presentation.components.elements.TopBarSimple
import com.partners.hdfils_recolte.presentation.ui.components.Space
import com.qs.helper.printer.Device
import com.qs.helper.printer.PrintService
import kotlinx.coroutines.launch

@Composable
fun PrinterConfig(
    onBackEvent: () -> Unit,
    vm: ApplicationViewModel? = viewModel()
) {
    PrinterConfigBody(onBackEvent,vm)
}

@SuppressLint("UnrememberedMutableState", "CoroutineCreationDuringComposition")
@Composable
fun PrinterConfigBody(
    onBackEvent: () -> Unit = {},
    vm: ApplicationViewModel? = viewModel()
) {
    val context = LocalContext.current
    val scopeCoroutine = rememberCoroutineScope()
    val deviceList: MutableList<Device?> = vm?.configuration?.printer?.deviceList as MutableList<Device?>
    var bluetoothStatus by mutableStateOf("")
    deviceList.clear()
    if (!PrintService.pl.IsOpen()){
        PrintService.pl.open(context)
    }


    Scaffold(
        topBar = {
            TopBarSimple(
                title = "Configuration Printer",
                isMain = false,
                onBackEvent = onBackEvent
            )
        }
    ) {
        Column(Modifier
            .padding(it)
            .padding(5.dp)) {
            Button(onClick = {
                val device = vm.configuration.printer.scan()!!
                device.forEach{ dev->
                   if(deviceList.none{ d -> d?.deviceAddress == dev?.deviceAddress }){
                       deviceList.add(dev)
                   }
                }
                scopeCoroutine.launch {
                    Toast.makeText(context,"${deviceList.size}", Toast.LENGTH_SHORT).show()
                }
            },colors = ButtonDefaults.buttonColors(
                containerColor =  Color(0xFF15D77D),
                disabledContentColor = Color(0xFF080624),
                disabledContainerColor = Color(0xFF080624)
            )) {
                Text("Scanning", color = Color.White, fontSize = 16.sp)
            }
            Text(bluetoothStatus, color = Color.Black)
            HorizontalDivider()
            deviceList.forEachIndexed { i, device->
                Row(modifier = Modifier.clickable{
                    scopeCoroutine.launch {
                        val cmd = device?.deviceAddress.toString()
                        try {
                            val connect: Boolean = PrintService.pl.connect(cmd)
                            if (connect){
                                StoreData(context).setBluetoothPrinter(cmd)
                            }
                        }
                        catch (e: Exception){
                            Toast.makeText(context,e.message, Toast.LENGTH_SHORT).show()
                            Log.e("***************************",e.message.toString())
                        }
                    }

                }) {
                    Text("${device?.deviceName}", color = Color.Black)
                    Space(x = 10)
                    Text("${device?.deviceAddress}", color = Color.Black)
                }
            }

        }
    }
}

@Composable
@Preview(showBackground = true)
fun PrinterConfigPreview() {
    PrinterConfigBody()
}