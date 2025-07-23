package com.client.recouvrementapp.domain.viewmodel.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.client.recouvrementapp.domain.model.room.PeriodModel
import com.client.recouvrementapp.domain.model.room.TransactionTypeModel
import com.client.recouvrementapp.domain.repository.room.PeriodRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PeriodViewModel(private val repository: PeriodRepository) : ViewModel() {

    private val _listPeriod = MutableStateFlow<List<PeriodModel>>(arrayListOf())
    val listPeriod get() = _listPeriod.asStateFlow()

    fun getAllPeriod(){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _listPeriod.value = repository.allPeriod
            }
        }
    }
    fun insert( periodModel: PeriodModel) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            repository.insert(periodModel)
        }

    }

    fun update( periodModel: PeriodModel) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            repository.update(periodModel)
        }
    }

}

class PeriodViewModelFactory(private val repository: PeriodRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PeriodViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PeriodViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}