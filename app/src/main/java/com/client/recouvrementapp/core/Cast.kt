package com.client.recouvrementapp.core

import java.util.Locale

/**
 * hex String to byte array
 */
fun hexStringToBytes(hexString: String): ByteArray {
    var hexString = hexString
    hexString = hexString.lowercase(Locale.getDefault())
    val hexStrings: Array<String?> =
        hexString.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
    val bytes = ByteArray(hexStrings.size)
    for (i in hexStrings.indices) {
        val hexChars = hexStrings[i]!!.toCharArray()
        bytes[i] = (charToByte(hexChars[0])
            .toInt() shl 4 or charToByte(hexChars[1])
            .toInt()).toByte()
    }
    return bytes
}

private fun charToByte(c: Char): Byte {
    return "0123456789abcdef".indexOf(c).toByte()
}