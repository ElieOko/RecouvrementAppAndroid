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
import androidx.activity.viewModels
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
import com.client.recouvrementapp.domain.viewmodel.ApplicationViewModel
import com.client.recouvrementapp.domain.viewmodel.ConfigurationViewModel
import com.client.recouvrementapp.domain.viewmodel.InstanceRoomViewModel
import com.client.recouvrementapp.domain.viewmodel.KtorViewModel
import com.client.recouvrementapp.domain.viewmodel.config.PrinterConfigViewModel
import com.client.recouvrementapp.domain.viewmodel.room.CurrencyViewModel
import com.client.recouvrementapp.domain.viewmodel.room.CurrencyViewModelFactory
import com.client.recouvrementapp.domain.viewmodel.room.PaymentMethodViewModel
import com.client.recouvrementapp.domain.viewmodel.room.PaymentMethodViewModelFactory
import com.client.recouvrementapp.domain.viewmodel.room.PeriodViewModel
import com.client.recouvrementapp.domain.viewmodel.room.PeriodViewModelFactory
import com.client.recouvrementapp.domain.viewmodel.room.RecouvrementViewModel
import com.client.recouvrementapp.domain.viewmodel.room.RecouvrementViewModelFactory
import com.client.recouvrementapp.domain.viewmodel.room.TransactionTypeViewModel
import com.client.recouvrementapp.domain.viewmodel.room.TransactionTypeViewModelFactory
import com.client.recouvrementapp.domain.viewmodel.room.UserViewModel
import com.client.recouvrementapp.domain.viewmodel.room.UserViewModelFactory
import com.client.recouvrementapp.presentation.ui.theme.RecouvrementAppTheme
import com.qs.helper.printer.Device
import com.qs.helper.printer.PrintService
import com.qs.helper.printer.PrinterClass
import com.qs.helper.printer.bt.BtService

class MainActivity : ComponentActivity() {
    private lateinit var navHostController: NavHostController

    private val userViewModel: UserViewModel by viewModels {
        UserViewModelFactory((application as RecouvrementApplication).userRepository)
    }

    private val periodViewModel: PeriodViewModel by viewModels {
        PeriodViewModelFactory((application as RecouvrementApplication).periodRepository)
    }

    private val currencyViewModel: CurrencyViewModel by viewModels {
        CurrencyViewModelFactory((application as RecouvrementApplication).currencyRepository)
    }

    private val paymentMethodViewModel: PaymentMethodViewModel by viewModels {
        PaymentMethodViewModelFactory((application as RecouvrementApplication).paymentMethodRepository)
    }

    private val transactionTypeViewModel: TransactionTypeViewModel by viewModels {
        TransactionTypeViewModelFactory((application as RecouvrementApplication).transactionTypeRepository)
    }

    private val recouvrementViewModel: RecouvrementViewModel by viewModels {
        RecouvrementViewModelFactory((application as RecouvrementApplication).recouvrementRepository)
    }

    var mhandler: Handler? = null
    var handler: Handler? = null
    val MESSAGE_STATE_CHANGE: Int = 1
    val MESSAGE_READ: Int = 2
    val MESSAGE_WRITE: Int = 3
    val MESSAGE_DEVICE_NAME: Int = 4
    lateinit var bluetoothStatus : String
    var checkState: Boolean = true
    var tvUpdate: Thread? = null
    private var backPressedTime: Long = 0
    private lateinit var toast: Toast
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            navHostController = rememberNavController()
            RecouvrementAppTheme {
                val ktorClientViewModel =viewModel<KtorViewModel>()
                val printerViewModel = viewModel<PrinterConfigViewModel>()
                val connectivityViewModel = viewModel<ConnectivityViewModel> {
                    ConnectivityViewModel(
                        connectivityObserver = AndroidConnectivityObserver(
                            context = applicationContext
                        )
                    )
                }
                val isConnected by connectivityViewModel.isConnected.collectAsStateWithLifecycle()
                val applicationViewModel = viewModel<ApplicationViewModel>{
                    ApplicationViewModel(
                        roomVm = InstanceRoomViewModel(
                            periodViewModel = periodViewModel,
                            transactionTypeViewModel = transactionTypeViewModel,
                            currencyViewModel = currencyViewModel,
                            userViewModel = userViewModel,
                            recouvrementViewModel = recouvrementViewModel,
                            paymentMethodViewModel = paymentMethodViewModel
                        ),
                        configurationVm = ConfigurationViewModel(
                            printerViewModel = printerViewModel,
                            isConnectNetworkState = isConnected,
                            ktorClient = ktorClientViewModel
                        )
                    )
                }
                applicationViewModel.room.period = periodViewModel
                applicationViewModel.room.transactionType = transactionTypeViewModel
                applicationViewModel.room.currency = currencyViewModel
                applicationViewModel.room.period = periodViewModel
                applicationViewModel.room.user = userViewModel
                applicationViewModel.room.recouvrement = recouvrementViewModel
                applicationViewModel.room.paymentMethod = paymentMethodViewModel
                applicationViewModel.configuration.printer = printerViewModel
                applicationViewModel.configuration.isConnectNetwork = isConnected
                applicationViewModel.configuration.ktor = ktorClientViewModel
                initPrinterService(printerViewModel)
                PrintService.pl = BtService(this, mhandler, handler)
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    Navigation(navHostController,applicationViewModel)
                }
            }
        }
//        onBackInvokedDispatcher.registerOnBackInvokedCallback(
//            OnBackInvokedDispatcher.PRIORITY_DEFAULT
//        ) {
//            val currentTime = System.currentTimeMillis()
//
//            if (currentTime - backPressedTime < 2000) {
//                if (::toast.isInitialized) toast.cancel()
//                finish() // ferme l'activité
//            } else {
//                toast = Toast.makeText(this@MainActivity, "Appuyez encore pour quitter", Toast.LENGTH_SHORT)
//                toast.show()
//                backPressedTime = currentTime
//            }
//        }
    }
    private fun initPrinterService(vm: PrinterConfigViewModel) {
        handler = @SuppressLint("HandlerLeak")
        object : Handler() {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                when (msg.what) {
                    0 -> {}
                    1 -> {
                        val d = msg.obj as Device
                        if (!checkData(vm.deviceList , d)) {
                            vm.addDevice(d)
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

                        if (readBuf[0].toInt() == 0x13) {
                            PrintService.isFUll = true

                        } else if (readBuf[0].toInt() == 0x11) {
                            PrintService.isFUll = false

                        } else if (readBuf[0].toInt() == 0x08) {

                        } else if (readBuf[0].toInt() == 0x01) {

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
                                    applicationContext, "58mm",
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
        tvUpdate = object : Thread() {
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
                    }
                }
            }
        }
        tvUpdate!!.start()
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

