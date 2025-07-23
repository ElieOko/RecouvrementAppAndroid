package com.client.recouvrementapp.domain.viewmodel.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.client.recouvrementapp.domain.model.room.RecouvrementModel
import com.client.recouvrementapp.domain.model.room.RecouvrementWithRelations
import com.client.recouvrementapp.domain.model.room.TransactionTypeModel
import com.client.recouvrementapp.domain.repository.room.RecouvrementRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RecouvrementViewModel(private val repository: RecouvrementRepository) : ViewModel() {

    private val _listRecouvrement = MutableStateFlow<List<RecouvrementWithRelations>>(arrayListOf())
    val listRecouvrement get() = _listRecouvrement.asStateFlow()

    private val _listRecouvrementToDay = MutableStateFlow<List<RecouvrementWithRelations>>(arrayListOf())
    val listRecouvrementToday get() = _listRecouvrementToDay.asStateFlow()

    fun getAllRecouvrement(userId : Int) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            _listRecouvrement.value = repository.allRecouvrement(userId)
        }
    }


    fun getAllRecouvrementToDay(dateCurrent : String, currencyId : Int, userId : Int) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            _listRecouvrementToDay.value = repository.allRecouvrementDay(dateCurrent,currencyId,userId)
        }
    }


    fun insert(recouvrement: RecouvrementModel) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            repository.insert(recouvrement)
        }
    }


    fun update(recouvrement: RecouvrementModel) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            repository.update(recouvrement)
        }
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