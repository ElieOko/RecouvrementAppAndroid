package com.client.recouvrementapp.core

import java.text.Normalizer
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
fun removeAccents(input: String): String {
    // Décompose les caractères accentués en caractères de base + accents
    val normalized = Normalizer.normalize(input, Normalizer.Form.NFD)
    // Supprime tous les diacritiques (accents)
    return normalized.replace("\\p{InCombiningDiacriticalMarks}+".toRegex(), "")
}