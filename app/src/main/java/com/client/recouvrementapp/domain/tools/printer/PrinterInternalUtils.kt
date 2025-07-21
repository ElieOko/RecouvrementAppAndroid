//package com.soficom_transfert.tickets.tools.printer
//
//import android.pt.printer.Printer
//import com.soficom_transfert.tickets.app.SoficomTicket
//import com.soficom_transfert.tickets.data.helpers.ObjectBox
//import com.soficom_transfert.tickets.data.models.CurrencyModel
//import com.soficom_transfert.tickets.data.models.IntervalModel
//import com.soficom_transfert.tickets.data.models.TransferModel
//import com.soficom_transfert.tickets.tools.AppUtils
//import java.text.DecimalFormat
//import java.text.SimpleDateFormat
//import java.util.*
//
//object PrinterInternalUtils {
//    fun printSimple(model: TransferModel) {
//        val printer = Printer()
//        if(printer.openPrinter() == 0)
//        {
//            fun printOriginal()
//            {
//                printer.setBold(1)
//                printer.setAlignType(0)
//                printer.printString(AppUtils.centerPadding(model.orderNumber.toString(), "Original",48,' '))
//                printer.printString("Soficom Transfert")
//                printer.printString(SoficomTicket.getInstance().user.branch?.title)
//                when(model.transferTypeId?.toInt()){
//                    1 -> {
//                        printer.printString("Envoi")
//                    }
//                    2 -> {
//                        printer.printString("Retrait")
//                    }
//                    3 -> {
//                        printer.printString("Ria")
//                    }
//                    4 -> {
//                        printer.printString("Special")
//                    }
//                    5 -> {
//                        printer.printString("MoneyTrans")
//                    }
//                }
//                printer.setBold(0)
//
//                val intervalBox = ObjectBox.getBoxStore().boxFor(IntervalModel::class.java)
//                model.intervalId?.let {intervalId->
//                    val intervalModel = if(intervalId > 0) intervalBox.get(intervalId) else null
//                    intervalModel?.let {
//                        val decimalFormat = DecimalFormat.getNumberInstance(Locale.GERMAN) as DecimalFormat
//                        val min = decimalFormat.format(intervalModel.min)
//                        val max = if(intervalModel.max == 0) "+".padEnd(min.length,'+') else decimalFormat.format(intervalModel.max)
//                        printer.printString("$min - $max")
//                    }
//                }
//
//                if(model.senderPhone!=null){
//                    model.senderPhone?.let{senderPhone->
//                        printer.printString("Télé $senderPhone")
//                    }
//                } else {
//                    if(model.receiverPhone!=null){
//                        model.receiverPhone?.let{receiverPhone->
//                            printer.printString("Télé $receiverPhone")
//                        }
//                    }
//                }
//
//                if(model.amount!=null){
//                    model.amount?.let { amount->
//                        if(amount > 0){
//                            model.currencyId?.let {currencyId->
//                                val currencyBox = ObjectBox.getBoxStore().boxFor(CurrencyModel::class.java)
//                                val currencyModel = currencyBox.get(currencyId)
//                                printer.printString("${AppUtils.rightPadding("Montant",15,' ')}${String.format ("%.0f", model.amount)}${currencyModel.code}")
//                            }
//                        }
//                    }
//                } else {
//                    model.currencyId?.let {currencyId->
//                        val currencyBox = ObjectBox.getBoxStore().boxFor(CurrencyModel::class.java)
//                        val currencyModel = currencyBox.get(currencyId)
//                        printer.printString(currencyModel.title)
//                    }
//                }
//
//                model.dateCreated?.let {dateCreated->
//                    try{
//                        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault())
//                        val date = format.parse(dateCreated)
//                        if(date!=null){
//                            val dateFormat = SimpleDateFormat("dd-MM-yyyy - hh:mm", Locale.getDefault())
//                            printer.printString(dateFormat.format(date))
//                        }
//                    }catch (ex: Exception){
//
//                    }
//                }
//                printer.printString("_".padEnd(48,'_'))
//                for(x in 0 until 5) printer.printString(" ")
//            }
//
//            fun printClient()
//            {
//                printer.setBold(1)
//                printer.setAlignType(0)
//                printer.printString(AppUtils.centerPadding(model.orderNumber.toString(), "Client",48,' '))
//                printer.printString("Soficom Transfert")
//                printer.printString(SoficomTicket.getInstance().user.branch?.title)
//                when(model.transferTypeId?.toInt()){
//                    1 -> {
//                        printer.printString("Envoi")
//                    }
//                    2 -> {
//                        printer.printString("Retrait")
//                    }
//                    3 -> {
//                        printer.printString("Ria")
//                    }
//                    4 -> {
//                        printer.printString("Special")
//                    }
//                    5 -> {
//                        printer.printString("MoneyTrans")
//                    }
//                }
//                printer.setBold(0)
//
//                model.intervalId?.let { intervalId->
//                    val intervalBox = ObjectBox.getBoxStore().boxFor(IntervalModel::class.java)
//                    val intervalModel = if(intervalId > 0) intervalBox.get(intervalId) else null
//                    intervalModel?.let {
//                        val decimalFormat = DecimalFormat.getNumberInstance(Locale.GERMAN) as DecimalFormat
//                        val min = decimalFormat.format(intervalModel.min)
//                        val max = if(intervalModel.max == 0) "+".padEnd(min.length,'+') else decimalFormat.format(intervalModel.max)
//                        printer.printString("$min - $max")
//                    }
//                }
//
//                if(model.senderPhone!=null){
//                    model.senderPhone?.let{senderPhone->
//                        printer.printString("Télé $senderPhone")
//                    }
//                } else {
//                    if(model.receiverPhone!=null){
//                        model.receiverPhone?.let{receiverPhone->
//                            printer.printString("Télé $receiverPhone")
//                        }
//                    }
//                }
//
//                if(model.amount!=null){
//                    model.amount?.let { amount->
//                        if(amount > 0){
//                            model.currencyId?.let {currencyId->
//                                val currencyBox = ObjectBox.getBoxStore().boxFor(CurrencyModel::class.java)
//                                val currencyModel = currencyBox.get(currencyId)
//                                printer.printString("${AppUtils.rightPadding("Montant",15,' ')}${String.format ("%.0f", model.amount)}${currencyModel.code}")
//                            }
//                        }
//                    }
//                } else {
//                    model.currencyId?.let {currencyId->
//                        val currencyBox = ObjectBox.getBoxStore().boxFor(CurrencyModel::class.java)
//                        val currencyModel = currencyBox.get(currencyId)
//                        printer.printString(currencyModel.title)
//                    }
//                }
//
//                model.dateCreated?.let { dateCreated->
//                    try{
//                        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault())
//                        val date = format.parse(dateCreated)
//                        if(date!=null){
//                            val dateFormat = SimpleDateFormat("dd-MM-yyyy - hh:mm", Locale.getDefault())
//                            printer.printString(dateFormat.format(date))
//                        }
//                    }catch (ex: Exception){
//
//                    }
//                }
//
//                if(model.barcode !=null){
//                    printer.setAlignType(1)
//                    printer.printQR(model.barcode,17)
//                    printer.setAlignType(0)
//                }
//
//                printer.printString("Ceci est juste un ticket pour votre numero")
//                printer.printString("d'ordre au guichet et non un recu de paiement")
//                printer.printString("Toujours a votre ecoute, pour mieux vous servir")
//                printer.printString("en toute securite et rapidite")
//                printer.printString("Plus d'info +243 819 872 444, +243 998 724 444")
//                printer.printString("Email :dir@groupesoficom.com")
//                printer.printString("Web   :https://www.groupesoficom.com/")
//                printer.printString("_".padEnd(48,'_'))
//                for(x in 0 until 5) printer.printString(" ")
//            }
//            printOriginal()
//            printClient()
//            printer.closePrinter()
//        }
//    }
//
//
//    fun printNormal(model: TransferModel) {
//        val printer = Printer()
//        if(printer.openPrinter() == 0)
//        {
//            fun printOriginal()
//            {
//                printer.setBold(1)
//                printer.setAlignType(0)
//                printer.printString(AppUtils.centerPadding(model.orderNumber.toString(), "Original",48,' '))
//                printer.printString("Soficom Transfert")
//                printer.printString(SoficomTicket.getInstance().user.branch?.title)
//                when(model.transferTypeId?.toInt()){
//                    1 -> {
//                        printer.printString("Envoi")
//                    }
//                    2 -> {
//                        printer.printString("Retrait")
//                    }
//                    3 -> {
//                        printer.printString("Ria")
//                    }
//                    4 -> {
//                        printer.printString("Special")
//                    }
//                    5 -> {
//                        printer.printString("MoneyTrans")
//                    }
//                }
//                printer.setBold(0)
//
//                when(model.transferTypeId?.toInt()){
//                    1 -> {
//                        printer.printString("${AppUtils.rightPadding("Destination",15,' ')}${model.toBranchName}")
//                        printer.printString("${AppUtils.rightPadding("Expediteur",15,' ')}${model.senderName}")
//                        printer.printString("${AppUtils.rightPadding("Exp. Tel",15,' ')}${model.senderPhone}")
//                        printer.printString("${AppUtils.rightPadding("Adresse",15,' ')}${model.location}")
//                        printer.printString("${AppUtils.rightPadding("Beneficiaire",15,' ')}${model.receiverName}")
//                        printer.printString("${AppUtils.rightPadding("Ben. Tel",15,' ')}${model.receiverPhone}")
//                    }
//                    2,3,6 -> {
//                        printer.printString("${AppUtils.rightPadding("Provenance",15,' ')}${model.fromBranchName}")
//                        printer.printString("${AppUtils.rightPadding("Expediteur",15,' ')}${model.senderName}")
//                        printer.printString("${AppUtils.rightPadding("Beneficiaire",15,' ')}${model.receiverName}")
//                        printer.printString("${AppUtils.rightPadding("Ben. Tel",15,' ')}${model.receiverPhone}")
//                        printer.printString("${AppUtils.rightPadding("Adresse",15,' ')}${model.location}")
//                    }
//                    4 -> {
//                        printer.printString("${AppUtils.rightPadding("Nom Complet",15,' ')}${model.senderName}")
//                        printer.printString("${AppUtils.rightPadding("Telephone",15,' ')}${model.senderPhone}")
//                    }
//                }
//
//                model.amount?.let{amount->
//                    if(amount > 0){
//                        model.currencyId?.let{currencyId->
//                            val currencyBox = ObjectBox.getBoxStore().boxFor(CurrencyModel::class.java)
//                            val currencyModel = currencyBox.get(currencyId)
//                            printer.printString("${AppUtils.rightPadding("Montant",15,' ')}${String.format ("%.0f", model.amount)}${currencyModel.code}")
//                        }
//                    }
//                }
//
//                model.code?.let { code->
//                    if(code.trim().isNotEmpty()){
//                        printer.printString("${AppUtils.rightPadding("Code",15,' ')}${model.code}")
//                    }
//                }
//
//
//                if(model.transferTypeId?.toInt()==4){
//                    printer.printString("${AppUtils.rightPadding("Message",15,' ')}${model.note}")
//                } else {
//                    printer.printString("${AppUtils.rightPadding("Motif",15,' ')}${model.note}")
//                }
//
//                model.dateCreated?.let{dateCreated->
//                    try{
//                        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault())
//                        val date = format.parse(dateCreated)
//                        if(date!=null){
//                            val dateFormat = SimpleDateFormat("dd-MM-yyyy - hh:mm", Locale.getDefault())
//                            printer.printString(dateFormat.format(date))
//                        }
//                    }catch (ex: Exception){
//
//                    }
//                }
//
//                printer.printString("_".padEnd(48,'_'))
//                for(x in 0 until 5) printer.printString(" ")
//            }
//
//            fun printClient()
//            {
//                printer.setBold(1)
//                printer.setAlignType(0)
//                printer.printString(AppUtils.centerPadding(model.orderNumber.toString(), "Client",48,' '))
//                printer.printString("Soficom Transfert")
//                printer.printString(SoficomTicket.getInstance().user.branch?.title)
//                when(model.transferTypeId?.toInt()){
//                    1 -> {
//                        printer.printString("Envoi")
//                    }
//                    2 -> {
//                        printer.printString("Retrait")
//                    }
//                    3 -> {
//                        printer.printString("Ria")
//                    }
//                    4 -> {
//                        printer.printString("Special")
//                    }
//                    5 -> {
//                        printer.printString("MoneyTrans")
//                    }
//                }
//                printer.setBold(0)
//
//                when(model.transferTypeId?.toInt()){
//                    1 -> {
//                        printer.printString("${AppUtils.rightPadding("Destination",15,' ')}${model.toBranchName}")
//                        printer.printString("${AppUtils.rightPadding("Expediteur",15,' ')}${model.senderName}")
//                        printer.printString("${AppUtils.rightPadding("Exp. Tel",15,' ')}${model.senderPhone}")
//                        printer.printString("${AppUtils.rightPadding("Adresse",15,' ')}${model.location}")
//                        printer.printString("${AppUtils.rightPadding("Beneficiaire",15,' ')}${model.receiverName}")
//                        printer.printString("${AppUtils.rightPadding("Ben. Tel",15,' ')}${model.receiverPhone}")
//                    }
//                    2,3,6 -> {
//                        printer.printString("${AppUtils.rightPadding("Provenance",15,' ')}${model.fromBranchName}")
//                        printer.printString("${AppUtils.rightPadding("Expediteur",15,' ')}${model.senderName}")
//                        printer.printString("${AppUtils.rightPadding("Beneficiaire",15,' ')}${model.receiverName}")
//                        printer.printString("${AppUtils.rightPadding("Ben. Tel",15,' ')}${model.receiverPhone}")
//                        printer.printString("${AppUtils.rightPadding("Adresse",15,' ')}${model.location}")
//                    }
//                    4 -> {
//                        printer.printString("${AppUtils.rightPadding("Nom Complet",15,' ')}${model.senderName}")
//                        printer.printString("${AppUtils.rightPadding("Telephone",15,' ')}${model.senderPhone}")
//                    }
//                }
//
//                model.amount?.let { amount->
//                    if(amount > 0){
//                        model.currencyId?.let {currencyId->
//                            val currencyBox = ObjectBox.getBoxStore().boxFor(CurrencyModel::class.java)
//                            val currencyModel = currencyBox.get(currencyId)
//                            printer.printString("${AppUtils.rightPadding("Montant",15,' ')}${String.format ("%.0f", model.amount)}${currencyModel.code}")
//                        }
//                    }
//                }
//
//                model.code?.let{code->
//                    if(code.trim().isNotEmpty()){
//                        printer.printString("${AppUtils.rightPadding("Code",15,' ')}${model.code}")
//                    }
//                }
//
//                if(model.transferTypeId?.toInt()==4){
//                    printer.printString("${AppUtils.rightPadding("Message",15,' ')}${model.note}")
//                } else {
//                    printer.printString("${AppUtils.rightPadding("Motif",15,' ')}${model.note}")
//                }
//
//                model.dateCreated?.let {dateCreated->
//                    try{
//                        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault())
//                        val date = format.parse(dateCreated)
//                        if(date!=null){
//                            val dateFormat = SimpleDateFormat("dd-MM-yyyy - hh:mm", Locale.getDefault())
//                            printer.printString(dateFormat.format(date))
//                        }
//                    }catch (ex: Exception){
//
//                    }
//                }
//
//                if(model.barcode !=null){
//                    printer.setAlignType(1)
//                    printer.printQR(model.barcode,17)
//                    printer.setAlignType(0)
//                }
//
//                printer.printString("Ceci est juste un ticket pour votre numero")
//                printer.printString("d'ordre au guichet et non un recu de paiement")
//                printer.printString("Toujours a votre ecoute, pour mieux vous servir")
//                printer.printString("en toute securite et rapidite")
//                printer.printString("Plus d'info +243 819 872 444, +243 998 724 444")
//                printer.printString("Email :dir@groupesoficom.com")
//                printer.printString("Web   :https://www.groupesoficom.com/")
//                printer.printString("_".padEnd(48,'_'))
//                for(x in 0 until 5) printer.printString(" ")
//            }
//            printOriginal()
//            printClient()
//            printer.closePrinter()
//        }
//    }
//}