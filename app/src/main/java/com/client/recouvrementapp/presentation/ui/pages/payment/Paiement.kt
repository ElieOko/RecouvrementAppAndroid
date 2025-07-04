package com.client.recouvrementapp.presentation.ui.pages.payment

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.client.recouvrementapp.presentation.components.elements.TopBarSimple

@Composable
fun Paiement(){
    PaiementBody()
}

@Composable
fun PaiementBody(){
    Scaffold(
        topBar = {
            TopBarSimple(
                isMain = false,
                title = "Paiement"
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