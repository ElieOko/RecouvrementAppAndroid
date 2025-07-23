package com.client.recouvrementapp.presentation.ui.pages.recouvrement

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.client.recouvrementapp.domain.model.TypeRecouvrement
import com.client.recouvrementapp.domain.viewmodel.ApplicationViewModel
import com.client.recouvrementapp.presentation.components.elements.TopBarSimple
import com.client.recouvrementapp.presentation.ui.theme.wsColor

@Composable
fun HistoryRecouvrement(
    navC: NavHostController,
    onBackEvent: () -> Unit = {},
    viewModelGlobal: ApplicationViewModel?
) {
    HistoryRecouvrementBody(navC,onBackEvent,viewModelGlobal)
}

@Composable
fun HistoryRecouvrementBody(
    navC: NavHostController? = null,
    onBackEvent: () -> Unit = {},
    viewModelGlobal: ApplicationViewModel? = null
) {
    val scrollHorizontal = rememberScrollState()
    val scrollVertical = rememberScrollState()
    val positionChannel = remember { mutableIntStateOf(1) }
    val colorState = remember { mutableStateOf(true) }
    val activeChip = remember { mutableStateOf(true) }
    val typeRecouvrementList =  remember {
        mutableStateListOf(
            TypeRecouvrement(id = 1, title = "Tous", true),
            TypeRecouvrement(id = 2, title = "Dettes ")
        )
    }
    Scaffold(
        topBar = {
            TopBarSimple(
                title = "Historiques",
                isMain = false,
                onBackEvent = onBackEvent
            )
        }
    ) {
        Column(Modifier.verticalScroll(scrollVertical)) {
            Column(Modifier.padding(it).fillMaxSize()) {
                Row(modifier = Modifier.horizontalScroll(scrollHorizontal).fillMaxWidth()) {
                    Spacer(Modifier.width(15.dp))
                    typeRecouvrementList.forEachIndexed {indice,it->
                        colorState.value = it.isActive
                        SuggestionChip(
                            enabled = activeChip.value,
                            shape = RoundedCornerShape(35.dp),
                            label = {
                                Text(
                                    when(it.id){
                                        1-> it.title
                                        2-> it.title
                                        3 -> it.title
                                        4-> it.title
                                        else -> it.title
                                    }
                                )
                            },
                            onClick = {
                                typeRecouvrementList.forEachIndexed { i, it ->
                                    typeRecouvrementList[i] = it.copy(isActive = i == indice)
                                }
                                positionChannel.intValue = it.id
                            },
                            colors =  if(it.isActive) SuggestionChipDefaults.suggestionChipColors(wsColor, labelColor = if (!activeChip.value) wsColor else Color.Unspecified, disabledContainerColor = if (!activeChip.value) wsColor else Color.Unspecified) else SuggestionChipDefaults.suggestionChipColors()
                        )
                        Spacer(Modifier.width(10.dp))
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HistoryRecouvrementPreview(){
    HistoryRecouvrementBody()
}