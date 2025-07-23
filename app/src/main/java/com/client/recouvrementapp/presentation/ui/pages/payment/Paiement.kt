package com.client.recouvrementapp.presentation.ui.pages.payment

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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
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
import com.client.recouvrementapp.data.local.Constantes.Companion.convertMillisToDate
import com.client.recouvrementapp.data.local.Constantes.Companion.cotisationOrdinaireId
import com.client.recouvrementapp.data.local.Constantes.Companion.cotisationSpesmId
import com.client.recouvrementapp.data.local.Constantes.Companion.subscriptionId
import com.client.recouvrementapp.domain.model.core.Currency
import com.client.recouvrementapp.domain.model.core.PaymentMethod
import com.client.recouvrementapp.domain.model.core.Period
import com.client.recouvrementapp.domain.model.core.TransactionType
import com.client.recouvrementapp.domain.route.ScreenRoute
import com.client.recouvrementapp.domain.viewmodel.ApplicationViewModel
import com.client.recouvrementapp.presentation.components.elements.InputFieldCompose
import com.client.recouvrementapp.presentation.components.elements.Label
import com.client.recouvrementapp.presentation.components.elements.SelectInputField
import com.partners.hdfils_recolte.presentation.ui.components.Space

@Composable
fun Paiement(
    navC: NavHostController,
    onBackEvent: () -> Unit = {},
    viewModelGlobal: ApplicationViewModel?
) {
    PaiementBody(navC,onBackEvent, viewModelGlobal)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaiementBody(
    navC: NavHostController? = null,
    onBackEvent: () -> Unit = {},
    viewModelGlobal: ApplicationViewModel? = null
) {
    var remarks by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var paymentDate by remember { mutableStateOf("") }
    var transactionTypeId by remember { mutableStateOf(10) }
    var period by remember { mutableStateOf(Period().asDataSelect()[0].name) }
    var code by remember { mutableStateOf("") }
    var device by remember { mutableStateOf(Currency().asDataSelect()[0].name) }
    var transactionType by remember { mutableStateOf(TransactionType().asDataSelect()[0].name) }
    var paymentMethod by remember { mutableStateOf(PaymentMethod().asDataSelect()[0].name) }
    var showDatePicker by remember { mutableStateOf(false) }
    var isActive by remember { mutableStateOf(true) }
    val datePickerState = rememberDatePickerState()
    var selectedDate = datePickerState.selectedDateMillis?.let {
        convertMillisToDate(it)
    } ?: convertMillisToDate(System.currentTimeMillis())
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
                    withStyle(style = SpanStyle(color = Color.Red,fontFamily = FontFamily.Serif,)) {
                        append("*")
                    }},fontSize = 18.sp)
                SelectInputField(
                    itemList = TransactionType().asDataSelect(),
                    textValueIn = transactionType,
                    onChangeText={
                        transactionTypeId = it.id
                    },
                    icon = R.drawable.type
                )
                Space(y = 10)
                Text(buildAnnotatedString {
                    append("Communication ")
                    withStyle(style = SpanStyle(color = Color.Red,fontFamily = FontFamily.Serif,)) {
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
                            withStyle(style = SpanStyle(color = Color.Red,fontFamily = FontFamily.Serif,)) {
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
                        Label("Devise", color = Color.Black, size = 18,)
                        SelectInputField(
                            modifier = Modifier.width(168.dp),
                            itemList = Currency().asDataSelect(),
                            textValueIn = device,
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
                                itemList = Period().asDataSelect(),
                                textValueIn = period,
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
                    itemList = PaymentMethod().asDataSelect(),
                    textValueIn = paymentMethod,
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
                        navC?.navigate(route = ScreenRoute.PaymentPrinter.name)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor =  Color(0xFF15D77D),
                        disabledContentColor = Color(0xFF080624),
                        disabledContainerColor = Color(0xFF080624)
                    )
                ) {
                    Text(text = if(isActive) "Sauvegarder" else "Chargement...", fontSize = 16.sp, color = Color.White)
                    Space(x = 10)
                    Icon(painterResource(R.drawable.save),null, modifier = Modifier.size(24.dp))
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