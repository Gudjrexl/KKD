package com.jrexl.novanector.objectBuild

import com.jrexl.novanector.apiiInterface.ProfileInterface
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    val baseurl = Constf.BASE_URL
    val api: ProfileInterface by lazy {
        Retrofit.Builder()
            .baseUrl(baseurl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProfileInterface::class.java)
    }
}
