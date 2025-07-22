package com.client.recouvrementapp.domain.model.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "TRecouvrement",
    foreignKeys = [
        ForeignKey(
            entity = UserModel::class,
            parentColumns = arrayOf("user_id"),
            childColumns = arrayOf("user_id"),
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = PaymentMethodModel::class,
            parentColumns = arrayOf("payment_method_id"),
            childColumns = arrayOf("payment_method_id"),
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = CurrencyModel::class,
            parentColumns = arrayOf("currency_id"),
            childColumns = arrayOf("currency_id"),
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = PeriodModel::class,
            parentColumns = arrayOf("period_id"),
            childColumns = arrayOf("period_id"),
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = TransactionTypeModel::class,
            parentColumns = arrayOf("transaction_type_id"),
            childColumns = arrayOf("transaction_type_id"),
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        ),
    ]
)
data class RecouvrementModel(
    @PrimaryKey val uid: Int,
    val id : Int = 0,
    @ColumnInfo(name = "user_id")
    val userId : Int,
    @ColumnInfo(name = "payment_method_id")
    val paymentMethodId : Int,
    @ColumnInfo(name = "period_id")
    val periodId : Int?,
    @ColumnInfo(name = "currency_id")
    val currencyId : Int,
    @ColumnInfo(name = "transaction_type_id")
    val transactionTypeId : Int,
    @ColumnInfo(name = "code")
    val code : String,
    @ColumnInfo(name = "amount")
    val amount : Int,
    @ColumnInfo(name = "note")
    val remark : String?,
    @ColumnInfo(name = "date_payment")
    val datePayment : String?,
)
