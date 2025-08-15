package com.client.recouvrementapp.presentation.components.elements

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.client.recouvrementapp.domain.model.RecouvrementAmountOfDay
import com.partners.hdfils_recolte.presentation.ui.components.Space

@Composable
@Preview(showBackground = true)
fun BoxMainRecouvrement(
    modifier: Modifier = Modifier,
    listRecouvrementAmountOfDay : List<RecouvrementAmountOfDay> = emptyList(),
    width : Int = 200,
    onclick : ()->Unit ={}
){
    Card(
        modifier
            .padding(10.dp)
            .width(width.dp)
            .clickable(interactionSource = remember { MutableInteractionSource() },
                indication = null){
                onclick()

        },colors = CardDefaults.cardColors(
        containerColor = Color(0xFF15D77D)
    ), elevation = CardDefaults.cardElevation(5.dp)) {
        Row(modifier = Modifier.fillMaxWidth().padding(5.dp),verticalAlignment = Alignment.CenterVertically) {
//            Icon(painterResource(R.drawable.bottom), contentDescription = "", modifier = Modifier.size(30.dp), tint = Color.Green.copy(alpha = 0.5f))
            Spacer(Modifier.width(10.dp))
            Column{
                Text("Recouvrement pour aujourd'hui",color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Absolute.Right) {
                Box(modifier = Modifier.border(width = 10.dp, color = Color.LightGray.copy(
                    alpha = 0.3F,
                ),shape = RoundedCornerShape(52.dp)).scale(0.75f)){
//                    Icon(painterResource(R.drawable.doc), modifier = Modifier.size(42.dp), contentDescription = "icon",tint = Color.Black)
                }
            }
        }
        Row(modifier = Modifier.fillMaxWidth().padding(5.dp),verticalAlignment = Alignment.CenterVertically) {
            Spacer(Modifier.width(10.dp))
            Column {
                listRecouvrementAmountOfDay.forEach {
                    Text(if(it.currency.symbole.length != 1) "${it.currency.symbole} ${it.amount}" else "${it.currency.symbole}  ${it.amount}", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}