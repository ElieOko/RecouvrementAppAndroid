package com.client.recouvrementapp.presentation.components.elements

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.client.recouvrementapp.domain.model.DataSelect


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectInputField(
    modifier : Modifier = Modifier,
    textValueIn: String,
    onChangeText: (DataSelect) -> Unit = {},
    itemList: List<DataSelect>?,
    icon : Int = 0
) {
    var expanded by remember { mutableStateOf(false) }
    var textValue by remember { mutableStateOf(textValueIn) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {expanded = false}
    ) {
        OutlinedTextField(
            value = textValue,
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
            onValueChange = { textValue = it },
            shape = RoundedCornerShape(10.dp),
            singleLine = true,
            modifier = modifier
                .fillMaxWidth()
                .menuAnchor()
                .clickable{
                    expanded = !expanded
                }
            ,
            readOnly = true,
            leadingIcon = {
                if(icon != 0){
                    Icon(
                        painter = painterResource(icon),
                        contentDescription = null,
                        modifier = Modifier.size(25.dp),
                        tint = Color.Black
                    )
                }

            },
            trailingIcon = {
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        if (!expanded) Icons.Outlined.KeyboardArrowDown else Icons.Outlined.KeyboardArrowUp,
                        contentDescription = "Sélectionner",
                        tint = Color.Black
                    )
                }
            }
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {expanded=false}
        ) {
            itemList?.forEach { item ->
                DropdownMenuItem(
                    modifier = Modifier.fillMaxWidth(),
                    text = {
                        Text(text = item.description.ifEmpty { item.name }, color = Color.Black)
                    },
                    onClick = {
                        textValue = item.description.ifEmpty { item.name }
                        onChangeText(item) // Appel de la fonction de changement
                        expanded = false
                        onChangeText(item)
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectInputFieldSimple(
    modifier : Modifier = Modifier,
    textValueIn: String,
    onChangeText: (DataSelect) -> DataSelect? = {null},
    itemList: List<DataSelect>?
) {
    var expanded by remember { mutableStateOf(false) }
    var textValue by remember { mutableStateOf(textValueIn) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {expanded = false}
    ) {
        OutlinedTextField(
            value = textValue,
            singleLine = true,
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
            onValueChange = { textValue = it },
            shape = RoundedCornerShape(10.dp),
            modifier = modifier
                .fillMaxWidth()
                .menuAnchor()
            ,
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        if (!expanded) Icons.Outlined.KeyboardArrowDown else Icons.Outlined.KeyboardArrowUp,
                        contentDescription = "Sélectionner",
                        tint = Color.Black
                    )
                }
            }
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {expanded=false}
        ) {
            itemList?.forEach { item ->
                DropdownMenuItem(
                    modifier = Modifier.fillMaxWidth(),
                    text = {
                        Text(text = item.name, color = Color.Black)
                    },
                    onClick = {
                        textValue = item.name
                        onChangeText(item) // Appel de la fonction de changement
                        expanded = false
                        onChangeText(item)
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
    }
}