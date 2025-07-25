package com.client.recouvrementapp.domain.viewmodel.room

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.client.recouvrementapp.domain.model.room.RecouvrementModel
import com.client.recouvrementapp.domain.model.room.RecouvrementWithRelations
import com.client.recouvrementapp.domain.repository.room.RecouvrementRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RecouvrementViewModel(private val repository: RecouvrementRepository) : ViewModel() {

    private val _listRecouvrement = MutableStateFlow<List<RecouvrementWithRelations>>(arrayListOf())
    private val _recouvrementDetail = MutableStateFlow<RecouvrementWithRelations?>(RecouvrementWithRelations())
    private val _listRecouvrementAll = MutableStateFlow<List<RecouvrementWithRelations>>(emptyList())
    private val _stateSave = MutableStateFlow<Long>(0)
    val listRecouvrement get() = _listRecouvrement.asStateFlow()
    val recouvrementDetail get() = _recouvrementDetail.asStateFlow()
    val stateSave get() = _stateSave.asStateFlow()
    val listRecouvrementAll get() = _listRecouvrementAll.asStateFlow()

    private val _listRecouvrementToDay = MutableStateFlow<Int?>(0)
    private val _listRecouvrementToDayCDF = MutableStateFlow<Int?>(0)
    val listRecouvrementToday get() = _listRecouvrementToDay.asStateFlow()
    val listRecouvrementTodayCDF get() = _listRecouvrementToDayCDF.asStateFlow()

    fun getAllRecouvrement(userId : Int) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            repository.allRecouvrement(userId).collect {
                _listRecouvrement.value = it
            }
        }
    }

    fun getDetailRecouvrement(recouvrementId : Int) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            repository.getDetailRecouvrement(recouvrementId).collect {
                _recouvrementDetail.value = it
            }
        }
    }


    init {
        viewModelScope.launch {
            repository.allRecouvrement().collect {
                _listRecouvrementAll.value = it
            }
        }
    }
    fun getAllRecouvrement() = viewModelScope.launch {
        withContext(Dispatchers.IO) {
              repository.allRecouvrement().collect {
                  _listRecouvrementAll.value = it
                  Log.e("view model =>","$it")
            }

        }
    }


    fun getAllRecouvrementToDay(dateCurrent : String, currencyId : Int, userId : Int) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            _listRecouvrementToDay.value = repository.allRecouvrementDay(dateCurrent,currencyId,userId)
            Log.e("vm today ->","${_listRecouvrementToDay.value}")
        }
    }

    fun getAllRecouvrementToDayCDF(dateCurrent : String, currencyId : Int, userId : Int) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            _listRecouvrementToDayCDF.value = repository.allRecouvrementDayCDF(dateCurrent,currencyId,userId)
        }
    }


    fun insert(recouvrement: RecouvrementModel) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            _stateSave.value = repository.insert(recouvrement)
            Log.e("vm insert =>","$recouvrement")
            Log.e("state row =>","${_stateSave.value}")
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