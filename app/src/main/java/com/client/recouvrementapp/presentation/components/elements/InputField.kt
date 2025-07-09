package com.client.recouvrementapp.presentation.components.elements

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun InputField(
    modifier: Modifier = Modifier,
    value : String,
    onValueChange: (String) -> Unit,
    icon : Int = 0,
    iconLast : Int? = null,
    isSingle : Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    placeholder : String = "",
    onclickLastIcon : ()-> Unit = {}
) {
    OutlinedTextField(
        value = value,
        keyboardOptions = keyboardOptions,
        onValueChange = onValueChange,
        placeholder = {Text(placeholder)},
        leadingIcon = {
            if (icon != 0){
                Icon(
                    painter = painterResource(icon),
                    contentDescription = null,
                    modifier = Modifier.size(25.dp),
                    tint = Color.Black
                )
            }
        },
        trailingIcon = {
            if (iconLast != null){
                IconButton(onClick = onclickLastIcon) {
                    Icon(painterResource(iconLast),null)
                }
            }
        },
        maxLines = 1,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Black,
            unfocusedBorderColor = Color.Black,
            focusedLabelColor = Color.Black,
            unfocusedLabelColor = Color.Black,
            cursorColor = Color.Black,
            focusedLeadingIconColor = Color.Black,
            unfocusedLeadingIconColor = Color.Black
        ),
        textStyle = TextStyle(color = Color.Black),
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        singleLine = isSingle
    )
}


@Composable
fun InputFieldCompose(
    modifier: Modifier = Modifier,
    value : String,
    onValueChange: (String) -> Unit,
    iconLast : Int? = null,
    isSingle : Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    placeholder : String = "",
    onclickLastIcon : ()-> Unit = {}
) {
    OutlinedTextField(
        value = value,
        keyboardOptions = keyboardOptions,
        onValueChange = onValueChange,
        placeholder = {Text(placeholder)},
        trailingIcon = {
            if (iconLast != null){
                IconButton(onClick = onclickLastIcon) {
                    Icon(painterResource(iconLast),null)
                }
            }
        },
        maxLines = 1,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Black,
            unfocusedBorderColor = Color.Black,
            focusedLabelColor = Color.Black,
            unfocusedLabelColor = Color.Black,
            cursorColor = Color.Black,
            focusedLeadingIconColor = Color.Black,
            unfocusedLeadingIconColor = Color.Black
        ),
        textStyle = TextStyle(color = Color.Black),
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        singleLine = isSingle
    )
}