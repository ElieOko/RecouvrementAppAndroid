package com.client.recouvrementapp.core.permissions.`interface`

interface PermissionTextProvider {
    fun getDescription(isPermanentlyDeclined: Boolean): String
}
