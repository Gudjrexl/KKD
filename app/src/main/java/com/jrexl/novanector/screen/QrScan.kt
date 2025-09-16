package com.jrexl.novanector.screen

import android.app.Activity
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.jrexl.novanector.thirdscreen.Cameraqr

@Composable
fun QRScanScreen() {
    val context = LocalContext.current
    LaunchedEffect(Unit){
        val intent = Intent(context, Cameraqr::class.java)
        context.startActivity(intent)
        (context as? Activity)?.finish()
    }
}






