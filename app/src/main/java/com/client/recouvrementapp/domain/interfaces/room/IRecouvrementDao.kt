package com.client.recouvrementapp.domain.interfaces.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.client.recouvrementapp.domain.model.room.RecouvrementModel
import kotlinx.coroutines.flow.Flow

@Dao
interface IRecouvrementDao {
    @Query("SELECT * FROM TRecouvrement")
    fun getAll(): Flow<List<RecouvrementModel>>

    @Insert
    fun insertAll(vararg recouvrements: RecouvrementModel)

    @Delete
    fun delete(recouvrement: RecouvrementModel)
}