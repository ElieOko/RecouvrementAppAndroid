package com.client.recouvrementapp.presentation.components.elements

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.partners.hdfils_recolte.presentation.ui.components.Space
import com.client.recouvrementapp.R
import com.client.recouvrementapp.domain.model.MenuItem
import com.client.recouvrementapp.presentation.ui.theme.bagdeColor

@Composable
fun TopBarCustom(
    username: String = "elieoko"
){
    Column(Modifier) {
        Row(
            Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(R.drawable.user),
                contentDescription = "",
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
            )
            Column(Modifier.padding(10.dp)) {
                Space(y=1)
                Label("@$username", modifier = Modifier.absoluteOffset(x=7.dp), color = Color.Black)
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
//                MIcon(Icons.Default.Notifications)
//                Space(x = 10)
//                MIcon(Icons.Default.Create)
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarSimple(
    username: String = "elieoko",
    title : String = "Recouvrement",
    onclickLogOut : ()->Unit = {},
    onBackEvent : ()-> Unit = {},
    onclick :()-> Unit = {},
    menuItem :List<MenuItem> = emptyList(),
    isMain : Boolean = true
){
    var expanded by remember { mutableStateOf(false) }
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White.copy(0.8F)),
        navigationIcon = {
            if (!isMain){
                IconButton(onClick = {onBackEvent()}) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, null, modifier = Modifier.size(24.dp))
                }
            }
        },
        title = { Text(title) },
        actions = {
            if (isMain){
                IconButton(onClick = {onclickLogOut()}) {
                    Icon(painterResource(R.drawable.logout), null, modifier = Modifier.size(24.dp))
                }
                Space(x = 18)
                IconButton(
                    onClick = onclick,
                    colors = IconButtonDefaults.iconButtonColors(containerColor = bagdeColor),
                    modifier = Modifier.size(32.dp).border(width = 10.dp,
                        color = Color(0xF788F18A),
                        shape = CircleShape
                    )
                ) {
                    Text(username[0].uppercaseChar().toString(), color = Color.White)
                }
                Space(x = 10)
                Box {
                    IconButton({}) {
                        Icon(painterResource(R.drawable.menu), null, modifier = Modifier.size(24.dp))
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        menuItemData.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option) },
                                onClick = { /* Do something... */ }
                            )
                        }
                    }
                }

            }
        }
    )
}