package com.client.recouvrementapp.presentation.ui.pages.recouvrement

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
fun DetailRecouvrement(navC: NavHostController, onBackEvent: () -> Unit, isConnected: Boolean) {
    DetailRecouvrementBody(navC,onBackEvent)
}

@Composable
fun DetailRecouvrementBody(navC: NavHostController? = null, onBackEvent: () -> Unit ={}) {
    Scaffold(
        topBar = {
            TopBarSimple(
                isMain = false,
                title = "Details",
                onBackEvent = onBackEvent
            )
        }
    ) {
        Column(Modifier.padding(it).fillMaxSize()) {

        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailRecouvrementPreview(){
    DetailRecouvrementBody()
}