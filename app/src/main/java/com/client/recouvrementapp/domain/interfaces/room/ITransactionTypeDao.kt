package com.client.recouvrementapp.domain.interfaces.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.client.recouvrementapp.domain.model.room.TransactionTypeModel
import kotlinx.coroutines.flow.Flow

@Dao
interface ITransactionTypeDao {
    @Query("SELECT * FROM TTransactionType")
    fun getAll(): List<TransactionTypeModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg transactionTypes: TransactionTypeModel)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateAll(vararg transactionTypes: TransactionTypeModel)

    @Delete
    suspend fun delete(transactionType: TransactionTypeModel)
}