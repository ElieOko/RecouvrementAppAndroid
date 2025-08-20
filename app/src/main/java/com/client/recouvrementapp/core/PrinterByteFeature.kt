package com.client.recouvrementapp.core

import kotlin.byteArrayOf

object PrinterByteFeature {
    const val cmdEsc   : Byte   = 0x1B
    const val tab   : Byte   = 0x09
    const val FormFeed = 0x0C
    val leftAlignemt = byteArrayOf(cmdEsc,0x61,0x00)
    val centerAlignemt = byteArrayOf(cmdEsc,0x61,0x01)
    val rightAlignemt = byteArrayOf(cmdEsc,0x61,0x02)
    val italiqueOn = byteArrayOf(cmdEsc,0x34,0x01)
    val italiqueOff = byteArrayOf(cmdEsc,0x34,0x00)
    val boldOn = byteArrayOf(cmdEsc,0x45,0x00)
    val boldOff = byteArrayOf(cmdEsc,0x45,0x01)
    fun line(n : Byte = 0x02): ByteArray {
        val line = byteArrayOf(cmdEsc,0x64, n)
        return line
    }

    fun space(size : Int) = " ".repeat(size)
    val title = "FJS ASBL"
    val subTitle = "RECU"
    val phone = "+243 978276398"
    val email = "fondationjonathansanguofficiel@gmail.com"
    fun textAlignRow(label : String, valueText : String){
        val first = label.toByteArray(charset("GBK"))
        val second = valueText.toByteArray(charset("GBK"))
    }
}