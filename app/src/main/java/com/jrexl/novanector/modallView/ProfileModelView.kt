package com.jrexl.novanector.modallView

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jrexl.novanector.dataclass.AdharPanDetails
import com.jrexl.novanector.dataclass.ProfileDataClass
import com.jrexl.novanector.objectBuild.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File


class ProfileModelView : ViewModel()

{


    private val _adharPanDetails = MutableStateFlow<AdharPanDetails?>(null)
    val adharPanDetails: StateFlow<AdharPanDetails?> = _adharPanDetails

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun fetchAdharPan(contactno: String?) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getAdharPanDetails(contactno)
                if (response.isSuccessful) {
                    _adharPanDetails.value = response.body()
                } else {
                    _errorMessage.value = "Error: ${response.code()} - ${response.message()}"
                }
            } catch (e: Exception) {
                Log.e("KycViewModel", "Error fetching Aadhar/PAN", e)
                _errorMessage.value = "Exception: ${e.localizedMessage}"
            }
        }
    }


    var profilevm by mutableStateOf<ProfileDataClass?>(null)
        private set


    @SuppressLint("SuspiciousIndentation")
    fun profiledatagetviewmode(contactno: String?) {
        viewModelScope.launch {
            try {
              val response = RetrofitInstance.api.Profileget(contactno)
                if (response.isSuccessful) {
                    profilevm = response.body()
                } else {
                    Log.e("API", "Error: ${response.code()} ${response.message()}")
                    profilevm = null
                }
            }catch (e : Exception){
                print(e)
            }
        }

    }

    fun profilePostdataviewmode(profileDataClass: ProfileDataClass) {
        viewModelScope.launch {
            try {
val resp = RetrofitInstance.api.ProfileUpdate(profileDataClass)
                if (resp.isSuccessful){
                    profiledatagetviewmode(profileDataClass.contactno)
                }
                }catch (e : Exception){
                print(e)
            }
        }
    }

    fun Updateprofilepic(context: Context, contactno: String?, imageFile: File ){
        val imagepart = MultipartBody.Part.createFormData(
            "image", imageFile.name, imageFile.asRequestBody("image/*".toMediaTypeOrNull())
        )
        viewModelScope.launch {
            try {
                val resp = RetrofitInstance.api.Profiepicupdate(contactno, imagepart)
                if (resp.isSuccessful){
                    profiledatagetviewmode(contactno)
                }
            }catch (e : Exception){
                print(e)
            }
        }



    }


    fun bankverifyimg(context: Context, contactno: String?, imageFile: File){
        val imagepart = MultipartBody.Part.createFormData(
            "imageFile", imageFile.name, imageFile.asRequestBody("image/*".toMediaTypeOrNull())
        )
        viewModelScope.launch {
            try {
                val resp = RetrofitInstance.api.bankverifyimg(contactno, imagepart)
                if (resp.isSuccessful){
                    Log.d("API", "Bank verification image posted successfully")
                }
            }catch (e: Exception){
        }
        }
    }

    fun bankdatapost(bankverificationdata: com.jrexl.novanector.dataclass.Bankverificationdata){
        viewModelScope.launch {
            try {
                val resp = RetrofitInstance.api.bankverificationdata(bankverificationdata)
                if (resp.isSuccessful){
                    Log.d("API", "Bank verification data posted successfully")
                }
            }catch (e: Exception){
                print(e)
            }

        }
    }

    fun kycadharimg(context: Context, contactno: String?, adharimgfile: File) {
        val imagepart = MultipartBody.Part.createFormData(
            "adharimg", adharimgfile.name, adharimgfile.asRequestBody("image/*".toMediaTypeOrNull())
        )
        viewModelScope.launch {
            try {
                val resp = RetrofitInstance.api.kycadharimg(contactno, imagepart)
                if (resp.isSuccessful) {
                    Log.d("API", "Adhar verification image posted successfully")
                }
            } catch (e: Exception) {
            }
        }
    }
fun kycpanimg(context: Context, contactno: String?, imageFile: File) {
    val imagepart = MultipartBody.Part.createFormData(
        "panimg", imageFile.name, imageFile.asRequestBody("image/*".toMediaTypeOrNull())
    )
    viewModelScope.launch {
        try {
            val resp = RetrofitInstance.api.kycpanimg(contactno, imagepart)
            if (resp.isSuccessful) {
                Log.d("API", "Pan verification image posted successfully")
            }
        } catch (e: Exception) {

    }
}


}

    fun Kydata( kycadpan: com.jrexl.novanector.dataclass.kycadpan){
        viewModelScope.launch {
            try {
                val resp = RetrofitInstance.api.kycadpan(kycadpan)
                if (resp.isSuccessful) {
                    Log.d("API", "KYC data posted successfully")
                }
            } catch (e: Exception) {

        }
    }
}}