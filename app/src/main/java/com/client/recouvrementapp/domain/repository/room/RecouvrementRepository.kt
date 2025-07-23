package com.client.recouvrementapp.domain.repository.room

import androidx.annotation.WorkerThread
import com.client.recouvrementapp.domain.interfaces.room.IRecouvrementDao
import com.client.recouvrementapp.domain.model.room.RecouvrementModel
import com.client.recouvrementapp.domain.model.room.RecouvrementWithRelations
import kotlinx.coroutines.flow.Flow

class RecouvrementRepository(private val recouvrementDao: IRecouvrementDao){

    @WorkerThread
    fun allRecouvrement(userId : Int) : List<RecouvrementWithRelations> = recouvrementDao.getAll(userId)

    @WorkerThread
    fun allRecouvrementDay(dateCurrent: String, currencyId : Int, userId : Int) : List<RecouvrementWithRelations> = recouvrementDao.getRecouvrementToDay(dateCurrent, currencyId, userId)

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(recouvrement: RecouvrementModel) {
        recouvrementDao.insertAll(recouvrement)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(recouvrement: RecouvrementModel) {
        recouvrementDao.updateAll(recouvrement)
    }
}