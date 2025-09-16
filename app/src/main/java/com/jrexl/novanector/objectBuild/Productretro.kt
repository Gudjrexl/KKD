package com.jrexl.novanector.objectBuild

import com.jrexl.novanector.apiiInterface.ProductDetApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Productretro {
    private const val baseurl = Constf.BASE_URL

    val PrdtApi: ProductDetApi by lazy {
        Retrofit.Builder()
            .baseUrl(baseurl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProductDetApi::class.java)
    }
}