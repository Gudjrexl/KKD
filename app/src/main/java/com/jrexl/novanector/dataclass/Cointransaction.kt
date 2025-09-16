package com.jrexl.novanector.dataclass

import com.google.gson.annotations.SerializedName

data class Withdrawal(
    var contactcno: String,
    var coins: Int
)
data class transactionhistory(
    var contactcno: String,
    var itemexp: String,
    var typetransaction: String,
    var coins: Int,
    var date: String
)
data class creditcoindata(
    var contactcno: String?,
    var coins: Int,
    var scanQRCodes: String

)

data class coinfeedback(
    val coinusername: String,
    val coindate: String,
    val creditcoininacount: Boolean
)