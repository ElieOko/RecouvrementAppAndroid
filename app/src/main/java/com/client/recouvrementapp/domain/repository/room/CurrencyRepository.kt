package com.client.recouvrementapp.domain.repository.room

import androidx.annotation.WorkerThread
import com.client.recouvrementapp.domain.interfaces.room.ICurrencyDao
import com.client.recouvrementapp.domain.model.room.CurrencyModel
import kotlinx.coroutines.flow.Flow

class CurrencyRepository(private val currencyDao: ICurrencyDao) {
    val allCurrency : List<CurrencyModel> = currencyDao.getAll()

    @WorkerThread
    suspend fun insert(currencies: CurrencyModel) {
        currencyDao.insertAll(currencies)
    }

    @WorkerThread
    suspend fun update(currencies: CurrencyModel) {
        currencyDao.updateAll(currencies)
    }
}