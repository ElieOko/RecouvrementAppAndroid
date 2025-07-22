package com.client.recouvrementapp.domain.interfaces.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.client.recouvrementapp.domain.model.room.UserModel
import kotlinx.coroutines.flow.Flow

@Dao
interface IUserDao {
    @Query("SELECT * FROM TUser")
    fun getAll(): Flow<List<UserModel>>

    @Query("SELECT * FROM TUser WHERE user_id IN (:userId)")
    fun loadAllById(userId: Int): Flow<List<UserModel>>

    @Insert
    fun insertAll(vararg users: UserModel)

    @Delete
    fun delete(user: UserModel)
}