package com.client.recouvrementapp.presentation.components.elements

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.client.recouvrementapp.R
import com.client.recouvrementapp.presentation.ui.theme.bagdeColor

@Composable
@Preview(showBackground = true)
fun ImageIconButton(
    onclick : ()->Unit = {}
){
    val infiniteTransition = rememberInfiniteTransition(label = "infinite transition")
    val animatedColor by infiniteTransition.animateColor(
        initialValue = Color(0xF788F18A),
        targetValue = Color(0xFFFFFFFF),
        animationSpec = infiniteRepeatable(tween(1000), RepeatMode.Reverse),
        label = "color"
    )
    Box {
        Image(
            painter = painterResource(R.drawable.recouvrement),
            contentDescription = null,
            modifier = Modifier
                .size(145.dp)
                .clip(RoundedCornerShape(60),
                ),
            contentScale = ContentScale.Crop
        )
        Row(modifier = Modifier.padding(2.dp).absoluteOffset(y = (110).dp,x = (109).dp)) {
            IconButton(
                onClick = onclick,
                colors = IconButtonDefaults.iconButtonColors(containerColor = bagdeColor),
                modifier = Modifier.size(32.dp).border(width = 10.dp,
                    color = animatedColor,
                    shape = CircleShape
                )
            ) {
                Icon(painterResource(R.drawable.plus),null, tint = Color.White)
            }
        }
    }
}