package com.client.recouvrementapp.data.shared

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class StoreData(private val context: Context) {
    private val gson: Gson = Gson()
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("storeDataRecouvrement")
        val deviceBluetooth = stringPreferencesKey("bluetooth_printer")
        val user_key = stringPreferencesKey("user_key")
    }

    val getBluetoothPrinter: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[deviceBluetooth] ?: ""
        }
    suspend fun setBluetoothPrinter(device : String) {
        context.dataStore.edit { settings ->
            settings[deviceBluetooth] = device
        }
    }
}