package com.jrexl.novanector.dataclass

import androidx.compose.ui.graphics.vector.ImageVector
import com.google.gson.annotations.SerializedName

data class Cardinfo(val name: String, val coins: Int, val cardNumber: String)

data class advertiseHomeImage(     @SerializedName("icons") val images: String? = null
)