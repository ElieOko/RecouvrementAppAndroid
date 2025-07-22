package com.client.recouvrementapp.domain.interfaces.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.client.recouvrementapp.domain.model.room.TransactionTypeModel
import kotlinx.coroutines.flow.Flow

@Dao
interface ITransactionTypeDao {
    @Query("SELECT * FROM TTransactionType")
    fun getAll(): Flow<List<TransactionTypeModel>>

    @Insert
    suspend fun insertAll(vararg transactionTypes: TransactionTypeModel)

    @Delete
    suspend fun delete(transactionType: TransactionTypeModel)
}