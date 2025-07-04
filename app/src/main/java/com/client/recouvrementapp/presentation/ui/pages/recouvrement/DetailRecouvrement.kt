package com.client.recouvrementapp.presentation.ui.pages.recouvrement

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.client.recouvrementapp.presentation.components.elements.TopBarSimple

@Composable
fun DetailRecouvrement(){
    DetailRecouvrementBody()
}

@Composable
fun DetailRecouvrementBody(){
    Scaffold(
        topBar = {
            TopBarSimple(
                isMain = false,
                title = "Details"
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