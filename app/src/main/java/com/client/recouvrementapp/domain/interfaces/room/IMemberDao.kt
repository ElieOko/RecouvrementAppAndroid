package com.client.recouvrementapp.domain.interfaces.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.client.recouvrementapp.domain.model.room.MemberModel

@Dao
interface IMemberDao {
    @Query("SELECT * FROM TMember")
    fun getAll(): List<MemberModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg member: MemberModel)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateAll(vararg member: MemberModel)

    @Delete
    suspend fun delete(member: MemberModel)
}