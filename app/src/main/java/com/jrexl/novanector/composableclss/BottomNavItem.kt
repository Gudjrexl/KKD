package com.jrexl.novanector.composableclss

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.QrCode
import com.jrexl.novanector.R

sealed class BottomNavItem (val route: String, val icon: Int, val label: String){

    object Home : BottomNavItem("home", R.drawable.homeicon, "Home")
    object Product : BottomNavItem("product/All", R.drawable.producticon, "Product")
    object QRScan : BottomNavItem("qrscan", R.drawable.scannericon, "Scan")
    object Withdrawal : BottomNavItem("withdrawal", R.drawable.withdrawalicon, "Wallet")
    object Profile : BottomNavItem("profile", R.drawable.profileicon, "Profile")

}