package com.jrexl.novanector.objectBuild

import com.jrexl.novanector.apiiInterface.ApiService
import com.jrexl.novanector.apiiInterface.TransactionCoin
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Transactioncoinobject {
    private const val BASE_URL = Constf.BASE_URL

    val withsrawalapi: TransactionCoin by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TransactionCoin::class.java)
    }
}