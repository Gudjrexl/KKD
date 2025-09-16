package com.jrexl.novanector.secondscreen

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import com.jrexl.novanector.R
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jrexl.novanector.modallView.TransactionModelView
import com.jrexl.novanector.objectBuild.DataStoreManager
import com.jrexl.novanector.objectBuild.SmallData
import com.jrexl.novanector.thirdscreen.Cameraqr
import java.text.DecimalFormat



@Composable
 fun transactioncompose(vm: TransactionModelView = viewModel(),     onBack: () -> Unit
) {
    val transactions by vm.transactionsList.collectAsState()
    var contactcno: String? by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    var coin by remember { mutableStateOf(1) }
    coin = SmallData.coinCount
    val formattedCoin = DecimalFormat("#,###").format(coin)

    LaunchedEffect(Unit) {
        contactcno = DataStoreManager.getUserPhone(context)
        vm.transactionhistorymodelfunc(contactcno)

    }
    Column(modifier = Modifier.fillMaxWidth().fillMaxHeight().background(
        brush = Brush.verticalGradient(
            colors = listOf(
                Color(0xFFB3E5FC),
                Color.White
            )
        )
    )){

        Row(modifier = Modifier.statusBarsPadding().padding(start = 4.dp)) { IconButton(
            onClick = {
onBack()            },
        ){
            Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Back",
            tint = Color.Black,
            modifier = Modifier.size(25.dp)
        ) }
            Text(text = "Transaction History", fontSize = 20.sp, modifier = Modifier.padding(start = 1.dp, top = 13.dp), color = Color.Black)
        }
        androidx.compose.material3.Text(
            text = formattedCoin,
            fontSize = 30.sp,
            color = Color.Black,
            modifier = Modifier.padding(8.dp).align(Alignment.CenterHorizontally)
        )
        androidx.compose.material3.Text(
            text = "Available Balance",
            fontSize = 20.sp,
            color = Color.Black,
            fontStyle = FontStyle.Normal,
            modifier = Modifier.padding(8.dp).align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(20.dp))
        LazyColumn {
            items(transactions){
tra->  Column(modifier = Modifier
                .padding(8.dp)
                ){
                Row(
                    modifier = Modifier.fillMaxWidth().padding(start = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ){

    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(id = R.drawable.paintimg),
            contentDescription = "Paint",
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(3.dp))
        Text(text = tra.itemexp, fontSize = 15.sp, color = Color.Black)
        Spacer(modifier= Modifier.weight(1f))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.dollar),
                contentDescription = "Coin",
                modifier = Modifier.size(16.dp)  // Adjust size as needed
            )
            Spacer(modifier = Modifier.width(4.dp))

            Text(
                text = if (tra.typetransaction == "deduct") "-${tra.coins}" else "+${tra.coins}",
                fontSize = 15.sp,
                color = if (tra.typetransaction == "deduct") Color.Red else Color(0xFF008000),
                modifier = Modifier.padding(end = 8.dp)
            )
        }

    }

            }}
            }
        }






    }

}

@Composable
@Preview(showBackground = true)
fun transactioncomposePreview() {
    transactioncompose(onBack = {})
}
