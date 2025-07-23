package com.client.recouvrementapp.domain.repository.room

import androidx.annotation.WorkerThread
import com.client.recouvrementapp.domain.interfaces.room.IUserDao
import com.client.recouvrementapp.domain.model.room.UserModel
import kotlinx.coroutines.flow.Flow

class UserRepository(val dao : IUserDao) {
    val allUser : Flow<List<UserModel>> = dao.getAll()

    @WorkerThread
    fun getCurrentUser(userId : Int): List<UserModel> = dao.loadAllById(userId)

    @WorkerThread
    fun insert(user: UserModel) {
        dao.insertAll(user)
    }

    @WorkerThread
    fun update(user: UserModel) {
        dao.updateAll(user)
    }
}