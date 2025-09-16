package com.jrexl.novanector.objectBuild

import com.jrexl.novanector.apiiInterface.ApiService
import com.jrexl.novanector.apiiInterface.HomeContentInterface
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object HomeContentApi {
    private const val BASE_URL = Constf.BASE_URL

    val HCapi: HomeContentInterface by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(HomeContentInterface::class.java)
    }
}