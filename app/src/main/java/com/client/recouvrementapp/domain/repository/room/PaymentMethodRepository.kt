package com.client.recouvrementapp.domain.repository.room

import androidx.annotation.WorkerThread
import com.client.recouvrementapp.domain.interfaces.room.IPaymentMethodDao
import com.client.recouvrementapp.domain.model.core.PaymentMethod
import com.client.recouvrementapp.domain.model.room.CurrencyModel
import com.client.recouvrementapp.domain.model.room.PaymentMethodModel
import kotlinx.coroutines.flow.Flow

class PaymentMethodRepository(val paymentMethodDao: IPaymentMethodDao) {
    @WorkerThread
    fun allPaymentMethod() : List<PaymentMethodModel> = paymentMethodDao.getAll()

    @WorkerThread
    suspend fun insert(paymentMethod: PaymentMethodModel) {
        paymentMethodDao.insertAll(paymentMethod)
    }

    @WorkerThread
    suspend fun update(paymentMethod: PaymentMethodModel) {
        paymentMethodDao.updateAll(paymentMethod)
    }
}