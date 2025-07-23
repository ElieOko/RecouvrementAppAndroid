package com.client.recouvrementapp.domain.interfaces.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.client.recouvrementapp.domain.model.room.RecouvrementModel
import com.client.recouvrementapp.domain.model.room.RecouvrementWithRelations
import kotlinx.coroutines.flow.Flow

@Dao
interface IRecouvrementDao {
    @Transaction
    @Query("SELECT * FROM TRecouvrement WHERE user_id LIKE :userId")
    fun getAll(userId : Int): Flow<List<RecouvrementWithRelations>>

    @Transaction
    @Query("SELECT * FROM TRecouvrement WHERE date_payment LIKE :dateCurrent AND currency_id LIKE :currencyId AND user_id LIKE :userId")
    fun getRecouvrementToDay(dateCurrent: String, currencyId: Int, userId: Int): Flow<List<RecouvrementWithRelations>>

    @Insert
    fun insertAll(vararg recouvrements: RecouvrementModel)

    @Delete
    fun delete(recouvrement: RecouvrementModel)
}