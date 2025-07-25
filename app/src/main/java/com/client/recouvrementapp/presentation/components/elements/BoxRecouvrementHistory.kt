package com.client.recouvrementapp.presentation.components.elements

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.client.recouvrementapp.R
import com.client.recouvrementapp.domain.model.room.RecouvrementWithRelations


@Composable
@Preview(showBackground = true)
fun BoxRecouvrementHistory(
    data : RecouvrementWithRelations? = null,
    onclick:()-> Unit = {}
){
    Card(Modifier.padding(10.dp).clickable(interactionSource = remember { MutableInteractionSource() },
        indication = null){
        onclick()
    },colors = CardDefaults.cardColors(
        containerColor = Color(0xFF15D77D),
        contentColor = Color.White
    ), elevation = CardDefaults.cardElevation(5.dp)) {
        Row(modifier = Modifier.fillMaxWidth().padding(5.dp),verticalAlignment = Alignment.CenterVertically) {
            Column() {
                Text("${data?.recouvrement?.id}")
                Text("Date et heure: ${data?.recouvrement?.createdOn}")
                Text("${data?.recouvrement?.time}")
            }
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Absolute.Right) {
                Text("${data?.recouvrement?.amount}${data?.currency?.symbole}", fontSize = 16.sp, modifier = Modifier.absoluteOffset(y = 10.dp))
                Spacer(Modifier.width(5.dp))
                Box(modifier = Modifier.border(width = 10.dp, color = Color.LightGray.copy(
                    alpha = 0.3F,
                ),shape = RoundedCornerShape(52.dp)).scale(0.75f)){
                    Icon(painterResource(R.drawable.communication), modifier = Modifier.size(35.dp), contentDescription = "icon")
                }
            }

        }

    }
}