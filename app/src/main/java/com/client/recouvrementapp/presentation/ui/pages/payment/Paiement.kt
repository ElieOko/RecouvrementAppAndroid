package com.client.recouvrementapp.presentation.ui.pages.payment

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.client.recouvrementapp.presentation.components.elements.TopBarSimple

@Composable
fun Paiement(navC: NavHostController, onBackEvent: () -> Unit={}) {
    PaiementBody(navC,onBackEvent)
}

@Composable
fun PaiementBody(navC: NavHostController? = null, onBackEvent: () -> Unit ={}) {
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

        }
    }
}

@Composable
@Preview(showBackground = true)
fun PaiementPreview(){
    PaiementBody()
}