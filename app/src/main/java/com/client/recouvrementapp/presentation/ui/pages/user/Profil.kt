package com.client.recouvrementapp.presentation.ui.pages.user

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.client.recouvrementapp.data.shared.StoreData
import com.client.recouvrementapp.domain.model.core.user.ProfilUser
import com.client.recouvrementapp.domain.viewmodel.ApplicationViewModel
import com.client.recouvrementapp.presentation.components.elements.TopBarSimple
import com.client.recouvrementapp.presentation.ui.theme.bagdeColor
import com.partners.hdfils_recolte.presentation.ui.components.Space
import kotlinx.coroutines.launch

@Composable
fun Profil(
    navC: NavHostController?,
    onBackEvent: () -> Unit,
    viewModelGlobal: ApplicationViewModel?
) {
    ProfilBody(navC,onBackEvent,viewModelGlobal)
}

@Composable
@Preview(showBackground = true)
fun ProfilBody(
    navC: NavHostController? = null,
    onBackEvent: () -> Unit = {} ,
    viewModelGlobal: ApplicationViewModel? = null
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val user = remember { mutableStateListOf<ProfilUser?>() }
    LaunchedEffect(Unit){
        scope.launch {
            StoreData(context).getUser.collect { u ->
                user.add(u)}
        }
    }
    Scaffold(
        topBar = {
            TopBarSimple(
                isMain = false,
                title = "Profil",
                onBackEvent = onBackEvent
            )
        }
    ) {
        Column(Modifier
            .fillMaxSize()
            .padding(it)
            .background(Color(0xFF25262C))
            .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFFFFFFF)
                )
            ) {
                if(user.isNotEmpty()){
                    Column(
                        modifier = Modifier.padding(20.dp).fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        IconButton(
                            onClick = {},
                            colors = IconButtonDefaults.iconButtonColors(containerColor = bagdeColor),
                            modifier = Modifier.size(82.dp).border(width = 10.dp,
                                color = Color(0xF788F18A),
                                shape = CircleShape
                            )
                        ) {
                            user[0]?.profile?.username[0]?.uppercaseChar().toString()
                                .let { text -> Text(text, color = Color.White, fontSize = 24.sp) }
                        }
                        Space(y = 24)
                        Text(user[0]?.profile?.displayName.toString(), color = Color.Black, fontSize = 24.sp)
                    }
                }

            }
        }
    }
}