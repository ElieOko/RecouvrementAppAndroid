package com.client.recouvrementapp.domain.interfaces.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.client.recouvrementapp.domain.model.room.PeriodModel
import kotlinx.coroutines.flow.Flow

@Dao
interface IPeriodDao {
    @Query("SELECT * FROM TPeriod")
    fun getAll(): Flow<List<PeriodModel>>

    @Insert
    suspend fun insertAll(vararg periodes: PeriodModel)

    @Update
    suspend fun updateAll(vararg periodes: PeriodModel)

    @Delete
    suspend fun delete(periode: PeriodModel)
}