package com.client.recouvrementapp.presentation.ui.pages.payment

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.client.recouvrementapp.R
import com.client.recouvrementapp.core.hexStringToBytes
import com.client.recouvrementapp.domain.viewmodel.PrinterViewModel
import com.client.recouvrementapp.presentation.components.elements.TopBarSimple
import com.partners.hdfils_recolte.presentation.ui.components.Space
import com.qs.helper.printer.PrintService
import java.io.UnsupportedEncodingException

@Composable
fun PaimentPrinter(navC: NavHostController, onBackEvent: () -> Unit, isConnected: Boolean) {
    PaimentPrinterBody(navC, onBackEvent)
}

@SuppressLint("UnrememberedMutableState", "CoroutineCreationDuringComposition")
@Composable
fun PaimentPrinterBody(navC: NavHostController?= null, onBackEvent: () -> Unit ={}) {
    val context = LocalContext.current
    var bluetooth by mutableStateOf("")
    val scopeCoroutine = rememberCoroutineScope()
    val viewModel: PrinterViewModel = viewModel()
    //val print = PrintService.pl
    var isActive = remember { mutableStateOf(true) }
    PrintService.pl.open(context)
    Scaffold (
        topBar = {
            TopBarSimple(
                title = "Printer",
                isMain = false,
                onBackEvent = onBackEvent
            )
        }
    ){
        Column(Modifier
            .padding(it)
            .fillMaxSize()
            .padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {

            Button(
                    onClick = {
                       // viewModel.onPrintClicked(context,bluetooth)
                        //scopeCoroutine.launch {
                        isActive.value = false
                        PrintService.LanguageStr = "Japanese"
                            try {
                                val message = "hello bro welcome"
                                val delimiterTop = "----- DEBUT -----\n"
                                val delimiterBottom = "\n------ FIN ------\n"

                                val messageToPrint = delimiterTop + message + delimiterBottom

                                //val send = messageToPrint.toByteArray(charset("GBK"))

                                val bt: ByteArray = hexStringToBytes(messageToPrint)
                                PrintService.pl.write(messageToPrint.toByteArray())
                                PrintService.pl.printText("\n")
                                PrintService.pl.write(byteArrayOf(0x1B,0x0C))
                                Toast.makeText(context,"Hello", Toast.LENGTH_SHORT).show()
                                //return
                            }
                            catch (e : UnsupportedEncodingException){
                                e.printStackTrace();
                                Log.e("**********",e.message.toString())
                                Toast.makeText(context,e.message.toString(), Toast.LENGTH_SHORT).show()
                            }
                            //
                            //PrintService.pl.write(byteArrayOf(0x1d, 0x0c))
                            isActive.value = true

                       // }

                    },
                    enabled = isActive.value ,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor =  Color(0xFF080624),
                        disabledContentColor = Color(0xFF080624),
                        disabledContainerColor = Color(0xFF080624)
                    )
                ) {
                    Text(text ="Imprimer", fontSize = 16.sp, color = Color.White)
                    Space(x = 10)
                    Icon(painterResource(R.drawable.print),null, modifier = Modifier.size(24.dp), tint = Color.White)
                }
        }
    }
}


@Composable
@Preview(showBackground = true)
fun PaimentPrinterPreview() {
    PaimentPrinterBody()
}