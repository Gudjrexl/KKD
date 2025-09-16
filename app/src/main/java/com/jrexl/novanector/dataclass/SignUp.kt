package com.jrexl.novanector.dataclass

data class UserSignUp(
    val name: String,
    val phone: String,
    val email: String,
    val password: String,
    val confirmpassword: String
)
data class ServerResponse(
    val message: String?,
    val error: String?,
    val user: UserData?

)
data class LoginData(
    val uniqueid: String,
    val password: String,

    )

data class LoginResponse(
    val message: String,
    val user: UserData?
)
data class UserData(
    val name: String,
    val email: String,
    val phone: String
)