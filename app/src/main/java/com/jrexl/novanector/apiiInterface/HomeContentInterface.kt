package com.jrexl.novanector.apiiInterface

import com.jrexl.novanector.dataclass.Cardinfo
import com.jrexl.novanector.dataclass.advertiseHomeImage
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeContentInterface {
    @GET("/user/card")
    suspend fun getCardInfo(@Query("phone") phone: String): Cardinfo

    @GET("/advertisement/Homeimages")
    suspend fun getImageAdvertisement(): List<advertiseHomeImage>
}