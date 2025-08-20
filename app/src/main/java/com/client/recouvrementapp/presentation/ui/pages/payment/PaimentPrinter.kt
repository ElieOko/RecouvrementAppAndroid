package com.client.recouvrementapp.presentation.ui.pages.payment

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.client.recouvrementapp.R
import com.client.recouvrementapp.core.PrinterByteFeature
import com.client.recouvrementapp.core.PrinterByteFeature.space
import com.client.recouvrementapp.core.printer.PrinterImage
import com.client.recouvrementapp.core.removeAccents
import com.client.recouvrementapp.data.shared.StoreData
import com.client.recouvrementapp.domain.viewmodel.ApplicationViewModel
import com.client.recouvrementapp.domain.viewmodel.PrinterViewModel
import com.client.recouvrementapp.presentation.components.elements.MAlertDialog
import com.client.recouvrementapp.presentation.components.elements.TopBarSimple
import com.partners.hdfils_recolte.presentation.ui.components.Space
import com.qs.helper.printer.PrintService
import kotlinx.coroutines.launch
import java.io.UnsupportedEncodingException

@Composable
fun PaimentPrinter(
    onBackEvent: () -> Unit,
    viewModelGlobal: ApplicationViewModel?,
    recouvementId: Int? = 0
) {
    PaimentPrinterBody(onBackEvent, viewModelGlobal,recouvementId)
}

@SuppressLint("UnrememberedMutableState", "CoroutineCreationDuringComposition",
    "SuspiciousIndentation"
)
@Composable
fun PaimentPrinterBody(
    onBackEvent: () -> Unit = {},
    vm: ApplicationViewModel? = null,
    recouvementId: Int? = null
) {
    val context = LocalContext.current
    val scopeCoroutine = rememberCoroutineScope()
    val viewModel: PrinterViewModel = viewModel()
    val detail = vm?.room?.recouvrement?.recouvrementDetail?.collectAsState()?.value
    val isActive = remember { mutableStateOf(true) }
    val btMap = BitmapFactory.decodeResource(context.resources,R.drawable.logo)
    var msg: String? by remember { mutableStateOf("") }
    var titleMsg by remember { mutableStateOf("Erreur") }
    var isShow by remember { mutableStateOf(false) }

    LaunchedEffect(Unit){
        scopeCoroutine.launch {
            vm?.room?.recouvrement?.getDetailRecouvrement(recouvementId!!)
        }
        scopeCoroutine.launch {
            StoreData(context).getBluetoothPrinter.collect { cmd ->
                if(cmd != null){
                    PrintService.pl.connect(cmd)
                }
            }
        }
        PrintService.pl.open(context)
    }
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
                        isActive.value = false
//                        PrintService.LanguageStr = "CP850"
                        scopeCoroutine.launch {
                            try {
                                val transactionType = when(detail?.recouvrement?.transactionType){
                                    "Subscription"-> "FJS Devise"
                                    "LoanRepay"-> "Micro-Pret"
                                    "Savings" -> "Epargne"
                                    "CotisationOrdinaire" -> "Cotisation Ordinaire"
                                    "CotisationSpesm" -> "Cotisation Spesm"
                                    else-> ""
                                }
                                val devise = when(detail?.currency?.code){
                                    "USD"-> "Dollar Americain"
                                    "CDF"-> "Franc Congolais"
                                    else -> ""
                                }
                                val sendTitle = PrinterByteFeature.title.toByteArray(charset("GBK"))
                                val sendSubTitle = PrinterByteFeature.subTitle.uppercase().toByteArray(charset("GBK"))
                                val sendMethodPayment = "Mode de paiement${space(17 - "Mode de paiement".length)}: ${removeAccents(detail?.paymentMethod?.name?.uppercase() as String)}".toByteArray(charset("GBK"))
                                PrintService.pl.write(PrinterByteFeature.centerAlignemt)
                                PrintService.pl.write(PrinterByteFeature.boldOff)
                                PrintService.pl.write(sendTitle)
                                PrintService.pl.write(PrinterByteFeature.line(0x01))
                                PrintService.pl.write(PrinterByteFeature.boldOn)
                                PrintService.pl.write(PrinterByteFeature.centerAlignemt)
                                PrintService.pl.write(sendSubTitle)
                                PrintService.pl.write(PrinterByteFeature.line(0x01))
                                PrintService.pl.write(PrinterByteFeature.leftAlignemt)
                                PrintService.pl.printText("De${space(5 - "De".length)}:\t ${detail.member?.name}\n")
                                PrintService.pl.printText("Montant${space(17 - "Montant".length)}: ${detail.recouvrement?.amount}\n")
                                PrintService.pl.printText("Devise${space(11 - "Devise".length)} : $devise\n")
                                PrintService.pl.write(sendMethodPayment)
                                PrintService.pl.write(PrinterByteFeature.line(0x01))
                                PrintService.pl.printText("Projet${space(18 - "Projet".length)}: $transactionType\n")
                                PrintService.pl.printText("Date et heure : ${detail.recouvrement?.createdOn} ${detail.recouvrement?.time}\n")
                                PrintService.pl.printText("Par${space(5 - "Par".length)}: Agent ${detail.user?.displayName?.uppercase()}\n")
                                PrintService.pl.write(PrinterByteFeature.line(0x02))
                                PrintService.pl.printText("Contact${space(2)}: ${PrinterByteFeature.phone}\n")
                                PrintService.pl.write(PrinterByteFeature.centerAlignemt)
                                PrintService.pl.write(PrinterByteFeature.line(0x02))
                                PrintService.pl.printText("Merci")
                                PrintService.pl.write(PrinterByteFeature.boldOff)
                                PrintService.pl.write(PrinterByteFeature.line(0x01))
                                PrintService.pl.printText("Fondatation Jonathan Sangu")
                                PrintService.pl.write(PrinterByteFeature.line(0x04))
                                //PrintService.pl.write(PrinterByteFeature.line(0x01))
                                //PrintService.pl.write(PrinterImage.draw2PxPoint(btMap))
                                PrintService.pl.write(byteArrayOf(0x1d, 0x0c))

                            }
                            catch (e : UnsupportedEncodingException){
                                e.printStackTrace()
                                msg         = e.message
                                titleMsg    = "Exception"
                                isShow      = true
                                Log.e("**********",e.message.toString())
                                Toast.makeText(context,e.message.toString(), Toast.LENGTH_SHORT).show()
                            }
                            catch (e : Exception){
                                msg         = e.message
                                titleMsg    = "Exception"
                                isShow      = true
                            }
                            //PrintService.pl.write(byteArrayOf(0x1d, 0x0c))
                            isActive.value = true
                        }
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
        if(isShow){
            MAlertDialog(
                dialogTitle = titleMsg,
                dialogText =  "$msg",
                onDismissRequest = {
                    isShow = false
                }
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PaimentPrinterPreview() {
    PaimentPrinterBody()
}