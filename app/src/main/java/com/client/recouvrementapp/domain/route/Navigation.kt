package com.client.recouvrementapp.domain.route

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.client.recouvrementapp.presentation.ui.pages.auth.AuthLogin
import com.client.recouvrementapp.presentation.ui.pages.home.Home
import com.client.recouvrementapp.presentation.ui.pages.payment.Paiement
import com.client.recouvrementapp.presentation.ui.pages.recouvrement.DetailRecouvrement
import com.client.recouvrementapp.presentation.ui.pages.recouvrement.HistoryRecouvrement

@Composable
fun Navigation(navC: NavHostController, innerPadding: PaddingValues){
    NavHost(navController = navC, startDestination = ScreenRoute.Login.name, route = "root") {
        composable(ScreenRoute.Login.name) {
            AuthLogin(navC,onBackEvent={})
        }
        composable(ScreenRoute.Home.name) {
            Home(navC)
        }
        composable(ScreenRoute.History.name) {
            HistoryRecouvrement(navC,onBackEvent={navC.popBackStack()})
        }
        composable(ScreenRoute.Detail.name) {
            DetailRecouvrement(navC,onBackEvent={navC.popBackStack()})
        }
        composable(ScreenRoute.Payment.name) {
            Paiement(navC,onBackEvent={navC.popBackStack()})
        }
    }
}