package com.client.recouvrementapp.domain.repository.room

import android.util.Log
import androidx.annotation.WorkerThread
import com.client.recouvrementapp.domain.interfaces.room.IRecouvrementDao
import com.client.recouvrementapp.domain.model.room.RecouvrementModel
import com.client.recouvrementapp.domain.model.room.RecouvrementWithRelations
import kotlinx.coroutines.flow.Flow

class RecouvrementRepository(private val recouvrementDao: IRecouvrementDao){

    @WorkerThread
    fun allRecouvrement(userId : Int) : Flow<List<RecouvrementWithRelations>> = recouvrementDao.getAll(userId)

    @WorkerThread
    fun getDetailRecouvrement(recouvrementId : Int) : Flow<RecouvrementWithRelations> = recouvrementDao.getDetailRecouvrement(recouvrementId)

    @WorkerThread
    fun allRecouvrement() : Flow<List<RecouvrementWithRelations>> {
        Log.e("repository get =>","${recouvrementDao.getAll()}")
        return recouvrementDao.getAll()
    }

    @WorkerThread
    fun allRecouvrementDay(dateCurrent: String, currencyId : Int, userId : Int) : Int? = recouvrementDao.getRecouvrementToDay(dateCurrent, currencyId, userId)

    @WorkerThread
    fun allRecouvrementDayCDF(dateCurrent: String, currencyId : Int, userId : Int) : Int? = recouvrementDao.getRecouvrementToDayCDF(dateCurrent, currencyId, userId)

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(recouvrement: RecouvrementModel): Long {
        Log.e("repository =>","$recouvrement")
        return recouvrementDao.insertAll(recouvrement)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(recouvrement: RecouvrementModel) {
        recouvrementDao.updateAll(recouvrement)
    }
}