package com.jrexl.novanector.apiiInterface

import com.jrexl.novanector.dataclass.AdharPanDetails
import com.jrexl.novanector.dataclass.Bankverificationdata
import com.jrexl.novanector.dataclass.ProfileDataClass
import com.jrexl.novanector.dataclass.kycadpan
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface ProfileInterface {
    @GET("user/profiledata/{contactno}")
    suspend fun Profileget(@Path("contactno") contactno: String?): Response<ProfileDataClass>

    @POST("user/profiledata")
    suspend fun ProfileUpdate(@Body profileDataClass: ProfileDataClass): Response<Unit>

    @Multipart
    @PUT("/user/profiledata/{contactno}")
    suspend fun Profiepicupdate(
        @Path("contactno") contactno: String?,
        @Part image: MultipartBody.Part): Response<Unit>


    @GET("adharpandetails/{contactno}")
    suspend fun getAdharPanDetails(
        @Path("contactno") contactno: String?
    ): Response<AdharPanDetails>



    @POST("/user/bankverification")
    suspend fun bankverificationdata(@Body bankverificationdata: Bankverificationdata): Response<Unit>

    @Multipart
    @PUT("/user/bankverification/{contactno}")
    suspend fun bankverifyimg(
        @Path("contactno") contactno: String?,
        @Part imageFile: MultipartBody.Part): Response<Unit>

    @POST("/user/kycadpan")
    suspend fun kycadpan(@Body kycadpan: kycadpan): Response<Unit>



    @Multipart
    @PUT("/user/kycadpan/adhar/{contactno}")
    suspend fun kycadharimg(
        @Path("contactno") contactno: String?,
        @Part adharimg: MultipartBody.Part): Response<Unit>

    @Multipart
    @PUT("/user/kycadpan/pan/{contactno}")
    suspend fun kycpanimg(
        @Path("contactno") contactno: String?,
        @Part panimg: MultipartBody.Part): Response<Unit>



}