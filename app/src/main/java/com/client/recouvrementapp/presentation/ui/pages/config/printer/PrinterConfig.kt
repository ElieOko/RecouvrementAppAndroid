package com.client.recouvrementapp.presentation.ui.pages.config.printer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.client.recouvrementapp.presentation.components.elements.TopBarSimple

@Composable
fun PrinterConfig(navC: NavHostController, onBackEvent: () -> Unit ) {
    PrinterConfigBody(navC,onBackEvent)
}

@Composable
fun PrinterConfigBody(navC: NavHostController? = null, onBackEvent: () -> Unit = {}) {
    Scaffold(
        topBar = {
            TopBarSimple(
                title = "Configuration Printer",
                isMain = false,
                onBackEvent = onBackEvent
            )
        }
    ) {
        Column(Modifier.padding(it)) {

        }
    }
}

@Composable
@Preview(showBackground = true)
fun PrinterConfigPreview() {
    PrinterConfigBody()
}