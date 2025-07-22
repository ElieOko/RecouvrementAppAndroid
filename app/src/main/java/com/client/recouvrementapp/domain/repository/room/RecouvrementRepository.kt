package com.client.recouvrementapp.domain.repository.room

import androidx.annotation.WorkerThread
import com.client.recouvrementapp.domain.interfaces.room.IRecouvrementDao
import com.client.recouvrementapp.domain.model.room.RecouvrementModel
import com.client.recouvrementapp.domain.model.room.RecouvrementWithRelations
import kotlinx.coroutines.flow.Flow

class RecouvrementRepository(private val recouvrementDao: IRecouvrementDao){

    fun allRecouvrement(userId : Int) : Flow<List<RecouvrementWithRelations>> = recouvrementDao.getAll(userId)

    fun allRecouvrementDay(dateCurrent: String, currencyId : Int) : Flow<List<RecouvrementWithRelations>> = recouvrementDao.getRecouvrementToDay(dateCurrent, currencyId)

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(recouvrement: RecouvrementModel) {
        recouvrementDao.insertAll(recouvrement)
    }
}