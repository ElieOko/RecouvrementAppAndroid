package com.client.recouvrementapp

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.client.recouvrementapp.domain.connectivity.AndroidConnectivityObserver
import com.client.recouvrementapp.domain.connectivity.ConnectivityViewModel
import com.client.recouvrementapp.domain.route.Navigation
import com.client.recouvrementapp.domain.viewmodel.ParentViewModel
import com.client.recouvrementapp.domain.viewmodel.config.PrinterConfigViewModel
import com.client.recouvrementapp.presentation.ui.theme.RecouvrementAppTheme
import com.qs.helper.printer.Device
import com.qs.helper.printer.PrintService
import com.qs.helper.printer.PrinterClass
import com.qs.helper.printer.bt.BtService

class MainActivity : ComponentActivity() {
    private lateinit var navHostController: NavHostController
//    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>
//    private lateinit var permissionLauncherMain: ActivityResultLauncher<Intent>
    var mhandler: Handler? = null
    var handler: Handler? = null
    val MESSAGE_STATE_CHANGE: Int = 1
    val MESSAGE_READ: Int = 2
    val MESSAGE_WRITE: Int = 3
    val MESSAGE_DEVICE_NAME: Int = 4
    lateinit var bluetoothStatus : String
    var checkState: Boolean = true
    var tv_update: Thread? = null
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            navHostController = rememberNavController()
            RecouvrementAppTheme {
                val vm = viewModel<ParentViewModel>()
                vm.vmPrinterConfig = viewModel<PrinterConfigViewModel>()
                val viewModel = viewModel<ConnectivityViewModel> {
                    ConnectivityViewModel(
                        connectivityObserver = AndroidConnectivityObserver(
                            context = applicationContext
                        )
                    )
                }
                val isConnected by viewModel.isConnected.collectAsStateWithLifecycle()
                initPrinterService(vm)
                PrintService.pl = BtService(this, mhandler, handler)
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Navigation(navHostController,innerPadding, vm, isConnected)
                }
            }
        }

    }
    private fun initPrinterService(vm: ParentViewModel) {
        handler = @SuppressLint("HandlerLeak")
        object : Handler() {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                when (msg.what) {
                    0 -> {}
                    1 -> {
                        val d = msg.obj as Device
                        if (!checkData(vm.vmPrinterConfig?.deviceList , d)) {
                            vm.vmPrinterConfig?.addDevice(d)
                        }
                    }
                    2 -> {}
                }
            }
        }
        mhandler = @SuppressLint("HandlerLeak")
        object : Handler() {
            @SuppressLint("HandlerLeak")
            override fun handleMessage(msg: Message) {
                when (msg.what) {
                    MainActivity().MESSAGE_READ -> {
                        val readBuf = msg.obj as ByteArray
                        val readMessage = String(readBuf, 0, msg.arg1)
                        // Log.i(MainActivity.TAG, "readMessage=" + readMessage)
                        //  Log.i(MainActivity.TAG, "readBuf:" + readBuf[0])
                        if (readBuf[0].toInt() == 0x13) {
                            PrintService.isFUll = true

                        } else if (readBuf[0].toInt() == 0x11) {
                            PrintService.isFUll = false

                        } else if (readBuf[0].toInt() == 0x08) {

                        } else if (readBuf[0].toInt() == 0x01) {
                            //ShowMsg(getResources().getString(R.string.str_printer_state)+":"+getResources().getString(R.string.str_printer_printing));
                        } else if (readBuf[0].toInt() == 0x04) {

                        } else if (readBuf[0].toInt() == 0x02) {

                        } else {
                            if (readMessage.contains("800"))  // 80mm paper
                            {
                                PrintService.imageWidth = 72
                                Toast.makeText(
                                    applicationContext, "80mm",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else if (readMessage.contains("580"))  // 58mm paper
                            {
                                PrintService.imageWidth = 48
                                Toast.makeText(
                                    getApplicationContext(), "58mm",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }

                    MainActivity().MESSAGE_STATE_CHANGE -> when (msg.arg1) {
                        PrinterClass.STATE_CONNECTED -> {}
                        PrinterClass.STATE_CONNECTING -> Toast.makeText(
                            applicationContext,
                            "STATE_CONNECTING", Toast.LENGTH_SHORT
                        ).show()

                        PrinterClass.STATE_LISTEN, PrinterClass.STATE_NONE -> {}
                        PrinterClass.SUCCESS_CONNECT -> {
                            /*PrintService.pl.write([] { 0x1b, 0x2b }); */

                            try {
                                Thread.sleep(10)
                            } catch (e: InterruptedException) {
                                // TODO Auto-generated catch block
                                e.printStackTrace()
                            }
                            PrintService.pl.write(byteArrayOf(0x1d, 0x67, 0x33))
                            Toast.makeText(
                                applicationContext,
                                "SUCCESS_CONNECT", Toast.LENGTH_SHORT
                            ).show()
                        }
                        PrinterClass.FAILED_CONNECT -> Toast.makeText(
                            applicationContext,
                            "FAILED_CONNECT", Toast.LENGTH_SHORT
                        ).show()

                        PrinterClass.LOSE_CONNECT -> Toast.makeText(
                            applicationContext, "LOSE_CONNECT",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    MainActivity().MESSAGE_WRITE -> {}
                }
                super.handleMessage(msg)
            }
        }
        tv_update = object : Thread() {
            override fun run() {
                while (true) {
                    if (checkState) {
                        try {
                            sleep(500)
                        } catch (e: InterruptedException) {
                            // TODO Auto-generated catch block
                            e.printStackTrace()
                        }
                        // fun run() {
                        // TODO Auto-generated method stub
                        if (PrintService.pl != null) {
                            if (PrintService.pl.state == PrinterClass.STATE_CONNECTED) {
                                bluetoothStatus = "connected"
                                PrintService.pl.stopScan()
                            } else if (PrintService.pl.state == PrinterClass.STATE_CONNECTING) {
                                bluetoothStatus = "connecting to"
                            } else if (PrintService.pl.state == PrinterClass.STATE_SCAN_STOP) {
                                bluetoothStatus = "scanning is finished"

                            } else if (PrintService.pl.state == PrinterClass.STATE_SCANING) {
                                bluetoothStatus = "scanning"
                            } else {
                                val ss = PrintService.pl.state
                                bluetoothStatus = "not connected"
                            }
                        }
                        //}

                    }
                }
            }
        }
        tv_update!!.start()
    }

    private fun checkData(list: MutableList<Device?>?, d: Device): Boolean {
        if (list != null) {
            for (device in list) {
                if (device?.deviceAddress == d.deviceAddress) {
                    return true
                }
            }
        }
        return false
    }

}

