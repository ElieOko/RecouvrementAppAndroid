package com.client.recouvrementapp.domain.viewmodel.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.client.recouvrementapp.domain.model.room.PeriodModel
import com.client.recouvrementapp.domain.model.room.TransactionTypeModel
import com.client.recouvrementapp.domain.model.room.UserModel
import com.client.recouvrementapp.domain.repository.room.TransactionTypeRepository
import com.client.recouvrementapp.domain.repository.room.UserRepository
import kotlinx.coroutines.launch

class TransactionTypeViewModel(private val repository: TransactionTypeRepository) : ViewModel() {

    val getAllTransactionType: LiveData<List<TransactionTypeModel>> =  repository.allTransactionType.asLiveData()

    fun insert(transactionType: TransactionTypeModel) = viewModelScope.launch {
        repository.insert(transactionType)
    }

}

class TransactionTypeViewModelFactory(private val repository: TransactionTypeRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TransactionTypeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TransactionTypeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}