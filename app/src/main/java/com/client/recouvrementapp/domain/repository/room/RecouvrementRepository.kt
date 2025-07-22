package com.client.recouvrementapp.domain.repository.room

import androidx.annotation.WorkerThread
import com.client.recouvrementapp.domain.interfaces.room.IRecouvrementDao
import com.client.recouvrementapp.domain.model.room.RecouvrementModel
import kotlinx.coroutines.flow.Flow

class RecouvrementRepository(private val recouvrementDao: IRecouvrementDao){

    fun allRecouvrement(userId : Int) : Flow<List<RecouvrementModel>> = recouvrementDao.getAll(userId)

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(recouvrement: RecouvrementModel) {
        recouvrementDao.insertAll(recouvrement)
    }
}