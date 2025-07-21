package com.client.recouvrementapp.presentation.ui.pages.home

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.client.recouvrementapp.domain.model.MenuItem
import com.client.recouvrementapp.domain.model.RecouvrementAmountOfDay
import com.client.recouvrementapp.domain.model.core.Currency
import com.client.recouvrementapp.domain.route.ScreenRoute
import com.client.recouvrementapp.presentation.components.elements.BoxMainRecouvrement
import com.client.recouvrementapp.presentation.components.elements.ImageIconButton
import com.client.recouvrementapp.presentation.components.elements.MAlertDialog
import com.client.recouvrementapp.presentation.components.elements.TopBarSimple
//import com.google.accompanist.permissions.ExperimentalPermissionsApi
//import com.google.accompanist.permissions.PermissionStatus
//import com.google.accompanist.permissions.rememberMultiplePermissionsState
//import com.google.accompanist.permissions.rememberPermissionState
//import com.google.accompanist.permissions.shouldShowRationale
import com.partners.hdfils_recolte.presentation.ui.components.Space

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun Home(navC: NavHostController, isConnected: Boolean) {
    HomeBody(navC)
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeBody(navC: NavHostController? = null) {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp.dp
    val screenHeightDp = configuration.screenHeightDp.dp
    val size = ((screenHeightDp.value.toInt() / 2))
    val sizeWidth = ((screenWidthDp.value.toInt() / 2))
    val isShow = remember { mutableStateOf(false) }
    var titleMsg = ""
    var msg = ""
    var textPositive = "Valider"
    var textNegative = "Annuler"
    val listOfRecouvrementDay = listOf<RecouvrementAmountOfDay>(
        RecouvrementAmountOfDay(
            currency = Currency(1,"Dollar", "USD","$"),
            0.0
        ),
        RecouvrementAmountOfDay(
        currency = Currency(1,"Franc Congolais", "CDF","FC"),
            0.0
        ),
    )


    val onLogOutEvent :() -> Unit = {
        isShow.value = false
        navC?.navigate(route = ScreenRoute.Login.name){
            popUpTo(navC.graph.id){
                inclusive = true
            }
        }
    }
    val itemMenu = listOf<MenuItem>(
        MenuItem(1,"Config Printer", eventClick = {
            navC?.navigate(route = ScreenRoute.PrinterConfig.name)
        })
    )
    var onclick : () -> Unit = {}

//    val permissionsState = rememberMultiplePermissionsState(
//        permissions = permissionsToRequest
//    )
//    var hasRequestedPermissions by rememberSaveable { mutableStateOf(false) }
//    var permissionRequestCompleted by rememberSaveable { mutableStateOf(false) }
//    var statePermission by remember { mutableStateOf(false) }
//
//    LaunchedEffect(hasRequestedPermissions) {
//        if (hasRequestedPermissions) {
//            permissionRequestCompleted = permissionsState.revokedPermissions.isNotEmpty()
//        }
//    }
    Scaffold(
        topBar = {
            TopBarSimple(
                onclickLogOut = {
                    textPositive = "Oui"
                    textNegative = "Non"
                    msg = "Voulez-vous vraiment vous déconnectez ?"
                    titleMsg = "Information"
                    isShow.value = true
                    onclick = onLogOutEvent
                },
                menuItem = itemMenu
            )
        }
    ) {
        Column(Modifier.padding(it)) {
//            when {
//                permissionsState.allPermissionsGranted -> {
//                    statePermission = false
//                }
//                permissionsState.shouldShowRationale->{
//                    msg = "Permissions denied. Please enable them in app settings to proceed."
//                    titleMsg = "Permission"
//                    onclick = {
//                        textPositive = "Autoriser"
//                        permissionsState.launchMultiplePermissionRequest()
//                        statePermission = false
//                    }
//                }
//                else ->{
//                    if (permissionRequestCompleted){
//                        onclick = {
//                            titleMsg = "Permission"
//                            msg = "Permissions denied. Please enable them in app settings to proceed."
//                            statePermission = false
//                        }
//                    }
//                    else{
//                        if (!hasRequestedPermissions){
//                            statePermission = true
//                            titleMsg = "Permission"
//                            msg = "Notification && Bleutooth permission is required to use this feature."
//                            textPositive = "Autoriser"
//                            onclick = {
//                                permissionsState.launchMultiplePermissionRequest()
//                                hasRequestedPermissions = true
//                                statePermission = false
//                            }
//                        }
//
//                    }
//                }
//            }
            Column(Modifier.padding(5.dp).fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Space(y = 150)
                ImageIconButton(onclick = {
                    navC?.navigate(route = ScreenRoute.Payment.name)
                })
                Space(y = 10)
                Text("Appuyez pour recouvrir sur +", color = Color.Black, fontSize = 18.sp)
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom,
                modifier = Modifier.fillMaxSize()
            ) {
                ConstraintLayout {
                    // Create references for the composables to constrain
                    val (card, buttonLink) = createRefs()
                    Card(
                        modifier = Modifier
                            .fillMaxWidth().height((size / 2).dp),
                        shape = RectangleShape,
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFF25262C)
                        )) {
                    }
                    BoxMainRecouvrement(
                        modifier = Modifier
                            .absoluteOffset(y = (-45).dp, x = 41.dp),
                        onclick = {
                            navC?.navigate(route = ScreenRoute.History.name)
                        },
                        width = sizeWidth + 100,
                        listRecouvrementAmountOfDay = listOfRecouvrementDay
                    )
                }
            }

        }
        if(isShow.value){
            MAlertDialog(
                textNegative = textNegative,
                textPositive = textPositive,
                dialogTitle = titleMsg,
                dialogText =  msg,
                onDismissRequest = {
                    isShow.value = false
                },
                onConfirmation = onclick
            )
        }
//        if (statePermission){
//            PermissionDialog(
//            textPositive = "Autorisé",
//                onOkClick = onclick,
//                dialogText = msg,
//            )
//        }
//        if (statePermission){
//            PermissionDialog(
//                textPositive = "Autorisé",
//                onOkClick = onclick,
//                dialogText = msg,
//            )
//        }
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Preview(showBackground = true)
@Composable
fun HomePreview(){
    HomeBody(null)
}