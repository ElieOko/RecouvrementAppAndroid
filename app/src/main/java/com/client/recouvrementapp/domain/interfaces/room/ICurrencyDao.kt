package com.client.recouvrementapp.domain.interfaces.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.client.recouvrementapp.domain.model.room.CurrencyModel
import kotlinx.coroutines.flow.Flow

@Dao
interface ICurrencyDao {
    @Query("SELECT * FROM TCurrency")
    fun getAll(): List<CurrencyModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg currencies: CurrencyModel)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateAll(vararg currencies: CurrencyModel)

    @Delete
    suspend fun delete(currency: CurrencyModel)
}