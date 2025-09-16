package com.jrexl.novanector.screen

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.jrexl.novanector.R

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.jrexl.novanector.dataclass.Withdrawal
import com.jrexl.novanector.modallView.TransactionModelView
import com.jrexl.novanector.objectBuild.DataStoreManager
import com.jrexl.novanector.objectBuild.SmallData
import com.jrexl.novanector.secondscreen.transactioncompose
import kotlinx.coroutines.delay
import java.nio.file.WatchEvent
import java.text.DecimalFormat

@Composable
fun WithdrawalScreen(vm: TransactionModelView = androidx.lifecycle.viewmodel.compose.viewModel()) {
    var coin by remember { mutableStateOf(1) }
    coin = SmallData.coinCount
    var amountmin by remember { mutableStateOf("") }
    val formattedCoin = DecimalFormat("#,###").format(coin)
    val context = androidx.compose.ui.platform.LocalContext.current
    var contactcno: String? by remember { mutableStateOf("") }
    LaunchedEffect(Unit) {
        contactcno = DataStoreManager.getUserPhone(context)
    }
var amountcoin by remember { mutableStateOf("") }
    var showTransactionScreen by remember { mutableStateOf(false) }

    if (showTransactionScreen) {
        transactioncompose(
            onBack = { showTransactionScreen = false }
        )    }

    else{


    Column(modifier = Modifier.fillMaxSize().fillMaxWidth().background(brush = Brush.linearGradient(colors = listOf(Color(0xFFB3E5FC), Color.White)))){
        Row(modifier = Modifier.statusBarsPadding().padding(start = 16.dp, top = 8.dp, end =  16.dp)){
            Text(text = "Withdraw", fontSize = 20.sp, color = Color.Black, modifier = Modifier.padding(8.dp) )
            Spacer(modifier = Modifier.weight(1f))
//            Image(painter = painterResource(R.drawable.framewithrawal), contentDescription = "image",
//                modifier = Modifier.padding(end = 8.dp).size(40.dp).clickable(onClick = {
//                showTransactionScreen = true
//            }),
//              contentScale = ContentScale.Crop,  )
            Box(
                modifier = Modifier
                    .padding(end = 16.dp)
                    .height(48.dp)
                    .width(48.dp)
                    .background(Color.White, shape = CircleShape)
                ,
            ) {
                IconButton(
                    onClick = {
                        showTransactionScreen = true
                    },
                    modifier = Modifier

                        .height(48.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.withdrl),
                        contentDescription = "Support Image",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

        }
Spacer(modifier = Modifier.height(10.dp))
        Text(text = formattedCoin, fontSize = 30.sp, color = Color.Black, modifier = Modifier.padding(8.dp).align(androidx.compose.ui.Alignment.CenterHorizontally))
        Text(text = "Available Balance", fontSize = 20.sp, color = Color.Black, fontStyle = FontStyle.Normal, modifier = Modifier.padding(8.dp).align(androidx.compose.ui.Alignment.CenterHorizontally))
Spacer(modifier = Modifier.height(20.dp))
        Text(text = "Your withdrawal request is encrypted and secure.", modifier = Modifier.padding(8.dp).align(androidx.compose.ui.Alignment.CenterHorizontally))
        Box(modifier = Modifier.padding(16.dp).clip(RoundedCornerShape(16.dp)).background(brush = Brush.linearGradient(colors = listOf(Color(0xFFD1E7FD), Color(0xFFD7E4B7)))).fillMaxWidth().align(androidx.compose.ui.Alignment.CenterHorizontally)){
            Column(modifier = Modifier.padding(start= 10.dp, end = 10.dp)) {
                Text(text = "You can withdraw up to your available balance.", fontSize = 17.sp, color = Color.Black, modifier = Modifier.padding(8.dp))
                Text(text = "Amount Withdraw", fontSize = 14.sp, color = Color.Black, modifier = Modifier.padding(5.dp).offset(y = 12.dp))
                Spacer(modifier = Modifier.height(15.dp))
                OutlinedTextField(
                    value = amountcoin,
                    onValueChange = { amountcoin = it },
                    placeholder = { Text("Enter Amount") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(15.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        backgroundColor = Color.White
                    )
                )


                Text(text = "Min: 15,000", modifier = Modifier.align(androidx.compose.ui.Alignment.End).padding(8.dp), color = Color.Black)

                Spacer(modifier = Modifier.height(4.dp))
Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceBetween
) {
    Button(onClick = { amountcoin = "" },colors = ButtonDefaults.buttonColors(
        backgroundColor = Color.Transparent,
        contentColor = Color.Blue,

        ),
        elevation = ButtonDefaults.elevation(0.dp), modifier = Modifier.padding(10.dp)
            .border(1.dp, Color.Black, shape = RoundedCornerShape(16.dp))
            .height(48.dp).weight(1f), shape = RoundedCornerShape(16.dp)) { Text(text = "Clean", fontSize = 15.sp) }
Spacer(modifier = Modifier.width(0.5.dp))
    Button(onClick = {
        var amountcoinnumber = amountcoin.toIntOrNull()?: 0
            if (coin >= amountcoinnumber && amountcoinnumber >= 15000){
                try {
                    var withdrwaalcoincom = Withdrawal(contactcno!!, amountcoinnumber)
                    vm.withdrawal(withdrwaalcoincom)
                    Toast.makeText(context, "Request Sent", Toast.LENGTH_SHORT).show()
                    amountcoin = ""
                }catch (e: NumberFormatException){
                    Toast.makeText(context, "Please enter a valid number", Toast.LENGTH_SHORT).show()
                    return@Button
                }
            }
            else{
                amountmin = "Coin must be greater than 15,000 and less than available balance."
            }


    },colors = ButtonDefaults.buttonColors(
        backgroundColor = Color.Black,
        contentColor = Color.White
    ),elevation = ButtonDefaults.elevation(0.dp),
        modifier = Modifier.padding(10.dp).height(48.dp).weight(1f), shape = RoundedCornerShape(16.dp)) { Text(text = "Request", fontSize = 15.sp, color = Color.White) }

}
            }
        }
        Text(text = amountmin, modifier = Modifier.align(androidx.compose.ui.Alignment.CenterHorizontally).padding(8.dp), color = Color.Red)
        LaunchedEffect(amountmin) {
            delay(5000)
            amountmin = ""
        }
        Spacer(modifier = Modifier.height(100.dp))
        Text(text = "Need help?", modifier = Modifier.padding(0.dp).align(androidx.compose.ui.Alignment.CenterHorizontally).clickable(onClick = {
        }))
        Text(text =  "Contact support at support@birlaopus.com", modifier = Modifier.padding(1.dp).align(
            androidx.compose.ui.Alignment.CenterHorizontally,
        ))
}}

}
@Composable
@Preview(showBackground = true)
fun WithdrawalScreenPreview() {
    WithdrawalScreen()
}