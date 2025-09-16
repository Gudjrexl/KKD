package com.jrexl.novanector.thirdscreen

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.jrexl.novanector.HomePage

class Offerredeem : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val offerprice = intent.getStringExtra("offerprice")
        val offername = intent.getStringExtra("offername")
        val offerdescription = intent.getStringExtra("offerdescription")
        val imageUrl = intent.getStringExtra("hardcodedImageUrl")

        setContent {
            reedeemscreen(offerprice,offername,offerdescription,imageUrl)
        }
    }
}
@Composable
 fun reedeemscreen(
    offerprice: String?,
    offername: String?,
    offerdescription: String?,
    imageUrl: String?
) {
     val context = LocalContext.current

    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(Color.Transparent, darkIcons = true)
    systemUiController.setNavigationBarColor(Color.White, darkIcons = true)
    Column(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.White)
        .padding(16.dp)
    ){
        Row { IconButton(
            onClick = {
                (context as? Activity)?.finish()
            },
        ){Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Back",
            tint = Color.Black,
            modifier = Modifier.size(30.dp)
        ) }
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Product Details", fontSize = 20.sp, modifier = Modifier.padding(13.dp))
        }

        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(imageUrl)
                .crossfade(true)
                .diskCachePolicy(CachePolicy.ENABLED)
                .memoryCachePolicy(CachePolicy.ENABLED)
                .build(),
            contentDescription = "Product Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)))
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "$offername", fontSize = 20.sp, modifier = Modifier.padding(5.dp))
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "$offerdescription", fontSize = 16.sp, modifier = Modifier.padding(5.dp))
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Purchasing Points: $offerprice", fontSize = 16.sp, modifier = Modifier.padding(5.dp))
Spacer(modifier = Modifier.height(100.dp))

        Button(onClick = {
            val intent = Intent(context, HomePage::class.java)
            context.startActivity(intent)
        }, modifier = Modifier.fillMaxWidth()) {
            Text("Redeem")
        }




    }
}

@Composable
@Preview(showBackground = true)
fun reedeemscrrenPreview(){
    reedeemscreen("500", "offername", "offerdescription", "mageUrl")
}
