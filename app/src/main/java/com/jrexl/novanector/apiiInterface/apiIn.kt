package com.jrexl.novanector.apiiInterface

import com.jrexl.novanector.dataclass.LoginData
import com.jrexl.novanector.dataclass.LoginResponse
import com.jrexl.novanector.dataclass.ServerResponse
import com.jrexl.novanector.dataclass.UserSignUp
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("/user/signup")
    suspend fun registerUser(@Body user: UserSignUp): Response<ServerResponse>

    @POST("/user/login")
    suspend fun loginUser(@Body user: LoginData): Response<LoginResponse>
}
