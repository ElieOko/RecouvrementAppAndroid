package com.client.recouvrementapp.domain.interfaces.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import androidx.room.Upsert
import com.client.recouvrementapp.domain.model.room.RecouvrementModel
import com.client.recouvrementapp.domain.model.room.RecouvrementWithRelations
import kotlinx.coroutines.flow.Flow

@Dao
interface IRecouvrementDao {

    @Query("SELECT * FROM TRecouvrement")
    fun getAll(): Flow<List<RecouvrementWithRelations>>

    @Transaction
    @Query("SELECT * FROM TRecouvrement WHERE user_id LIKE :userId")
    fun getAll(userId : Int): Flow<List<RecouvrementWithRelations>>

    @Transaction
    @Query("SELECT * FROM TRecouvrement WHERE recouvrement_id LIKE :recouvrementId")
    fun getDetailRecouvrement(recouvrementId : Int): Flow<RecouvrementWithRelations>

    @Query("SELECT SUM(amount) FROM TRecouvrement WHERE created_on LIKE :dateCurrent AND currency_id LIKE :currencyId AND user_id LIKE :userId")
    fun getRecouvrementToDay(dateCurrent: String, currencyId: Int, userId: Int): Int?

    @Query("SELECT SUM(amount) FROM TRecouvrement WHERE created_on LIKE :dateCurrent AND currency_id LIKE :currencyId AND user_id LIKE :userId")
    fun getRecouvrementToDayCDF(dateCurrent: String, currencyId: Int, userId: Int): Int?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(recouvrements: RecouvrementModel):Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateAll(vararg recouvrements: RecouvrementModel)

    @Delete
    fun delete(recouvrement: RecouvrementModel)
}