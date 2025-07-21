package com.client.recouvrementapp.core.permissions

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.client.recouvrementapp.core.permissions.dialogue.BluetoothPermissionTextProvider
import com.client.recouvrementapp.core.permissions.dialogue.PermissionDialog
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
@Preview(showBackground = true)
fun ComposePermission(){
    val context = LocalContext.current
    val bleutoothConnect = rememberPermissionState(PermissionAndroid.BLUETOOTH)

    fun getScreenState(state: PermissionState) = when (state.status) {
        is PermissionStatus.Denied -> PermissionScreenState(
            title = "Bleutooth", buttonText = "Grant permission"
        )

        PermissionStatus.Granted -> PermissionScreenState(
            title = "Vous êtes connectez", buttonText = "Call"
        )
    }


    // Defines the PermissionScreen UI based on the permission state and user interactions
    var screenState by remember(bleutoothConnect.status) {
        mutableStateOf(getScreenState(bleutoothConnect))
    }
    val bleutooth = isPermissionGranted(PermissionAndroid.BLUETOOTH,context)
//    PermissionDialog(
//        BluetoothPermissionTextProvider(),
//        bleutooth,
//        {},{},{}
//    )
//    PermissionScreen(
//        state = screenState,
//        onClick = {
//            when (bleutoothConnect.status) {
//                PermissionStatus.Granted -> {
//                    Toast.makeText(context, "Faking a call...", Toast.LENGTH_SHORT).show()
//                }
//
//                is PermissionStatus.Denied -> {
//                    if ((bleutoothConnect.status as PermissionStatus.Denied).shouldShowRationale) {
//                        // Update our UI based on the user interaction by showing a rationale
//                        screenState = PermissionScreenState(
//                            title = "Call a phone",
//                            buttonText = "Grant permission",
//                            rationale = "In order to perform the call you need to grant access by accepting the next permission dialog.\n\nWould you like to continue?"
//                        )
//                    } else {
//                        // Directly launch the system permission dialog
//                        bleutoothConnect.launchPermissionRequest()
//                    }
//                }
//            }
//        },
//        onRationaleReply = { accepted ->
//            if (accepted) {
//                bleutoothConnect.launchPermissionRequest()
//            }
//            // Reset the state after user interaction
//            screenState = getScreenState(bleutoothConnect)
//        }
//    )
}