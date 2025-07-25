package com.client.recouvrementapp.core

import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

data class DateTimeModel(
    val date : String,
    val time : String
)
fun dateISOConvert(dateISO : String): DateTimeModel {
    val dateTime = OffsetDateTime.parse(dateISO)
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss")
    val heure = dateTime.format(timeFormatter)
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val formattedDate = dateTime.format(formatter)
    return DateTimeModel(formattedDate,heure)
}