package com.jrexl.novanector.apiiInterface

import com.jrexl.novanector.dataclass.Withdrawal
import com.jrexl.novanector.dataclass.coinfeedback
import com.jrexl.novanector.dataclass.creditcoindata
import com.jrexl.novanector.dataclass.transactionhistory
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface TransactionCoin {

    @Headers("Content-Type: application/json")
    @POST("/coin/credit")
    suspend fun transactionCoinc(@Body creditcoindata: creditcoindata): Response<coinfeedback>



    @POST("/coin/deduct")
    suspend fun transactionCoin(@Body Withdrawal: Withdrawal): Response<Unit>

    @GET("/coin/transactions/{contactcno}")
    suspend fun getTransactions(@Path("contactcno") contactcno: String?): List<transactionhistory>


}