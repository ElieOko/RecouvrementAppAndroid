package com.client.recouvrementapp.domain.viewmodel.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.client.recouvrementapp.domain.model.room.TransactionTypeModel
import com.client.recouvrementapp.domain.model.room.UserModel
import com.client.recouvrementapp.domain.repository.room.TransactionTypeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TransactionTypeViewModel(private val repository: TransactionTypeRepository) : ViewModel() {

    private val _listTypeTransaction = MutableStateFlow<List<TransactionTypeModel>>(arrayListOf())
    val listTypeTransaction get() = _listTypeTransaction.asStateFlow()

    fun getAllTransactionType(){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _listTypeTransaction.value = repository.allTransactionType
            }
        }
    }

    fun insert(transactionType: TransactionTypeModel) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            repository.insert(transactionType)
        }
    }

    fun update(transactionType: TransactionTypeModel) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            repository.update(transactionType)
        }

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