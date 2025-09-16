package com.jrexl.novanector.modallView

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jrexl.novanector.dataclass.LoginData
import com.jrexl.novanector.dataclass.UserSignUp
import com.jrexl.novanector.objectBuild.DataStoreManager
import com.jrexl.novanector.objectBuild.RetrofitClient
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {
    fun registerUser(context: Context, user: UserSignUp, onResult: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.Rapi.registerUser(user)
                if (response.isSuccessful) {
                    val body = response.body()
                    body?.user.let {
                        DataStoreManager.saveUser(context, it?.name ?: "", it?.phone ?: "",
                            it?.email ?: "",  ""
                        )
                    }
                    onResult(response.body()?.message ?: "Registered")
                } else {
                    onResult(response.errorBody()?.string() ?: "Error occurred")
                }
            } catch (e: Exception) {
                onResult("Network error: ${e.message}")
            }
        }
    }

    fun loginuser(context:Context,loginData: LoginData, onResult: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.Rapi.loginUser(loginData)

                if (response.isSuccessful) {
                    val body = response.body()
                    body?.user.let {
                        DataStoreManager.saveUser(context, it?.name ?: "", it?.phone ?: "",
                            it?.email ?: "", ""
                        )
                    }
                    onResult(body?.message ?: "Login successful")
                } else {
                    val error = response.errorBody()?.string() ?: "Login failed"
                    onResult(error)
                }

        }catch (
            e: Exception){
            onResult("Network error: ${e.message}")
        }
        }

    }
}
