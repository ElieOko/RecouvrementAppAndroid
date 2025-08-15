package com.client.recouvrementapp.domain.model.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "TRecouvrement",
)
data class RecouvrementModel(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "recouvrement_id")
    var id : Int,
    @ColumnInfo(name = "user_id")
    val userId : Int,
    @ColumnInfo(name = "payment_method_id")
    val paymentMethodId : Int,
    @ColumnInfo(name = "period_id")
    val periodId : Int?,
    @ColumnInfo(name = "member_id")
    val memberId : Int?,
    @ColumnInfo(name = "currency_id")
    val currencyId : Int,
    @ColumnInfo(name = "transaction_type")
    val transactionType : String,
    @ColumnInfo(name = "code")
    val code : String,
    @ColumnInfo(name = "amount")
    val amount : Int,
    @ColumnInfo(name = "note")
    val remark : String?,
    @ColumnInfo(name = "date_payment")
    val datePayment : String?,
    @ColumnInfo(name = "created_on")
    val createdOn : String,
    @ColumnInfo(name = "time_on")
    val time : String
)
