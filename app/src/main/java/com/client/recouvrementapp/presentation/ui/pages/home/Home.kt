package com.client.recouvrementapp.presentation.ui.pages.home

import android.annotation.SuppressLint
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.client.recouvrementapp.presentation.components.elements.BoxMainRecouvrement
import com.client.recouvrementapp.presentation.components.elements.ImageIconButton
import com.client.recouvrementapp.presentation.components.elements.TopBarCustom
import com.client.recouvrementapp.presentation.components.elements.TopBarSimple
import com.partners.hdfils_recolte.presentation.ui.components.Space

@Composable
fun Home(){
    HomeBody()
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeBody(){
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp.dp
    val screenHeightDp = configuration.screenHeightDp.dp
    val size = ((screenHeightDp.value.toInt() / 2))
    val sizeWidth = ((screenWidthDp.value.toInt() / 2))
    Scaffold(
        topBar = {
            TopBarSimple()
        }
    ) {
        Column(Modifier.padding(it)) {
            Column(Modifier.padding(5.dp).fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Space(y = 150)
                ImageIconButton()
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
                    //0xFF25262C
                    //
                    BoxMainRecouvrement(modifier = Modifier.absoluteOffset(y = (-45).dp, x = 42.dp), width = sizeWidth + 100)
                }

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview(){
    HomeBody()
}