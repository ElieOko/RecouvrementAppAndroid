package com.client.recouvrementapp.presentation.ui.pages.recouvrement

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.client.recouvrementapp.domain.viewmodel.ApplicationViewModel
import com.client.recouvrementapp.presentation.components.elements.TopBarSimple
import kotlinx.coroutines.launch

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

    LaunchedEffect(Unit) {
        scope.launch {
            vm?.room?.recouvrement?.getDetailRecouvrement(recouvementId!!)
        }
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
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailRecouvrementPreview(){
    DetailRecouvrementBody()
}