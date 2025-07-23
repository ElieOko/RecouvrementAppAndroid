package com.client.recouvrementapp.domain.viewmodel.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.client.recouvrementapp.domain.model.room.PaymentMethodModel
import com.client.recouvrementapp.domain.model.room.UserModel
import com.client.recouvrementapp.domain.repository.room.PaymentMethodRepository
import com.client.recouvrementapp.domain.repository.room.UserRepository
import kotlinx.coroutines.launch

class PaymentMethodViewModel(private val repository: PaymentMethodRepository) : ViewModel() {

    val getAllPaymentMethod: LiveData<List<PaymentMethodModel>> =  repository.allPaymentMethod.asLiveData()

    fun insert(paymentMethod : PaymentMethodModel) = viewModelScope.launch {
        repository.insert(paymentMethod)
    }

}

class PaymentMethodViewModelFactory(private val repository: PaymentMethodRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PaymentMethodViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PaymentMethodViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}