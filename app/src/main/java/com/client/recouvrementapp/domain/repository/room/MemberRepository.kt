package com.client.recouvrementapp.domain.repository.room

import androidx.annotation.WorkerThread
import com.client.recouvrementapp.domain.interfaces.room.IMemberDao
import com.client.recouvrementapp.domain.model.room.MemberModel

class MemberRepository(val dao: IMemberDao) {
    @WorkerThread
    fun allMember () : List<MemberModel> = dao.getAll()

    @WorkerThread
    suspend fun insert(memberModel: MemberModel) {
        dao.insertAll(memberModel)
    }

    @WorkerThread
    suspend fun update(memberModel: MemberModel) {
        dao.updateAll(memberModel)
    }
}