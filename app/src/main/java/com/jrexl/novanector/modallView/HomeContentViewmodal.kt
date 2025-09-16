package com.jrexl.novanector.modallView

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jrexl.novanector.dataclass.Cardinfo
import com.jrexl.novanector.dataclass.advertiseHomeImage
import com.jrexl.novanector.objectBuild.HomeContentApi
import com.jrexl.novanector.objectBuild.RetrofitClient
import com.jrexl.novanector.screen.HomeContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.State

import kotlinx.coroutines.launch

class HomeContentViewmodal : ViewModel() {
    var cardInfo by mutableStateOf<Cardinfo?>(null)
        private set
    var isLoading by mutableStateOf(true)
        private set
    private val _images = mutableStateOf<List<advertiseHomeImage>>(emptyList())
    val images: State<List<advertiseHomeImage>> = _images



    fun fetchCardInfo(phone: String){
        viewModelScope.launch {
            try {
                isLoading = true
                val infoResult = HomeContentApi.HCapi.getCardInfo(phone)
                cardInfo = infoResult
            }catch (e : Exception){
                e.printStackTrace()
            }
            finally {
                isLoading = false
            }

        }
    }




    fun fetchImages() {
        viewModelScope.launch {
            try {
                val result = HomeContentApi.HCapi.getImageAdvertisement()
                _images.value = result

            } catch (e: Exception) {
                Log.e("ImageFetch", "Error: ${e.message}")
            }
        }
    }




}