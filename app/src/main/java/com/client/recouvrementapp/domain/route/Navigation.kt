package com.client.recouvrementapp.domain.route

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.client.recouvrementapp.domain.viewmodel.ParentViewModel
import com.client.recouvrementapp.presentation.ui.pages.auth.AuthLogin
import com.client.recouvrementapp.presentation.ui.pages.config.printer.PrinterConfig
import com.client.recouvrementapp.presentation.ui.pages.home.Home
import com.client.recouvrementapp.presentation.ui.pages.payment.Paiement
import com.client.recouvrementapp.presentation.ui.pages.payment.PaimentPrinter
import com.client.recouvrementapp.presentation.ui.pages.recouvrement.DetailRecouvrement
import com.client.recouvrementapp.presentation.ui.pages.recouvrement.HistoryRecouvrement

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun Navigation(
    navC: NavHostController,
    innerPadding: PaddingValues,
    viewModelGlobal: ParentViewModel = viewModel(),
    isConnected: Boolean
){
    NavHost(navController = navC, startDestination = ScreenRoute.Login.name, route = "root") {
        composable(ScreenRoute.Login.name) {
            AuthLogin(navC,onBackEvent={},isConnected)
        }
        composable(ScreenRoute.Home.name) {
            Home(navC,isConnected)
        }
        composable(ScreenRoute.History.name) {
            HistoryRecouvrement(navC,onBackEvent={navC.popBackStack()},isConnected)
        }
        composable(ScreenRoute.Detail.name) {
            DetailRecouvrement(navC,onBackEvent={navC.popBackStack()},isConnected)
        }
        composable(ScreenRoute.Payment.name) {
            Paiement(navC,onBackEvent={navC.popBackStack()},isConnected)
        }
        composable(ScreenRoute.PrinterConfig.name) {
            PrinterConfig(navC, onBackEvent ={navC.popBackStack()}, viewModelGlobal.vmPrinterConfig,isConnected)
        }
        composable(ScreenRoute.PaymentPrinter.name) {
            PaimentPrinter(navC,onBackEvent={navC.popBackStack()},isConnected)
        }
    }
}