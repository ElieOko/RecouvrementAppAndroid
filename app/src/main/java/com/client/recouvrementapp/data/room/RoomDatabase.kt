package com.client.recouvrementapp.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.client.recouvrementapp.domain.interfaces.room.ICurrencyDao
import com.client.recouvrementapp.domain.interfaces.room.IPaymentMethodDao
import com.client.recouvrementapp.domain.interfaces.room.IPeriodDao
import com.client.recouvrementapp.domain.interfaces.room.IRecouvrementDao
import com.client.recouvrementapp.domain.interfaces.room.ITransactionTypeDao
import com.client.recouvrementapp.domain.interfaces.room.IUserDao
import com.client.recouvrementapp.domain.model.room.CurrencyModel
import com.client.recouvrementapp.domain.model.room.PaymentMethodModel
import com.client.recouvrementapp.domain.model.room.PeriodModel
import com.client.recouvrementapp.domain.model.room.RecouvrementModel
import com.client.recouvrementapp.domain.model.room.TransactionTypeModel
import com.client.recouvrementapp.domain.model.room.UserModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(
    entities = [CurrencyModel::class, PaymentMethodModel::class, PeriodModel::class, RecouvrementModel::class, TransactionTypeModel::class, UserModel::class], version = 2, exportSchema = false)
abstract class RecouvrementRoomDatabase : RoomDatabase() {
    abstract fun currencyDao(): ICurrencyDao
    abstract fun recouvrementDao(): IRecouvrementDao
    abstract fun transactionTypeDao(): ITransactionTypeDao
    abstract fun paymentMethodDao(): IPaymentMethodDao
    abstract fun userDao(): IUserDao
    abstract fun periodDao(): IPeriodDao
    companion object{
        @Volatile
        private var INSTANCE: RecouvrementRoomDatabase? = null
        fun getDatabase(context: Context): RecouvrementRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RecouvrementRoomDatabase::class.java,
                    "recouvrement"
                ).fallbackToDestructiveMigrationFrom(1)
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
    private class RecouvrementDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let {
                scope.launch {
                    // populateDatabase(database.userDao())
                }
            }
        }
    }
}