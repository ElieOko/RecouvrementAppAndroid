package com.client.recouvrementapp.domain.viewmodel

import androidx.lifecycle.ViewModel
import com.client.recouvrementapp.domain.viewmodel.config.PrinterConfigViewModel
import com.client.recouvrementapp.domain.viewmodel.room.CurrencyViewModel
import com.client.recouvrementapp.domain.viewmodel.room.PaymentMethodViewModel
import com.client.recouvrementapp.domain.viewmodel.room.PeriodViewModel
import com.client.recouvrementapp.domain.viewmodel.room.RecouvrementViewModel
import com.client.recouvrementapp.domain.viewmodel.room.TransactionTypeViewModel
import com.client.recouvrementapp.domain.viewmodel.room.UserViewModel


class InstanceRoomViewModel(
     periodViewModel             : PeriodViewModel,
     transactionTypeViewModel    : TransactionTypeViewModel,
     currencyViewModel           : CurrencyViewModel,
     userViewModel               : UserViewModel,
     recouvrementViewModel       : RecouvrementViewModel,
     paymentMethodViewModel      : PaymentMethodViewModel
) : ViewModel(){
    var period          = periodViewModel
    var transactionType = transactionTypeViewModel
    var currency        = currencyViewModel
    var user            = userViewModel
    var recouvrement    = recouvrementViewModel
    var paymentMethod   = paymentMethodViewModel
}

class ConfigurationViewModel(
    printerViewModel: PrinterConfigViewModel,
    ktorClient : KtorViewModel,
    isConnectNetworkState: Boolean
) : ViewModel(){
    var printer = printerViewModel
    var isConnectNetwork = isConnectNetworkState
    var ktor = ktorClient
}

class ApplicationViewModel(
    roomVm : InstanceRoomViewModel,
    configurationVm : ConfigurationViewModel
): ViewModel(){
    val room            = roomVm
    val configuration   = configurationVm
}
