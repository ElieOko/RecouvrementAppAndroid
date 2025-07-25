package com.client.recouvrementapp.presentation.ui.pages.payment

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearWavyProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.navigation.NavHostController
import com.client.recouvrementapp.presentation.components.elements.InputField
import com.client.recouvrementapp.presentation.components.elements.TopBarSimple
import com.client.recouvrementapp.R
import com.client.recouvrementapp.core.dateISOConvert
import com.client.recouvrementapp.data.local.Constantes.Companion.convertMillisToDate
import com.client.recouvrementapp.data.local.Constantes.Companion.cotisationOrdinaireId
import com.client.recouvrementapp.data.local.Constantes.Companion.cotisationSpesmId
import com.client.recouvrementapp.data.local.Constantes.Companion.recouvrementRoute
import com.client.recouvrementapp.data.local.Constantes.Companion.subscriptionId
import com.client.recouvrementapp.data.remote.requestServer
import com.client.recouvrementapp.data.shared.StoreData
import com.client.recouvrementapp.domain.model.ResponseHttpRequest
import com.client.recouvrementapp.domain.model.ResponseHttpRequestPayment
import com.client.recouvrementapp.domain.model.core.Recouvrement
import com.client.recouvrementapp.domain.model.core.TransactionType
import com.client.recouvrementapp.domain.model.room.CurrencyModel
import com.client.recouvrementapp.domain.model.room.PaymentMethodModel
import com.client.recouvrementapp.domain.model.room.PeriodModel
import com.client.recouvrementapp.domain.model.room.RecouvrementModel
import com.client.recouvrementapp.domain.model.room.UserModel
import com.client.recouvrementapp.domain.viewmodel.ApplicationViewModel
import com.client.recouvrementapp.presentation.components.elements.InputFieldCompose
import com.client.recouvrementapp.presentation.components.elements.Label
import com.client.recouvrementapp.presentation.components.elements.MAlertDialog
import com.client.recouvrementapp.presentation.components.elements.SelectInputField
import com.partners.hdfils_recolte.presentation.ui.components.Space
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpRequestTimeoutException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun Paiement(
    navC: NavHostController,
    onBackEvent: () -> Unit = {},
    viewModelGlobal: ApplicationViewModel?
) {
    PaiementBody(navC,onBackEvent, viewModelGlobal)
}

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun PaiementBody(
    navC: NavHostController? = null,
    onBackEvent: () -> Unit = {},
    vm: ApplicationViewModel? = null
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val channel = Channel<UserModel>()
    var remarks by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var transactionTypeId by remember { mutableIntStateOf(10) }
    var periodId by remember { mutableIntStateOf(0) }
    var currencyId by remember { mutableIntStateOf(0) }
    var paymentTypeId by remember { mutableIntStateOf(0) }
    var period: String by remember { mutableStateOf("") }
    var code by remember { mutableStateOf("") }
    var device: String by remember { mutableStateOf("") }
    var transactionType by remember { mutableStateOf(TransactionType().asDataSelect()[0].name) }
    var paymentMethod: String by remember { mutableStateOf("") }
    var showDatePicker by remember { mutableStateOf(false) }
    var isActive by remember { mutableStateOf(true) }
    val datePickerState = rememberDatePickerState()
    val listRecouvrementAll = vm?.room?.recouvrement?.listRecouvrementAll?.collectAsState()
    var msg: String? by remember { mutableStateOf("") }
    var titleMsg by remember { mutableStateOf("Erreur") }
    var isShow by remember { mutableStateOf(false) }
    var selectedDate = datePickerState.selectedDateMillis?.let {
        convertMillisToDate(it)
    } ?: convertMillisToDate(System.currentTimeMillis())

    val paymentMethodList   = vm?.room?.paymentMethod?.listPaymentMethod?.collectAsState()
    val periodeList         = vm?.room?.period?.listPeriod?.collectAsState()
    val currencyList        = vm?.room?.currency?.listCurrencies?.collectAsState()
    val stateSave           = vm?.room?.recouvrement?.stateSave?.collectAsState()

    CoroutineScope(Dispatchers.IO).launch {
        vm?.room?.paymentMethod?.getAllPaymentMethod()
        vm?.room?.currency?.getAllCurrencies()
        vm?.room?.period?.getAllPeriod()
        //vm?.room?.recouvrement?.getAllRecouvrement()
        StoreData(context).getUser.collect {
            channel.send(UserModel(it.profile.id,it.profile.displayName,it.profile.username))
        }
    }

    Scaffold(
        topBar = {
            TopBarSimple(
                isMain = false,
                title = "Paiement",
                onBackEvent = onBackEvent
            )
        }
    ) {
        Column(Modifier.padding(it).fillMaxSize()) {
            Column(Modifier.padding(10.dp)) {
//                Label("Type de transaction", color = Color.Black, size = 18 )
                Text(buildAnnotatedString {
                    append("Type de transaction ")
                    withStyle(style = SpanStyle(color = Color.Red,fontFamily = FontFamily.Serif)) {
                        append("*")
                    }},fontSize = 18.sp)
                SelectInputField(
                    itemList = TransactionType().asDataSelect(),
                    textValueIn = transactionType,
                    onChangeText={
                        transactionTypeId = it.id
                        transactionType = it.name
                    },
                    icon = R.drawable.type
                )
                Space(y = 10)
                Text(buildAnnotatedString {
                    append("Communication ")
                    withStyle(style = SpanStyle(color = Color.Red,fontFamily = FontFamily.Serif)) {
                        append("*")
                    }}, color = Color.Black, fontSize = 18.sp )
                InputField(
                    modifier = Modifier,
                    value = code,
                    placeholder = "ex.0593",
                    onValueChange = { dataValue ->
                        code = dataValue
                    },
                    icon = R.drawable.communication
                )
                Space(y = 10)
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Column {
                        Text(buildAnnotatedString {
                            append("Montant ")
                            withStyle(style = SpanStyle(color = Color.Red,fontFamily = FontFamily.Serif)) {
                                append("*")
                            }}, color = Color.Black, fontSize = 18.sp )
                        InputField(
                            modifier = Modifier.width(190.dp),
                            value = amount,
                            placeholder = "0.0",
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                            onValueChange = { dataValue ->
                                amount = dataValue
                            },
                            icon = R.drawable.money
                        )
                    }
                    Space(x=10)
                    Column {
                        Label("Devise", color = Color.Black, size = 18)
                        SelectInputField(
                            modifier = Modifier.width(168.dp),
                            itemList = CurrencyModel().asDataSelect(currencyList?.value),
                            textValueIn = device,
                            onChangeText={
                                currencyId = it.id
                            },
                            icon = R.drawable.money_
                        )
                    }

                }
                Space(y = 10)
                Row(Modifier.fillMaxWidth()) {
                    if (transactionTypeId == cotisationOrdinaireId || transactionTypeId == cotisationSpesmId)
                        Column {
                            Label("Période", color = Color.Black, size = 18 )
                            SelectInputField(
                                modifier = Modifier,
                                itemList = PeriodModel().asDataSelect(periodeList?.value),
                                textValueIn = period,
                                onChangeText={
                                    periodId = it.id
                                },
                                icon = R.drawable.period
                            )
                        }
                    if(transactionTypeId == subscriptionId)
                        Column {
                            Label("Date de paiement", color = Color.Black, size = 18 )
                            InputFieldCompose(
                                modifier = Modifier,
                                value = selectedDate,
                                onValueChange = { dataValue ->
                                    selectedDate = dataValue
                                },
                                onclickLastIcon = {
                                    showDatePicker = !showDatePicker
                                },
                                isSingle = false,
                                iconLast = R.drawable.date
                            )
                        }
                }

                Space(y = 10)
                Label("Methode de paiement", color = Color.Black, size = 18 )
                SelectInputField(
                    itemList = PaymentMethodModel().asDataSelect(paymentMethodList?.value),
                    textValueIn = paymentMethod,
                    onChangeText={
                        paymentTypeId = it.id
                    },
                    icon = R.drawable.card
                )
                Space(y = 10)
                Label("Remarque", color = Color.Black, size = 18 )
                InputField(
                    modifier = Modifier,
                    value = remarks,
                    onValueChange = { dataValue ->
                        remarks = dataValue
                    },
                    placeholder = "note",
                    isSingle = false,
                    icon = R.drawable.note
                )
                Space(y = 10)
                Space(y=20)

                Button(
                    onClick = {
                        if (vm?.configuration?.isConnectNetwork == true){
                            if (currencyId == 0){
                                isShow = true
                                msg = "devise n'est pas renseigné"
                                titleMsg = "Champs vide"
                            }
                            if (code.isEmpty()){
                                isShow = true
                                msg = "communication n'est pas renseigné"
                                titleMsg = "Champs vide"
                            }
                            if (amount.isEmpty()){
                                isShow = true
                                msg = "montant n'est pas renseigné"
                                titleMsg = "Champs vide"
                            }
                            if (transactionType.isEmpty()){
                                isShow = true
                                msg = "transactionType n'est pas renseigné"
                                titleMsg = "Champs vide"
                            }
                            if (transactionType.isNotEmpty() && amount.isNotEmpty() && code.isNotEmpty() && currencyId != 0){
                                when(transactionType){
                                    "Cotisation Ordinaire","Cotisation Ordinaire" ->{
                                        Log.e("1 transactionTypeId","$transactionTypeId")
                                        if (periodId == 0){
                                            isShow = true
                                            titleMsg = "Champs vide"
                                            msg = "Choisissez une periode"
                                        } else{
                                            val recouvrement = Recouvrement(
                                                transactionType = transactionType,
                                                communication = code,
                                                amount = amount.toInt(),
                                                currencyId = currencyId,
                                                paymentMethodId = paymentTypeId,
                                                periodId = periodId
                                            )
                                            scope.launch {
                                                isActive = false
                                                //delay(2000)
                                                Log.e("_ici transactionTypeId","$transactionTypeId")

                                                val userModel = channel.receive()
                                                try {
                                                    Log.e("_before request transactionTypeId","$transactionTypeId")
                                                    val response = requestServer(
                                                        context = context,
                                                        route = recouvrementRoute,
                                                        data = recouvrement,
                                                        method = "POST"
                                                    )
                                                    val status = response.status.value
                                                    //isActive = false
                                                    Log.e("_after transactionTypeId","$transactionTypeId")
                                                    when(status){
                                                        in 200..299-> {
                                                            titleMsg = "success"
                                                            msg = "Enregistrement réussie avec succès"
                                                            isShow = true
                                                            isActive = true
                                                            val data = response.body<ResponseHttpRequestPayment>()
                                                            val dateTimeModel = dateISOConvert(data.createdOn)
                                                            scope.launch{
                                                                vm.room.recouvrement.insert(RecouvrementModel(
                                                                    id = data.id,
                                                                    userId =  userModel.id,
                                                                    paymentMethodId = recouvrement.paymentMethodId,
                                                                    periodId = periodId,
                                                                    currencyId = recouvrement.currencyId,
                                                                    transactionType = transactionType,
                                                                    code = code,
                                                                    amount = amount.toInt(),
                                                                    remark = remarks,
                                                                    datePayment = "",
                                                                    createdOn = dateTimeModel.date,
                                                                    time = dateTimeModel.time
                                                                ))
                                                            }
                                                        }
                                                        in 400 .. 499 ->{
                                                            isShow = true
                                                            titleMsg = "Erreur"
                                                            isActive = true
                                                            val data = response.body<ResponseHttpRequest>()
                                                            msg = data.message
                                                        }
                                                        in 500 .. 599 ->{
                                                            titleMsg = "Erreur serveur"
                                                            msg = "Erreur serveur réssayer plus tard nous resolvons ce problème"
                                                            isActive = true
                                                            isShow = true
                                                        }
                                                    }
                                                } catch (e : HttpRequestTimeoutException){
                                                    msg         = "La requete a pris plus de temps que prevue cela est du a votre connection ressayer"
                                                    titleMsg    = "Request expire"
                                                    isShow      = true
                                                    isActive    = true
                                                    Log.e("Network expired request ->",e.message.toString())
                                                }
                                            }
                                        }
                                    }
                                    "Subscription"->{
                                        if (selectedDate.isEmpty()){
                                            isShow = true
                                            titleMsg = "Champs vide"
                                            msg = "Choisissez une date"
                                        } else{
                                            val recouvrement = Recouvrement(
                                                transactionType = transactionType,
                                                communication = code,
                                                amount = amount.toInt(),
                                                currencyId = currencyId,
                                                paymentMethodId = paymentTypeId
                                            )
                                            scope.launch {
                                                isActive = false
                                                val userModel = channel.receive()
                                                try {
                                                    val response = requestServer(
                                                        context = context,
                                                        route = recouvrementRoute,
                                                        data = recouvrement,
                                                        method = "POST"
                                                    )
                                                    isActive = true
                                                    val status = response.status.value
                                                    when(status){
                                                        in 200..299-> {
                                                            isShow = true
                                                            titleMsg = "success"
                                                            msg = "Enregistrement réussie avec succès"
                                                            val data = response.body<ResponseHttpRequestPayment>()
                                                            val dateTimeModel = dateISOConvert(data.createdOn)
                                                            scope.launch{
                                                                vm.room.recouvrement.insert(RecouvrementModel(
                                                                    id = data.id,
                                                                    userId =  userModel.id,
                                                                    paymentMethodId = recouvrement.paymentMethodId,
                                                                    periodId = null,
                                                                    currencyId = recouvrement.currencyId,
                                                                    transactionType = transactionType,
                                                                    code = code,
                                                                    amount = amount.toInt(),
                                                                    remark = remarks,
                                                                    datePayment = "",
                                                                    createdOn = dateTimeModel.date,
                                                                    time = dateTimeModel.time
                                                                ))
                                                            }
                                                        }
                                                        in 400 .. 499 ->{
                                                            val data = response.body<ResponseHttpRequest>()
                                                            titleMsg = "Erreur"
                                                            msg = data.message
                                                            isShow = true
                                                        }
                                                        in 500 .. 599 ->{
                                                            titleMsg = "Erreur serveur"
                                                            msg = "Erreur serveur réssayer plus tard nous resolvons ce problème"
                                                            isShow = true
                                                        }
                                                    }
                                                } catch (e : HttpRequestTimeoutException){
                                                    msg         = "La requete a pris plus de temps que prevue cela est du a votre connection ressayer"
                                                    titleMsg    = "Request expire"
                                                    isShow      = true
                                                    isActive    = true
                                                    Log.e("Network expired request ->",e.message.toString())
                                                }
                                            }
                                        }
                                    }
                                    else -> {
                                        val recouvrement = Recouvrement(
                                            transactionType = transactionType,
                                            communication = code,
                                            amount = amount.toInt(),
                                            currencyId = currencyId,
                                            paymentMethodId = paymentTypeId,
                                            periodId = periodId
                                        )
                                        scope.launch {
                                            isActive = false
                                            val userModel = channel.receive()
                                            try {
                                                val response = requestServer(
                                                    context = context,
                                                    route = recouvrementRoute,
                                                    data = recouvrement,
                                                    method = "POST"
                                                )
                                                isActive = true
                                                val status = response.status.value
                                                Log.e("_after transactionTypeId","$transactionTypeId")
                                                when(status){
                                                    in 200..299-> {
                                                        titleMsg = "success"
                                                        msg = "Enregistrement réussie avec succès"
                                                        isShow = true
                                                        val data = response.body<ResponseHttpRequestPayment>()
                                                        val dateTimeModel = dateISOConvert(data.createdOn)
                                                        scope.launch{
                                                            vm.room.recouvrement.insert(RecouvrementModel(
                                                                id = data.id,
                                                                userId =  userModel.id,
                                                                paymentMethodId = recouvrement.paymentMethodId,
                                                                periodId = null,
                                                                currencyId = recouvrement.currencyId,
                                                                transactionType = transactionType,
                                                                code = code,
                                                                amount = amount.toInt(),
                                                                remark = remarks,
                                                                datePayment = "",
                                                                createdOn = dateTimeModel.date,
                                                                time = dateTimeModel.time
                                                            ))
                                                        }
                                                    }
                                                    in 400 .. 499 ->{
                                                        val data = response.body<ResponseHttpRequest>()
                                                        isShow = true
                                                        titleMsg = "Erreur"
                                                        msg = data.message
                                                    }
                                                    in 500 .. 599 ->{
                                                        titleMsg = "Erreur serveur"
                                                        msg = "Erreur serveur réssayer plus tard nous resolvons ce problème"
                                                        isShow = true
                                                    }
                                                }
                                            } catch (e : HttpRequestTimeoutException){
                                                msg         = "La requete a pris plus de temps que prevue cela est du a votre connection ressayer"
                                                titleMsg    = "Request expire"
                                                isShow      = true
                                                isActive    = true
                                                Log.e("Network expired request ->",e.message.toString())
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        else{
                            msg = "Vous n'êtes pas connecté veuillez vérifier votre connexion !!!"
                            titleMsg = "Problème de connexion"
                            isShow = true
                        }

//                        navC?.navigate(route = ScreenRoute.PaymentPrinter.name)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = isActive,
                    colors = ButtonDefaults.buttonColors(
                        containerColor =  Color(0xFF15D77D),
                        disabledContentColor = Color(0xFF080624),
                        disabledContainerColor = Color(0xFF080624)
                    )
                ) {
                    if(isActive){
                        Text(text = "Enregistrer", fontSize = 16.sp, color = Color.White)
                        Space(x = 10)
                        Icon(painterResource(R.drawable.save),null, modifier = Modifier.size(24.dp))
                    }
                    else{
                    LinearWavyProgressIndicator(
                        color = Color.White,
                        trackColor = Color.White,)
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
                if (showDatePicker) {
                    Popup(
                        onDismissRequest = { showDatePicker = false },
                        alignment = Alignment.TopStart
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .offset(y = 64.dp)
                                .shadow(elevation = 4.dp)
                                .background(MaterialTheme.colorScheme.surface)
                                .padding(16.dp)
                        ) {
                            DatePicker(
                                state = datePickerState,
                                showModeToggle = false,
                                headline = {
                                    Button(onClick = {
                                        showDatePicker = false
                                    }, modifier = Modifier.padding(10.dp)) {
                                        Text("Valider")
                                    }
                                }
                            )

                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PaiementPreview(){
    PaiementBody()
}