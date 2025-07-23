package com.client.recouvrementapp.domain.viewmodel.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.client.recouvrementapp.domain.model.room.RecouvrementModel
import com.client.recouvrementapp.domain.model.room.RecouvrementWithRelations
import com.client.recouvrementapp.domain.model.room.UserModel
import com.client.recouvrementapp.domain.repository.room.RecouvrementRepository
import com.client.recouvrementapp.domain.repository.room.UserRepository
import kotlinx.coroutines.launch

class RecouvrementViewModel(private val repository: RecouvrementRepository) : ViewModel() {

    fun getAllRecouvrement(userId : Int): LiveData<List<RecouvrementWithRelations>> =  repository.allRecouvrement(userId).asLiveData()

    fun getAllRecouvrementToDay(dateCurrent : String, currencyId : Int, userId : Int): LiveData<List<RecouvrementWithRelations>> =  repository.allRecouvrementDay(dateCurrent,currencyId,userId).asLiveData()

    fun insert(recouvrement: RecouvrementModel) = viewModelScope.launch {
        repository.insert(recouvrement)
    }

}

class RecouvrementViewModelFactory(private val repository: RecouvrementRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecouvrementViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RecouvrementViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}