package com.client.recouvrementapp

import android.app.Application
import com.client.recouvrementapp.data.room.RecouvrementRoomDatabase
import com.client.recouvrementapp.domain.repository.room.CurrencyRepository
import com.client.recouvrementapp.domain.repository.room.PaymentMethodRepository
import com.client.recouvrementapp.domain.repository.room.PeriodRepository
import com.client.recouvrementapp.domain.repository.room.RecouvrementRepository
import com.client.recouvrementapp.domain.repository.room.TransactionTypeRepository
import com.client.recouvrementapp.domain.repository.room.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class RecouvrementApplication : Application() {
    private val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { RecouvrementRoomDatabase.getDatabase(this,applicationScope) }
    val userRepository by lazy { UserRepository(database.userDao()) }
    val recouvrementRepository by lazy { RecouvrementRepository(database.recouvrementDao()) }
    val paymentMethodRepository by lazy { PaymentMethodRepository(database.paymentMethodDao()) }
    val currencyRepository by lazy { CurrencyRepository(database.currencyDao()) }
    val transactionTypeRepository by lazy { TransactionTypeRepository(database.transactionTypeDao()) }
    val periodRepository by lazy { PeriodRepository(database.periodDao()) }
}

//    override fun onTrimMemory(level: Int) {
//        super.onTrimMemory(level)
//    }