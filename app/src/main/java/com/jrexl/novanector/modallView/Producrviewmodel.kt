package com.jrexl.novanector.modallView

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jrexl.novanector.dataclass.Filtercat
import com.jrexl.novanector.dataclass.OfferDetDatacls
import com.jrexl.novanector.dataclass.ProductDetDatacls
import com.jrexl.novanector.dataclass.categorydatclss
import com.jrexl.novanector.objectBuild.Productretro
import kotlinx.coroutines.launch

class Producrviewmodel: ViewModel() {
    var products by mutableStateOf<List<ProductDetDatacls>>(emptyList())
        private set


    private var isLoaded = false

    fun fetchproducr() {
        if (isLoaded) return

        viewModelScope.launch {
            try {
                products = Productretro.PrdtApi.GetProductDetl()
                isLoaded = true

            }
            catch (e: Exception){
                Log.e("ErrorFetching", e.toString())
            }

        } }

    var productfilter by mutableStateOf<List<ProductDetDatacls>>(emptyList())

    fun applyfilter(category:List<String>, price:List<Int>){
        viewModelScope.launch {
            try {
                val request = Filtercat(category, price)
                val res = Productretro.PrdtApi.filterdatainter(request)
                Log.d("FILTERED_DATA", res.body().toString())
                if (res.isSuccessful){
                    val result = res.body() ?: emptyList()
                    Log.d("FILTERED_DATA", "Received: ${result.size} items")
                    productfilter = result
                }
                else{
                    Log.e("Error", res.message())
                }
            }catch (e:Exception){
                Log.e("Exception", e.toString())
            }
        }
    }


    var offerprod by mutableStateOf<List<OfferDetDatacls>>(emptyList())
        private set


    private var isofferLoaded = false

    fun fetchofferproduct() {
        if (isofferLoaded) return

        viewModelScope.launch {
            try {
                offerprod = Productretro.PrdtApi.GetofferDetl()
                isofferLoaded = true

            }
            catch (e: Exception){
                Log.e("ErrorFetching", e.toString())
            }

        } }


    var category by mutableStateOf<List<categorydatclss>>(emptyList())
        private set
    fun categoryviewmodel(){
        viewModelScope.launch {
            try {
                category = Productretro.PrdtApi.GetCategory()
            }catch (e: Exception){
                Log.e("ErrorFetching", e.toString())}}
    }

}

