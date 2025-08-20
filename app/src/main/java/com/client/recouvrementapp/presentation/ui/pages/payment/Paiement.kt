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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import com.client.recouvrementapp.data.remote.KtorClientAndroid
import com.client.recouvrementapp.data.remote.TokenModel
import com.client.recouvrementapp.data.remote.requestServer
import com.client.recouvrementapp.data.shared.StoreData
import com.client.recouvrementapp.domain.model.ResponseHttpRequest
import com.client.recouvrementapp.domain.model.ResponseHttpRequestPayment
import com.client.recouvrementapp.domain.model.core.Recouvrement
import com.client.recouvrementapp.domain.model.core.TransactionType
import com.client.recouvrementapp.domain.model.room.CurrencyModel
import com.client.recouvrementapp.domain.model.room.MemberModel
import com.client.recouvrementapp.domain.model.room.PaymentMethodModel
import com.client.recouvrementapp.domain.model.room.PeriodModel
import com.client.recouvrementapp.domain.model.room.RecouvrementModel
import com.client.recouvrementapp.domain.model.room.UserModel
import com.client.recouvrementapp.domain.route.ScreenRoute
import com.client.recouvrementapp.domain.viewmodel.ApplicationViewModel
import com.client.recouvrementapp.presentation.components.elements.InputFieldCompose
import com.client.recouvrementapp.presentation.components.elements.Label
import com.client.recouvrementapp.presentation.components.elements.MAlertDialog
import com.client.recouvrementapp.presentation.components.elements.SelectInputField
import com.partners.hdfils_recolte.presentation.ui.components.Space
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.serialization.JsonConvertException
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
    val coroutineScope = rememberCoroutineScope()
    val scrollVertical = rememberScrollState()
    //latvar recouvrement : Recouvrement
    lateinit var recouvrement : Recouvrement
    val context = LocalContext.current
    val channel      = Channel<UserModel>()
    val channelToken = Channel<TokenModel>()
    val channelRecouvrement = Channel<Int?>()
    val ktorClientAndroid = KtorClientAndroid()
    var remarks by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var transactionTypeId by remember { mutableIntStateOf(10) }
    var periodId by remember { mutableIntStateOf(0) }
    var currencyId by remember { mutableIntStateOf(0) }
    var paymentTypeId by remember { mutableIntStateOf(0) }
    var period: String by remember { mutableStateOf("") }
    var code by remember { mutableStateOf("") }
    var device: String by remember { mutableStateOf("") }
    var transactionType by remember { mutableStateOf(TransactionType().asDataSelect()[0].description) }
    var transactionTypeValue by remember { mutableStateOf(TransactionType().asDataSelect()[0].name) }
    var paymentMethod: String by remember { mutableStateOf("") }
    var showDatePicker by remember { mutableStateOf(false) }
    var isActive by remember { mutableStateOf(true) }
    val datePickerState = rememberDatePickerState()
    var msg: String? by remember { mutableStateOf("") }
    var titleMsg by remember { mutableStateOf("Erreur") }
    var isShow by remember { mutableStateOf(false) }
    var stateRequest by remember { mutableStateOf(false) }
    var stateRequestPosition by remember { mutableIntStateOf(0) }
    var selectedDate = datePickerState.selectedDateMillis?.let {
        convertMillisToDate(it)
    } ?: convertMillisToDate(System.currentTimeMillis())

    val paymentMethodList   = vm?.room?.paymentMethod?.listPaymentMethod?.collectAsState()
    val periodeList         = vm?.room?.period?.listPeriod?.collectAsState()
    val currencyList        = vm?.room?.currency?.listCurrencies?.collectAsState()

    CoroutineScope(Dispatchers.IO).launch {
        vm?.room?.paymentMethod?.getAllPaymentMethod()
        vm?.room?.currency?.getAllCurrencies()
        vm?.room?.period?.getAllPeriod()
        StoreData(context).getUser.collect {
            channel.send(UserModel(it.profile.id,it.profile.displayName,it.profile.username))
            channelToken.send(TokenModel(it.access_token,it.token_type))
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
        Column(Modifier.padding(it).fillMaxSize().verticalScroll(scrollVertical)) {
            Column(Modifier.padding(10.dp)) {
                Text(buildAnnotatedString {
                    append("Type de transaction ")
                    withStyle(style = SpanStyle(color = Color.Red,fontFamily = FontFamily.Serif)) {
                        append("*")
                    }},fontSize = 18.sp)
                SelectInputField(
                    itemList = TransactionType().asDataSelect(),
                    textValueIn = transactionType,
                    onChangeText={item->
                        transactionTypeId = item.id
                        transactionType = item.description
                        //Toast.makeText(context,"${item.name}|${item.description}", Toast.LENGTH_LONG).show()
                        transactionTypeValue = item.name
                        //Toast.makeText(context,"0 $transactionTypeValue", Toast.LENGTH_LONG).show()
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
                            modifier = Modifier.width(187.dp),
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
                            modifier = Modifier.width(169.dp),
                            itemList = CurrencyModel().asDataSelect(currencyList?.value),
                            textValueIn = device,
                            onChangeText={item->
                                currencyId = item.id
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
                                onChangeText={item->
                                    periodId = item.id
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
                    onChangeText={item->
                        paymentTypeId = item.id
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
                        scope.launch {
                            try {
                                when(vm?.configuration?.isConnectNetwork){
                                    true->{
                                        if (currencyId == 0){
                                            isShow = true
                                            msg = "Devise n'est pas renseigné"
                                            titleMsg = "Champs vide"
                                        }
                                        if (code.isEmpty()){
                                            isShow = true
                                            msg = "Communication n'est pas renseigné"
                                            titleMsg = "Champs vide"
                                        }
                                        if (amount.isEmpty()){
                                            isShow = true
                                            msg = "Montant n'est pas renseigné"
                                            titleMsg = "Champs vide"
                                        }
                                        if (transactionType.isEmpty()){
                                            isShow = true
                                            msg = "TransactionType n'est pas renseigné"
                                            titleMsg = "Champs vide"
                                        }
                                        if (transactionTypeValue.isNotEmpty() && amount.isNotEmpty() && code.isNotEmpty() && currencyId != 0){
                                            coroutineScope.launch {
                                                isActive = false
                                                delay(3000)
                                                try {
                                                    Log.e("SCOPE PAYMENT before","Mbote")
                                                    scope.launch {
                                                        StoreData(context).getUser.collect {user->
                                                            channel.send(UserModel(user.profile.id,user.profile.displayName,user.profile.username))
                                                            channelToken.send(TokenModel(user.access_token,user.token_type))
                                                        }
                                                    }
                                                    val userModel = channel.receive()
                                                    val tokenModel = channelToken.receive()

                                                    when(transactionTypeValue){
                                                        "CotisationOrdinaire","CotisationSpesm" ->{
                                                            if (periodId == 0){
                                                                isShow = true
                                                                titleMsg = "Champs vide"
                                                                msg = "Choisissez une periode"
                                                            } else {
                                                                stateRequestPosition = 1
                                                                recouvrement =
                                                                    Recouvrement(
                                                                        transactionType = transactionTypeValue,
                                                                        communication = code,
                                                                        amount = amount.toInt(),
                                                                        currencyId = currencyId,
                                                                        paymentMethodId = paymentTypeId,
                                                                        periodId = periodId
                                                                    )
                                                            }
                                                        }
                                                        "Subscription"->{
                                                            recouvrement = Recouvrement(
                                                                transactionType = transactionTypeValue,
                                                                communication = code,
                                                                amount = amount.toInt(),
                                                                currencyId = currencyId,
                                                                paymentMethodId = paymentTypeId
                                                            )
                                                            stateRequestPosition = 0
                                                        }
                                                        else->{
                                                            recouvrement = Recouvrement(
                                                                transactionType = transactionTypeValue,
                                                                communication = code,
                                                                amount = amount.toInt(),
                                                                currencyId = currencyId,
                                                                paymentMethodId = paymentTypeId,
                                                                // periodId = periodId
                                                            )
                                                            stateRequestPosition = 2
                                                        }
                                                    }
                                                    if(stateRequest){
                                                        isShow = true
                                                    } else{
                                                        Log.e("SCOPE PAYMENT","Here")
                                                        val response = ktorClientAndroid.postData(
                                                            token = tokenModel.token,
                                                            route = recouvrementRoute,
                                                            data = recouvrement
                                                        )
                                                        Log.e("SCOPE PAYMENT END","Hola")
                                                        isActive = true
                                                        val status = response.status.value
                                                        when (status) {
                                                            in 200..299 -> {
                                                                isShow = true
                                                                titleMsg = "success"
                                                                msg =
                                                                    "Enregistrement réussie avec succès"
                                                                val data =
                                                                    response.body<ResponseHttpRequestPayment>()
                                                                val dateTimeModel = dateISOConvert(data.createdOn)
                                                                scope.launch {
                                                                    vm.room.member.insert(
                                                                        MemberModel(data.member.id,data.member.name)
                                                                    )
                                                                }
                                                                scope.launch {
                                                                    vm.room.recouvrement.insert(
                                                                        RecouvrementModel(
                                                                            id = data.id,
                                                                            userId = userModel.id,
                                                                            paymentMethodId = recouvrement.paymentMethodId,
                                                                            periodId = null,
                                                                            currencyId = recouvrement.currencyId,
                                                                            memberId = data.member.id,
                                                                            transactionType = transactionType,
                                                                            code = code,
                                                                            amount = amount.toInt(),
                                                                            remark = remarks,
                                                                            datePayment = "",
                                                                            createdOn = dateTimeModel.date,
                                                                            time = dateTimeModel.time
                                                                        )
                                                                    )
                                                                    channelRecouvrement.send(data.id)
                                                                }
                                                            }

                                                            in 400..499 -> {
                                                                val data = response.body<ResponseHttpRequest>()
                                                                titleMsg = "Erreur"
                                                                msg = data.message
                                                                isShow = true
                                                            }

                                                            in 500..599 -> {
                                                                titleMsg = "Erreur serveur"
                                                                msg =
                                                                    "Erreur serveur réssayer plus tard nous resolvons ce problème"
                                                                isShow = true
                                                            }
                                                        }
                                                    }
                                                    // }
                                                }
                                                catch (e : HttpRequestTimeoutException){
                                                    msg = "La requete a pris plus de temps que prevue cela est du a votre connection ressayer"
                                                    titleMsg = "Request expire"
                                                    isShow = true
                                                    isActive = true
                                                    Log.e(
                                                        "Network expired request ->",
                                                        e.message.toString()
                                                    )
                                                }
                                                catch(e : JsonConvertException){
                                                    msg = e.message.toString()
                                                    titleMsg = "Erreur sur la serialisation"
                                                    isShow = true
                                                    isActive = true
                                                }
                                                catch(e: Exception) {
                                                    msg = e.message.toString()
                                                    titleMsg = "Exception"
                                                    isShow = true
                                                    isActive = true
                                                    println("Erreur inconnue : ${e::class.simpleName} - ${e.message}")
                                                }
                                            }
                                            /*when(transactionTypeValue){
                                                /*"CotisationOrdinaire","CotisationSpesm" ->{
                                                    if (periodId == 0){
                                                        isShow = true
                                                        titleMsg = "Champs vide"
                                                        msg = "Choisissez une periode"
                                                    } else{
                                                        scope.launch {
                                                            isActive = false
                                                            delay(2000)
                                                            try {
                                                                scope.launch {
                                                                    StoreData(context).getUser.collect {user->
                                                                        channel.send(UserModel(user.profile.id,user.profile.displayName,user.profile.username))
                                                                        channelToken.send(TokenModel(user.access_token,user.token_type))
                                                                    }
                                                                }
                                                                val userModel = channel.receive()
                                                                val tokenModel = channelToken.receive()
                                                                val recouvrement = Recouvrement(
                                                                    transactionType = transactionTypeValue,
                                                                    communication = code,
                                                                    amount = amount.toInt(),
                                                                    currencyId = currencyId,
                                                                    paymentMethodId = paymentTypeId,
                                                                    periodId = periodId
                                                                )
                                                                val response = ktorClientAndroid.postData(
                                                                    token = tokenModel.token,
                                                                    route = recouvrementRoute,
                                                                    data = recouvrement
                                                                )
                                                                isActive = true
                                                                val status = response.status.value
                                                                when (status) {
                                                                    in 200..299 -> {
                                                                        titleMsg = "success"
                                                                        msg =
                                                                            "Enregistrement réussie avec succès"
                                                                        isShow = true
                                                                        val data =
                                                                            response.body<ResponseHttpRequestPayment>()
                                                                        val dateTimeModel =
                                                                            dateISOConvert(data.createdOn)
                                                                        scope.launch {
                                                                            vm.room.member.insert(
                                                                                MemberModel(data.member.id,data.member.name)
                                                                            )
                                                                        }
                                                                        scope.launch {
                                                                            vm.room.recouvrement.insert(
                                                                                RecouvrementModel(
                                                                                    id = data.id,
                                                                                    userId = userModel.id,
                                                                                    paymentMethodId = recouvrement.paymentMethodId,
                                                                                    periodId = periodId,
                                                                                    currencyId = recouvrement.currencyId,
                                                                                    memberId = data.member.id,
                                                                                    transactionType = transactionType,
                                                                                    code = code,
                                                                                    amount = amount.toInt(),
                                                                                    remark = remarks,
                                                                                    datePayment = "",
                                                                                    createdOn = dateTimeModel.date,
                                                                                    time = dateTimeModel.time
                                                                                )
                                                                            )
                                                                            channelRecouvrement.send(data.id)
                                                                        }
                                                                    }

                                                                    in 400..499 -> {
                                                                        isShow = true
                                                                        titleMsg = "Erreur"
                                                                        val data = response.body<Any>()
                                                                        msg = "$data"
                                                                    }

                                                                    in 500..599 -> {
                                                                        titleMsg = "Erreur serveur"
                                                                        msg =
                                                                            "Erreur serveur réssayer plus tard nous resolvons ce problème"
                                                                        isShow = true
                                                                    }
                                                                }

                                                            } catch (e: HttpRequestTimeoutException) {
                                                                msg = "La requete a pris plus de temps que prevue cela est du a votre connection ressayer"
                                                                titleMsg = "Request expire"
                                                                isShow = true
                                                                isActive = true
                                                                Log.e(
                                                                    "Network expired request ->",
                                                                    e.message.toString()
                                                                )
                                                            }
                                                            catch(e : JsonConvertException){
                                                                msg = e.message.toString()
                                                                titleMsg = "Erreur sur la serialisation"
                                                                isShow = true
                                                                isActive = true
                                                            }
                                                            catch(e : Exception){
                                                                msg = e.message.toString()
                                                                titleMsg = "Erreur"
                                                                isShow = true
                                                                isActive = true
                                                            }
                                                        }
                                                    }
                                                }*/
                                                "Subscription"->{}
                                                /*else -> {
                                                    scope.launch {
                                                        isActive = false
                                                        delay(2000)
                                                        try {
                                                            scope.launch {
                                                                StoreData(context).getUser.collect {user->
                                                                    channel.send(UserModel(user.profile.id,user.profile.displayName,user.profile.username))
                                                                    channelToken.send(TokenModel(user.access_token,user.token_type))
                                                                }
                                                            }
                                                            val userModel = channel.receive()
                                                            val tokenModel = channelToken.receive()
                                                            val recouvrement = Recouvrement(
                                                                transactionType = transactionTypeValue,
                                                                communication = code,
                                                                amount = amount.toInt(),
                                                                currencyId = currencyId,
                                                                paymentMethodId = paymentTypeId,
                                                                // periodId = periodId
                                                            )
                                                            val response = ktorClientAndroid.postData(
                                                                token = tokenModel.token,
                                                                route = recouvrementRoute,
                                                                data = recouvrement
                                                            )
                                                            isActive = true
                                                            val status = response.status.value
                                                            when (status) {
                                                                in 200..299 -> {
                                                                    titleMsg = "success"
                                                                    msg =
                                                                        "Enregistrement réussie avec succès"
                                                                    isShow = true
                                                                    val data = response.body<ResponseHttpRequestPayment>()
                                                                    val dateTimeModel = dateISOConvert(data.createdOn)
                                                                    scope.launch {
                                                                        vm.room.member.insert(
                                                                            MemberModel(data.member.id,data.member.name)
                                                                        )
                                                                    }
                                                                    scope.launch {
                                                                        vm.room.recouvrement.insert(
                                                                            RecouvrementModel(
                                                                                id = data.id,
                                                                                userId = userModel.id,
                                                                                paymentMethodId = recouvrement.paymentMethodId,
                                                                                periodId = null,
                                                                                currencyId = recouvrement.currencyId,
                                                                                memberId = data.member.id,
                                                                                transactionType = transactionType,
                                                                                code = code,
                                                                                amount = amount.toInt(),
                                                                                remark = remarks,
                                                                                datePayment = "",
                                                                                createdOn = dateTimeModel.date,
                                                                                time = dateTimeModel.time
                                                                            )
                                                                        )
                                                                        channelRecouvrement.send(data.id)
                                                                    }
                                                                }

                                                                in 400..499 -> {
                                                                    val data = response.body<Any>()
                                                                    isShow = true
                                                                    titleMsg = "Erreur"
                                                                    msg = "$data"
                                                                }

                                                                in 500..599 -> {
                                                                    titleMsg = "Erreur serveur"
                                                                    msg = "Erreur serveur réssayer plus tard nous resolvons ce problème"
                                                                    isShow = true
                                                                }
                                                            }
                                                        }
                                                        catch (e: HttpRequestTimeoutException) {
                                                            msg =
                                                                "La requete a pris plus de temps que prevue cela est du a votre connection ressayer"
                                                            titleMsg = "Request expire"
                                                            isShow = true
                                                            isActive = true
                                                            Log.e(
                                                                "Network expired request ->",
                                                                e.message.toString()
                                                            )
                                                        }
                                                        catch(e : JsonConvertException){
                                                            msg = e.message.toString()
                                                            titleMsg = "Erreur sur la serialisation"
                                                            isShow = true
                                                            isActive = true
                                                        }
                                                        catch(e : Exception){
                                                            msg = e.message.toString()
                                                            titleMsg = "Erreur sur la serialisation"
                                                            isShow = true
                                                            isActive = true
                                                        }
                                                    }
                                                }*/
                                            }*/
                                            scope.launch {
                                                val recouvementId = channelRecouvrement.receive()
                                                if(recouvementId != null){
                                                    navC?.navigate(route = ScreenRoute.PaymentPrinter.name +"/${recouvementId}")
                                                }
                                            }
                                        }
                                    }
                                    else->{
                                        msg = "Vous n'êtes pas connecté veuillez vérifier votre connexion !!!"
                                        titleMsg = "Problème de connexion"
                                        isShow = true
                                    }
                                }
                            }
                            catch (e : Exception){
                                Log.e("error ->",e.message.toString())
                                titleMsg = "Exception"
                                msg = e.message
                                isShow = true
                            }
                        }
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