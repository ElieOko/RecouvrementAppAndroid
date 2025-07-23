package com.client.recouvrementapp.domain.repository.room

import androidx.annotation.WorkerThread
import com.client.recouvrementapp.domain.interfaces.room.ITransactionTypeDao
import com.client.recouvrementapp.domain.model.room.PeriodModel
import com.client.recouvrementapp.domain.model.room.TransactionTypeModel
import kotlinx.coroutines.flow.Flow

class TransactionTypeRepository(val dao: ITransactionTypeDao) {
    val allTransactionType : List<TransactionTypeModel> = dao.getAll()

    @WorkerThread
    suspend fun insert(transactionTypeModel: TransactionTypeModel) {

        dao.insertAll(transactionTypeModel)
    }

    @WorkerThread
    suspend fun update(transactionTypeModel: TransactionTypeModel) {
        dao.updateAll(transactionTypeModel)
    }
}