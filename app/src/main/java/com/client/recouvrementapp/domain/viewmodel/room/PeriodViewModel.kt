package com.client.recouvrementapp.domain.viewmodel.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.client.recouvrementapp.domain.model.room.PeriodModel
import com.client.recouvrementapp.domain.repository.room.PeriodRepository
import kotlinx.coroutines.launch

class PeriodViewModel(private val repository: PeriodRepository) : ViewModel() {

    val getAllPeriod: LiveData<List<PeriodModel>> =  repository.allPeriod.asLiveData()

    fun insert( periodModel: PeriodModel) = viewModelScope.launch {
        repository.insert(periodModel)
    }

    fun update( periodModel: PeriodModel) = viewModelScope.launch {
        repository.update(periodModel)
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