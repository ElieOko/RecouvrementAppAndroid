package com.client.recouvrementapp.domain.repository.room

import androidx.annotation.WorkerThread
import com.client.recouvrementapp.domain.interfaces.room.IUserDao
import com.client.recouvrementapp.domain.model.room.PeriodModel
import com.client.recouvrementapp.domain.model.room.UserModel
import kotlinx.coroutines.flow.Flow

class UserRepository(val dao : IUserDao) {
    val allUser : Flow<List<UserModel>> = dao.getAll()

    fun getCurrentUser(userId : Int): Flow<List<UserModel>> = dao.loadAllById(userId)

    @WorkerThread
    suspend fun insert(user: UserModel) {
        dao.insertAll(user)
    }
}