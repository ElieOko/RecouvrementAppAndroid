package com.client.recouvrementapp.domain.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.client.recouvrementapp.data.remote.NetworkRepository
import com.client.recouvrementapp.domain.model.room.UserModel
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class KtorViewModel() : ViewModel() {
    private val _request = MutableStateFlow<HttpResponse?>(null)
    private val repository: NetworkRepository = NetworkRepository()
    val responseKtor get() = _request.asStateFlow()
    fun request(context : Context, data : Any = {}, route : String, method : String = "GET"){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _request.value =  repository.requestServer(context,data,route,method)
            }
        }
    }
}

val vmKtor = KtorViewModel()
fun main(){
}