package com.client.recouvrementapp.presentation.ui.pages.home

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.client.recouvrementapp.data.local.Constantes.Companion.currencyRoute
import com.client.recouvrementapp.data.local.Constantes.Companion.paymentMethodRoute
import com.client.recouvrementapp.data.local.Constantes.Companion.periodOpenRoute
import com.client.recouvrementapp.data.remote.requestServer
import com.client.recouvrementapp.data.shared.StoreData
import com.client.recouvrementapp.domain.model.KeyValue
import com.client.recouvrementapp.domain.model.MenuItem
import com.client.recouvrementapp.domain.model.RecouvrementAmountOfDay
import com.client.recouvrementapp.domain.model.core.Currency
import com.client.recouvrementapp.domain.model.core.user.ProfilUser
import com.client.recouvrementapp.domain.model.room.CurrencyModel
import com.client.recouvrementapp.domain.model.room.PaymentMethodModel
import com.client.recouvrementapp.domain.model.room.PeriodModel
import com.client.recouvrementapp.domain.route.ScreenRoute
import com.client.recouvrementapp.domain.viewmodel.ApplicationViewModel
import com.client.recouvrementapp.presentation.components.elements.BoxMainRecouvrement
import com.client.recouvrementapp.presentation.components.elements.ImageIconButton
import com.client.recouvrementapp.presentation.components.elements.MAlertDialog
import com.client.recouvrementapp.presentation.components.elements.TopBarSimple
//import com.google.accompanist.permissions.ExperimentalPermissionsApi
//import com.google.accompanist.permissions.PermissionStatus
//import com.google.accompanist.permissions.rememberMultiplePermissionsState
//import com.google.accompanist.permissions.rememberPermissionState
//import com.google.accompanist.permissions.shouldShowRationale
import com.partners.hdfils_recolte.presentation.ui.components.Space
import io.ktor.client.call.body
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun Home(navC: NavHostController,viewModelGlobal: ApplicationViewModel?) {
    HomeBody(navC,viewModelGlobal)
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition",
    "ConfigurationScreenWidthHeight"
)
@Composable
fun HomeBody(navC: NavHostController? = null, vm: ApplicationViewModel? = null) {
    val configuration = LocalConfiguration.current
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val screenWidthDp = configuration.screenWidthDp.dp
    val screenHeightDp = configuration.screenHeightDp.dp
    val size = ((screenHeightDp.value.toInt() / 2))
    val sizeWidth = ((screenWidthDp.value.toInt() / 2))
    val isShow = remember { mutableStateOf(false) }
    var titleMsg = ""
    var msg = ""
    val user = remember { mutableStateListOf<ProfilUser?>() }
    val channelUser = Channel<ProfilUser>()
    var textPositive = "Valider"
    var textNegative = "Annuler"
    val listOfRecouvrementDay = listOf<RecouvrementAmountOfDay>(
        RecouvrementAmountOfDay(
            currency = Currency(1,"Dollar", "USD","$"),
            0.0
        ),
        RecouvrementAmountOfDay(
        currency = Currency(1,"Franc Congolais", "CDF","FC"),
            0.0
        ),
    )

    val onLogOutEvent :() -> Unit = {
        isShow.value = false
        navC?.navigate(route = ScreenRoute.Login.name){
            popUpTo(navC.graph.id){
                inclusive = true
            }
        }
    }
    val itemMenu = listOf<MenuItem>(
        MenuItem(1,"Config Printer", eventClick = {
            navC?.navigate(route = ScreenRoute.PrinterConfig.name)
        })
    )
    var onclick : () -> Unit = {}
    if (vm?.configuration?.isConnectNetwork == true){

        CoroutineScope(Dispatchers.IO).launch {
            val responseCurrency = requestServer(
                context = context,
                route = currencyRoute
            )
            Log.e("REQUEST->>>>>>>>>>>>>>>>","$responseCurrency")
            val status = responseCurrency.status.value
            when(status){
                in 200..299 ->{
                    //
                    val data : ArrayList<KeyValue> = responseCurrency.body()
                    Log.e("RESPONSE->>>>>>>>>>>>>>>>","$data")
                    scope.launch {
                        data.forEach {currency->
                            when(currency.id){
                                1->{
                                    val currencyModel = CurrencyModel(
                                        id = currency.id,
                                        name = currency.name,
                                        code = "USD",
                                        symbole = "$"
                                    )
                                    vm.room.currency.insert(currencyModel)
                                }
                                2->{
                                    val currencyModel = CurrencyModel(
                                        id = currency.id,
                                        name = currency.name,
                                        code = "CDF",
                                        symbole = "Fc"
                                    )
                                    vm.room.currency.insert(currencyModel)
                                }
                                else ->{
                                    val currencyModel = CurrencyModel(
                                        id = currency.id,
                                        name = currency.name
                                    )
                                    vm.room.currency.insert(currencyModel)
                                }
                            }


                        }
                    }
                }
                in 400..499->{

                }
            }
        }

        CoroutineScope(Dispatchers.IO).launch {
            val responseTypePaymentMethod = requestServer(
                context = context,
                route = paymentMethodRoute
            )
            val status = responseTypePaymentMethod.status.value
            when(status){
                in 200..299 ->{
                    val data : ArrayList<KeyValue> = responseTypePaymentMethod.body()
                    Log.e("RESPONSE->>>>>>>>>>>>>>>>","$data")
                    scope.launch {
                        data.forEach {typePaymentMethod->
                            val paymentModel = PaymentMethodModel(
                                id      = typePaymentMethod.id,
                                name    = typePaymentMethod.name
                            )
                            vm.room.paymentMethod.insert(paymentModel)
                        }
                    }
                }
                in 400..499->{

                }
            }
        }

        CoroutineScope(Dispatchers.IO).launch {
            val responsePeriod = requestServer(
                context = context,
                route = periodOpenRoute
            )
            val status = responsePeriod.status.value
            when(status){
                in 200..299 ->{
                    val data : ArrayList<KeyValue> = responsePeriod.body()
                    Log.e("RESPONSE->>>>>>>>>>>>>>>>","$data")

                    scope.launch {
                        data.forEach {period->
                            val periodModel = PeriodModel(
                                id      = period.id,
                                name    = period.name
                            )
                            vm.room.period.insert(periodModel)
                        }
                        Log.e("save periode>>>","$$$$$")
                    }
                }
                in 400..499->{

                }
            }
        }
    }


    Scaffold(
        topBar = {
            if (user.isNotEmpty()){
                TopBarSimple(
                    onclickLogOut = {
                        textPositive = "Oui"
                        textNegative = "Non"
                        msg = "Voulez-vous vraiment vous déconnectez ?"
                        titleMsg = "Information"
                        isShow.value = true
                        onclick = onLogOutEvent
                    },
                    menuItem = itemMenu,
                    username = user[0]?.profile?.username
                )
            }
        }
    ) {
        scope.launch {
            StoreData(context).getUser.collect { u ->
               // channelUser.send(u)
                user.add(u)
            }
//            val u = channelUser.receive()
//            user.add(u)
        }
        //CoroutineScope(Dispatchers.IO)
        scope.launch {
//            val responseCurrency = requestServer(
//                context = context,
//                route = currencyRoute
//            )
            Log.e("REQUEST->>>>>>>>>>>>>>>>","Elie Oko")
           // Toast.makeText(context,"test->${responseCurrency.status.value}", Toast.LENGTH_SHORT).show()
        }


//           // Toast.makeText(context,"test->${responseCurrency.status.value}", Toast.LENGTH_SHORT).show()
////            when(responseCurrency.status.value){
////                in 200..299 ->{
////                    //
////                    val res = responseCurrency.body<Array<KeyValue>>()
////                    Toast.makeText(context,res[0].name, Toast.LENGTH_SHORT).show()
////                }
////                in 400..499->{
////
////                }
////            }
//        }
        Column(Modifier.padding(it)) {
            Column(Modifier.padding(5.dp).fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Space(y = 150)
                ImageIconButton(onclick = {
                    navC?.navigate(route = ScreenRoute.Payment.name)
                })
                Space(y = 10)
                Text("Appuyez pour recouvrir sur +", color = Color.Black, fontSize = 18.sp)
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom,
                modifier = Modifier.fillMaxSize()
            ) {
                ConstraintLayout {
                    // Create references for the composables to constrain
                    val (card, buttonLink) = createRefs()
                    Card(
                        modifier = Modifier
                            .fillMaxWidth().height((size / 2).dp),
                        shape = RectangleShape,
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFF25262C)
                        )) {
                    }
                    BoxMainRecouvrement(
                        modifier = Modifier
                            .absoluteOffset(y = (-45).dp, x = 41.dp),
                        onclick = {
                            navC?.navigate(route = ScreenRoute.History.name)
                        },
                        width = sizeWidth + 100,
                        listRecouvrementAmountOfDay = listOfRecouvrementDay
                    )
                }
            }

        }
        if(isShow.value){
            MAlertDialog(
                textNegative = textNegative,
                textPositive = textPositive,
                dialogTitle = titleMsg,
                dialogText =  msg,
                onDismissRequest = {
                    isShow.value = false
                },
                onConfirmation = onclick
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Preview(showBackground = true)
@Composable
fun HomePreview(){
    HomeBody()
}