package com.client.recouvrementapp.domain.repository.room

import androidx.annotation.WorkerThread
import com.client.recouvrementapp.domain.interfaces.room.IPeriodDao
import com.client.recouvrementapp.domain.model.room.PaymentMethodModel
import com.client.recouvrementapp.domain.model.room.PeriodModel
import kotlinx.coroutines.flow.Flow

class PeriodRepository(val dao: IPeriodDao) {
    val allPeriod : Flow<List<PeriodModel>> = dao.getAll()

    @WorkerThread
    suspend fun insert(periodModel: PeriodModel) {
        dao.insertAll(periodModel)
    }

    @WorkerThread
    suspend fun update(periodModel: PeriodModel) {
        dao.updateAll(periodModel)
    }
}