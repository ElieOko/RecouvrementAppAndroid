package com.client.recouvrementapp.presentation.ui.pages.recouvrement

import android.graphics.BitmapFactory
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.client.recouvrementapp.R
import com.client.recouvrementapp.core.PrinterByteFeature
import com.client.recouvrementapp.core.PrinterByteFeature.space
import com.client.recouvrementapp.core.printer.PrinterImage
import com.client.recouvrementapp.core.removeAccents
import com.client.recouvrementapp.data.shared.StoreData
import com.client.recouvrementapp.domain.viewmodel.ApplicationViewModel
import com.client.recouvrementapp.presentation.components.elements.MAlertDialog
import com.client.recouvrementapp.presentation.components.elements.TopBarSimple
import com.partners.hdfils_recolte.presentation.ui.components.Space
import com.qs.helper.printer.PrintService
import kotlinx.coroutines.launch
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset

@Composable
fun DetailRecouvrement(
    navC: NavHostController,
    onBackEvent: () -> Unit,
    viewModelGlobal: ApplicationViewModel? = null,
    recouvementId: Int? = 0
) {
    DetailRecouvrementBody(navC,onBackEvent,viewModelGlobal,recouvementId)
}

@Composable
fun DetailRecouvrementBody(
    navC: NavHostController? = null,
    onBackEvent: () -> Unit = {},
    vm: ApplicationViewModel? = null,
    recouvementId: Int? =0
) {
    val scope = rememberCoroutineScope()
    val detail = vm?.room?.recouvrement?.recouvrementDetail?.collectAsState()?.value
    val context = LocalContext.current
    val isActive = remember { mutableStateOf(true) }
    val btMap = BitmapFactory.decodeResource(context.resources,R.drawable.logo)
    var msg: String? by remember { mutableStateOf("") }
    var titleMsg by remember { mutableStateOf("Erreur") }
    var isShow by remember { mutableStateOf(false) }
    PrintService.pl.open(context)
    LaunchedEffect(Unit) {
        scope.launch {
            vm?.room?.recouvrement?.getDetailRecouvrement(recouvementId!!)
        }
        scope.launch {
            StoreData(context).getBluetoothPrinter.collect { cmd ->
                if(cmd != null){
                    PrintService.pl.connect(cmd)
                }
            }
        }
        PrintService.pl.open(context)
    }
    Scaffold(
        topBar = {
            TopBarSimple(
                isMain = false,
                title = "Details ($recouvementId)",
                onBackEvent = onBackEvent
            )
        }
    ) {
        Column(Modifier.padding(it).fillMaxSize().background(Color(0xFF25262C))) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(bottomEnd = 45.dp, bottomStart = 45.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFFFFFFF)
                )) {
                Column(Modifier.padding(18.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Client", color = Color.Black.copy(
                            alpha = 0.5f
                        ), fontSize = 16.sp)

                        Text("Date & heure", color = Color.Black.copy(
                            alpha = 0.5f
                        ), fontSize = 16.sp)
                    }
                    Spacer(Modifier.height(4.dp))
                    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                        Text("${detail?.member?.name}", color = Color.Black, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                        Column {
                            Text("${detail?.recouvrement?.createdOn}", color = Color.Black, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                            Text("${detail?.recouvrement?.time}", color = Color.Black, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    Spacer(Modifier.height(10.dp))

                    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                        Text("Type de transaction", color = Color.Black.copy(
                            alpha = 0.5f
                        ), fontSize = 16.sp)
                        Text("Communication", color = Color.Black.copy(
                            alpha = 0.5f
                        ), fontSize = 16.sp)
                    }

                    HorizontalDivider()
                    Spacer(modifier = Modifier.height(5.dp))
                    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                        Text("${detail?.recouvrement?.transactionType}", color = Color.Black, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                        Text("${detail?.recouvrement?.code}", color = Color.Black, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                        Text("Methode de paiement", color = Color.Black.copy(
                            alpha = 0.5f
                        ), fontSize = 16.sp)
                        Text("Montant", color = Color.Black.copy(
                            alpha = 0.5f
                        ), fontSize = 16.sp)
                    }
                    HorizontalDivider()
                    Spacer(modifier = Modifier.height(5.dp))
                    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                        Text("${detail?.paymentMethod?.name}", color = Color.Black, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                        Text("${detail?.currency?.symbole} ${detail?.recouvrement?.amount}", color = Color.Black, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                    }
                    if (detail?.period != null){
                        Spacer(modifier = Modifier.height(20.dp))
                        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                            Text("Periode", color = Color.Black.copy(
                                alpha = 0.5f
                            ), fontSize = 16.sp)
                        }
                        Spacer(modifier = Modifier.height(5.dp))
                        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                            Text(detail.period.name, color = Color.Black, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
            Space(y = 10)
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                Button(
                    onClick = {
                        isActive.value = false
                        scope.launch {
                            try {
                                val transactionType = when(detail?.recouvrement?.transactionType){
                                    "Subscription"-> "FJS Devise"
                                    "Prêt"-> "Micro-Pret"
                                    "Epargne" -> "Epargne"
                                    else-> detail?.recouvrement?.transactionType
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
                                PrintService.pl.printText("Projet${space(17 - "Projet".length)}: $transactionType\n")
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
                        isActive.value = true
                    },
                    enabled = isActive.value ,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor =  Color(0xFF15D77D),
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

@Preview(showBackground = true)
@Composable
fun DetailRecouvrementPreview(){
    DetailRecouvrementBody()
}