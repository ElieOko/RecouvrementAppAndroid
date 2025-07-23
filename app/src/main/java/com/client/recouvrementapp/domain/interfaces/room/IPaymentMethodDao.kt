package com.client.recouvrementapp.domain.interfaces.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import com.client.recouvrementapp.domain.model.room.PaymentMethodModel

@Dao
interface IPaymentMethodDao {
    @Query("SELECT * FROM TPaymentMethod")
    fun getAll(): List<PaymentMethodModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg paymentMethods: PaymentMethodModel)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateAll(vararg paymentMethods: PaymentMethodModel)

    @Delete
    suspend fun delete(paymentMethod: PaymentMethodModel)
}