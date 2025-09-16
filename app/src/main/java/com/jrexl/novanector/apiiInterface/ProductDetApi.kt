package com.jrexl.novanector.apiiInterface

import com.jrexl.novanector.dataclass.Filtercat
import com.jrexl.novanector.dataclass.OfferDetDatacls
import com.jrexl.novanector.dataclass.ProductDetDatacls
import com.jrexl.novanector.dataclass.categorydatclss
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ProductDetApi {
    @GET("productdetails/productdet")
    suspend fun GetProductDetl(): List<ProductDetDatacls>

    @POST("productdetails/productfilter")
    suspend fun filterdatainter(@Body filter: Filtercat): Response<List<ProductDetDatacls>>

    @GET("offerdetails/offerlist")
    suspend fun GetofferDetl(): List<OfferDetDatacls>

    @GET("client/category")
    suspend fun GetCategory(): List<categorydatclss>
}