package com.client.recouvrementapp.core.permissions.dialogue

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.client.recouvrementapp.core.permissions.`interface`.PermissionTextProvider
import com.client.recouvrementapp.presentation.components.elements.MAlertDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PermissionDialog(
    permissionTextProvider: String = "",
    onDismiss: () -> Unit = {},
    onOkClick: () -> Unit,
    onGoToAppSettingsClick: () -> Unit = {},
    modifier: Modifier = Modifier,
    textPositive : String,
    dialogText : String
) {
    MAlertDialog(
        dialogTitle = "Permission required",
        textPositive = textPositive,
        onConfirmation = onOkClick,
        dialogText = dialogText
    )
}


class BluetoothPermissionTextProvider : PermissionTextProvider {
    override fun getDescription(isPermanentlyDeclined: Boolean): String {
        return if (isPermanentlyDeclined) {
            "Il semble que vous ayez définitivement refusé l'autorisation Bluetooth. " +
                    "Vous pouvez vous rendre dans les paramètres de l'application pour l'accorder."
        } else {
            "Cette application a besoin d'accéder au Bluetooth pour se connecter à des appareils à proximité."
        }
    }
}

class CameraPermissionTextProvider: PermissionTextProvider {
    override fun getDescription(isPermanentlyDeclined: Boolean): String {
        return if(isPermanentlyDeclined) {
            "It seems you permanently declined camera permission. " +
                    "You can go to the app settings to grant it."
        } else {
            "This app needs access to your camera so that your friends " +
                    "can see you in a call."
        }
    }
}

class RecordAudioPermissionTextProvider: PermissionTextProvider {
    override fun getDescription(isPermanentlyDeclined: Boolean): String {
        return if(isPermanentlyDeclined) {
            "It seems you permanently declined microphone permission. " +
                    "You can go to the app settings to grant it."
        } else {
            "This app needs access to your microphone so that your friends " +
                    "can hear you in a call."
        }
    }
}

class PhoneCallPermissionTextProvider: PermissionTextProvider {
    override fun getDescription(isPermanentlyDeclined: Boolean): String {
        return if(isPermanentlyDeclined) {
            "It seems you permanently declined phone calling permission. " +
                    "You can go to the app settings to grant it."
        } else {
            "This app needs phone calling permission so that you can talk " +
                    "to your friends."
        }
    }
}