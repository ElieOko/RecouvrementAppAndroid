package com.client.recouvrementapp.data.room

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.client.recouvrementapp.core.dateISOConvert
import com.client.recouvrementapp.domain.interfaces.room.ICurrencyDao
import com.client.recouvrementapp.domain.interfaces.room.IPaymentMethodDao
import com.client.recouvrementapp.domain.interfaces.room.IPeriodDao
import com.client.recouvrementapp.domain.interfaces.room.IRecouvrementDao
import com.client.recouvrementapp.domain.interfaces.room.ITransactionTypeDao
import com.client.recouvrementapp.domain.interfaces.room.IUserDao
import com.client.recouvrementapp.domain.model.core.Currency
import com.client.recouvrementapp.domain.model.core.PaymentMethod
import com.client.recouvrementapp.domain.model.room.CurrencyModel
import com.client.recouvrementapp.domain.model.room.PaymentMethodModel
import com.client.recouvrementapp.domain.model.room.PeriodModel
import com.client.recouvrementapp.domain.model.room.RecouvrementModel
import com.client.recouvrementapp.domain.model.room.TransactionTypeModel
import com.client.recouvrementapp.domain.model.room.UserModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.concurrent.Executors

@Database(
    entities = [CurrencyModel::class, PaymentMethodModel::class, PeriodModel::class, RecouvrementModel::class, TransactionTypeModel::class, UserModel::class], version = 10, exportSchema = false)
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
        fun getDatabase(context: Context, scope: CoroutineScope): RecouvrementRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RecouvrementRoomDatabase::class.java,
                    "RecouvrementDatabase.db"
                )
                    .setQueryCallback({ sqlQuery, bindArgs ->
                        Log.d("ROOM_SQL", "SQL Query: $sqlQuery SQL Args: $bindArgs")
                    }, Executors.newSingleThreadExecutor())
                    //.addMigrations(migrations = (8,9))
                    //.allowMainThreadQueries()
                    .addCallback(RecouvrementDatabaseCallback(scope))
                    .addCallback(object : Callback() {
                        override fun onOpen(db: SupportSQLiteDatabase) {
                            super.onOpen(db)
                            db.execSQL("PRAGMA foreign_keys=ON")
                            Log.d("DB", "Database opened")
                        }
                    })
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
    private class RecouvrementDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database->
                scope.launch {
                     //populateDatabase(database.userDao())
                    val recouvrementDao = database.recouvrementDao()
                    val currencyDao = database.currencyDao()
                    val userDao = database.userDao()
                    val methodDao = database.paymentMethodDao()

                    // Exemple de données par défaut
//                    val dateTimeModel = dateISOConvert("2025-07-24T10:01:05.5889257Z")
//                    val defaultRec = listOf(
//                        RecouvrementModel(
//                            id = 50,
//                            userId =  2,
//                            paymentMethodId = 2,
//                            periodId = 1,
//                            currencyId = 1,
//                            transactionType = "Subscription",
//                            code = "005",
//                            amount = 100,
//                            remark = "",
//                            datePayment = "",
//                            createdOn = dateTimeModel.date,
//                            time = dateTimeModel.time
//                        ),
//                        RecouvrementModel(
//                            id = 51,
//                            userId =  2,
//                            paymentMethodId = 2,
//                            periodId = null,
//                            currencyId = 1,
//                            transactionType = "Subscription",
//                            code = "005",
//                            amount = 500,
//                            remark = "",
//                            datePayment = "",
//                            createdOn = dateTimeModel.date,
//                            time = dateTimeModel.time
//                        ),
//                    )
//                    userDao.insertAll(
//                        UserModel(id = 2,"elieoko","test")
//                    )
//                    currencyDao.insertAll(CurrencyModel(1,"Dollar", "USD","$"),
//                        CurrencyModel(2,"Franc Congolais", "CDF","Fc")
//                    )
//                    methodDao.insertAll(
//                        PaymentMethodModel(id = 1, name = "Paiements par carte bancaire"),
//                        PaymentMethodModel(id = 2, name = "Paiement en espèces"),
//                        PaymentMethodModel(id = 3, name = "Paiements mobiles")
//                    )
//                    recouvrementDao.insertAll(defaultRec[0])
//                    recouvrementDao.insertAll(defaultRec[1])
//                    recouvrementDao.getAll().collect {
//                        Log.d("with database build rec","$it")
//                    }
                }
            }
        }

    }
}
