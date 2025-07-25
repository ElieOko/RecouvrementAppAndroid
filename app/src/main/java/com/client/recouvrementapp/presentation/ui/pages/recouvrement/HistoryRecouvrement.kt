package com.client.recouvrementapp.presentation.ui.pages.recouvrement

import android.annotation.SuppressLint
import android.util.Log
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.client.recouvrementapp.data.shared.StoreData
import com.client.recouvrementapp.domain.model.TypeRecouvrement
import com.client.recouvrementapp.domain.model.room.UserModel
import com.client.recouvrementapp.domain.route.ScreenRoute
import com.client.recouvrementapp.domain.viewmodel.ApplicationViewModel
import com.client.recouvrementapp.presentation.components.elements.BoxRecouvrementHistory
import com.client.recouvrementapp.presentation.components.elements.TopBarSimple
import com.client.recouvrementapp.presentation.ui.theme.wsColor
import com.partners.hdfils_recolte.presentation.ui.components.Space
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch

@Composable
fun HistoryRecouvrement(
    navC: NavHostController,
    onBackEvent: () -> Unit = {},
    viewModelGlobal: ApplicationViewModel?
) {
    HistoryRecouvrementBody(navC,onBackEvent,viewModelGlobal)
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun HistoryRecouvrementBody(
    navC: NavHostController? = null,
    onBackEvent: () -> Unit = {},
    vm: ApplicationViewModel? = null
) {
    val scrollHorizontal = rememberScrollState()
    val scrollVertical = rememberScrollState()
    val context = LocalContext.current
    val positionChannel = remember { mutableIntStateOf(1) }
    val scope = rememberCoroutineScope()
    val channel = Channel<UserModel>()
    val colorState = remember { mutableStateOf(true) }
    val activeChip = remember { mutableStateOf(true) }
    val typeRecouvrementList =  remember {
        mutableStateListOf(
            TypeRecouvrement(id = 1, title = "USD", true),
            TypeRecouvrement(id = 2, title = "CDF")
        )
    }

    val listRecouvrement = vm?.room?.recouvrement?.listRecouvrement?.collectAsState()
    val listRecouvrementAll = vm?.room?.recouvrement?.listRecouvrementAll?.collectAsState()?.value
    val userList = vm?.room?.user?.listUser?.collectAsState()
    LaunchedEffect(Unit) {
        scope.launch {
            Log.e("data launch ->", "$")
            StoreData(context).getUser.collect {
                vm?.room?.user?.getUser(it.profile.id)
                vm?.room?.recouvrement?.getAllRecouvrement(it.profile.id)
                Log.e("data here ->", "$listRecouvrementAll")
                Log.e("data user here ->", "${it.profile.id}")
                Log.e("data user room here ->", "${userList?.value?.size}")
                Log.e("data recouvrement by id room here ->", "$listRecouvrement")
            }
            listRecouvrementAll?.forEach { data->
                Log.e("data recouvrement room->", "$data")
            }
        }
        listRecouvrementAll?.forEach { data->
            Log.e("data recouvrement room->", "$data")
        }
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
        Log.e("data UI SIDE->", "$")
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
                Space(x = 10)
                val listUsd = listRecouvrement?.value?.filter { it.currency?.id == 1 }
                val listCdf = listRecouvrement?.value?.filter { it.currency?.id == 2 }
                when(positionChannel.intValue){
                    1->{
                        listUsd?.reversed()?.forEach {recouvrement->
                            BoxRecouvrementHistory(recouvrement, onclick = {
                                navC?.navigate(route = ScreenRoute.Detail.name +"/${recouvrement.recouvrement?.id}")
                            })
                        }
                    }
                    2->{
                        listCdf?.reversed()?.forEach {recouvrement->
                            BoxRecouvrementHistory(recouvrement, onclick = {
                                navC?.navigate(route = ScreenRoute.Detail.name +"/${recouvrement.recouvrement?.id}")
                            })
                        }
                    }
                    else -> {

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