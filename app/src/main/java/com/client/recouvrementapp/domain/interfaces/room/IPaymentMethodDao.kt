package com.client.recouvrementapp.domain.interfaces.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import com.client.recouvrementapp.domain.model.room.PaymentMethodModel

@Dao
interface IPaymentMethodDao {
    @Query("SELECT * FROM TPaymentMethod")
    fun getAll(): Flow<List<PaymentMethodModel>>

    @Insert
    suspend fun insertAll(vararg paymentMethods: PaymentMethodModel)

    @Delete
    suspend fun delete(paymentMethod: PaymentMethodModel)
}