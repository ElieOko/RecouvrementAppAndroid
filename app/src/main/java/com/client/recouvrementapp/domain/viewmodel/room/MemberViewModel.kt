package com.client.recouvrementapp.domain.viewmodel.room

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.client.recouvrementapp.domain.model.room.MemberModel
import com.client.recouvrementapp.domain.repository.room.MemberRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MemberViewModel(private val repository: MemberRepository) : ViewModel() {
    private val _listMember = MutableStateFlow<List<MemberModel>>(arrayListOf())
    val listMember get() = _listMember.asStateFlow()

    fun getAllMember(){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _listMember.value = repository.allMember()
            }
        }
    }
    fun insert( memberModel: MemberModel) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            repository.insert(memberModel)
        }

    }

    fun update( memberModel: MemberModel) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            repository.update(memberModel)
        }
    }

}

class MemberViewModelFactory(private val repository: MemberRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MemberViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MemberViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}