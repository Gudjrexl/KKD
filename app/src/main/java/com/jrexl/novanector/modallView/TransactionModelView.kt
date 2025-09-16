package com.jrexl.novanector.modallView

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.jrexl.novanector.dataclass.Withdrawal
import com.jrexl.novanector.dataclass.coinfeedback
import com.jrexl.novanector.dataclass.creditcoindata
import com.jrexl.novanector.dataclass.transactionhistory
import com.jrexl.novanector.objectBuild.Transactioncoinobject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class TransactionModelView: ViewModel() {
    fun withdrawal(withdrawal: Withdrawal){
        viewModelScope.launch {
            try {

                var withrawalcoinmodel = Transactioncoinobject.withsrawalapi.transactionCoin(withdrawal)
                if (withrawalcoinmodel.isSuccessful){

                }
            } catch (e: Exception){
                Log.e("API", "Error: ${e.message}")
            }
        }
    }

    private val _coinFeedback = MutableStateFlow<coinfeedback?>(null)
    val coinFeedback: StateFlow<coinfeedback?> = _coinFeedback

    fun credit(
        creditcoindata: creditcoindata,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val response = Transactioncoinobject.withsrawalapi.transactionCoinc(creditcoindata)
                if (response.isSuccessful) {
                    _coinFeedback.value = response.body()
                    onSuccess()
                } else {
                    val errorBody = response.errorBody()?.string() ?: "Unknown error"
                    try {
                        val feedback = Gson().fromJson(errorBody, coinfeedback::class.java)
                        _coinFeedback.value = feedback
                    } catch (e: Exception) {
                        Log.e("CREDIT", "Failed to parse error body: $errorBody")
                    }
                    onFailure(errorBody)
                    Log.e("CREDIT", "Credit failed: $errorBody")
                }
            } catch (e: Exception) {
                Log.e("API", "Error: ${e.message}")
                onFailure("Network error: ${e.message}")
            }
        }
    }



    private val _transactionsList = MutableStateFlow<List<transactionhistory>>(emptyList())
    val transactionsList: StateFlow<List<transactionhistory>> = _transactionsList

    fun transactionhistorymodelfunc(contactcno: String?){
        viewModelScope.launch {
            try {
                var transactionhistorymodel = Transactioncoinobject.withsrawalapi.getTransactions(contactcno)
                _transactionsList.value = transactionhistorymodel
            }catch (e: Exception){
        Log.e("API", "Error: ${e.message}")}
        }

}

}