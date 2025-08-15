package com.client.recouvrementapp.presentation.ui.pages.recouvrement

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.client.recouvrementapp.data.shared.StoreData
import com.client.recouvrementapp.domain.viewmodel.ApplicationViewModel
import com.client.recouvrementapp.presentation.components.elements.TopBarSimple
import com.partners.hdfils_recolte.presentation.ui.components.Space
import com.qs.helper.printer.PrintService
import kotlinx.coroutines.launch
import java.io.UnsupportedEncodingException

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
                title = "Details",
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
                        Text("ID", color = Color.Black.copy(
                            alpha = 0.5f
                        ), fontSize = 16.sp)

                        Text("Date & heure", color = Color.Black.copy(
                            alpha = 0.5f
                        ), fontSize = 16.sp)
                    }
                    Spacer(Modifier.height(4.dp))
                    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                        Text("$recouvementId", color = Color.Black, fontSize = 20.sp, fontWeight = FontWeight.Bold)
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
//                        PrintService.LanguageStr = "Japanese"
                        try {
                            val sendTitle = PrinterByteFeature.title.toByteArray(charset("GBK"))
                            val sendSubTitle = PrinterByteFeature.subTitle.toByteArray(charset("GBK"))
                            PrintService.pl.write(PrinterByteFeature.centerAlignemt)
                            PrintService.pl.write(PrinterByteFeature.boldOn)
                            PrintService.pl.write(sendTitle)
                            PrintService.pl.write(PrinterByteFeature.line(0x03))
                            PrintService.pl.write(PrinterByteFeature.boldOff)
                            PrintService.pl.write(sendSubTitle)
                            PrintService.pl.write(PrinterByteFeature.leftAlignemt)
                            PrintService.pl.printText("De :\t Admin")
                            PrintService.pl.write(PrinterByteFeature.line(0x01))
                            PrintService.pl.printText("Montant :\t Admin")

//
                        }
                        catch (e : UnsupportedEncodingException){
                            e.printStackTrace();
                            Log.e("**********",e.message.toString())
                            Toast.makeText(context,e.message.toString(), Toast.LENGTH_SHORT).show()
                        }

                        PrintService.pl.write(byteArrayOf(0x1d, 0x0c))
                        isActive.value = true

                        // }

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
    }
}

@Preview(showBackground = true)
@Composable
fun DetailRecouvrementPreview(){
    DetailRecouvrementBody()
}