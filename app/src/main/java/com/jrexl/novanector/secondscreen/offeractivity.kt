package com.jrexl.novanector.secondscreen

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.jrexl.novanector.R
import com.jrexl.novanector.dataclass.OfferDetDatacls
import com.jrexl.novanector.modallView.Producrviewmodel
import com.jrexl.novanector.objectBuild.Constf
import com.jrexl.novanector.screen.ProductCard
import com.jrexl.novanector.thirdscreen.Offerredeem
import kotlin.collections.chunked
import kotlin.collections.ifEmpty

class offeractivity : ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OfferScreen()
        }
    }
}
@Composable
fun OfferScreen(vm: Producrviewmodel = viewModel()){
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        vm.fetchofferproduct()
    }
    Column(modifier = Modifier
        .fillMaxSize()
        .background(
            brush = Brush.verticalGradient(
                colors = listOf(
                    Color(0xFFB3E5FC),
                    Color.White
                )
            )
        )
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
            Text(text = "Offers", fontSize = 20.sp, modifier = Modifier.padding(13.dp))
        }


        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(1.dp),
            verticalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            val productList = vm.offerprod
            items(productList.chunked(2)) { ritm ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        Offercard(ritm[0])
                    }

                    if (ritm.size > 1) {
                        Box(modifier = Modifier.weight(1f)) {
                            Offercard(ritm[1])
                        }
                    } else {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }


    }
}

@Composable
fun Offercard(product: OfferDetDatacls) {
    val hardcodedImageUrl = (Constf.BASE_URL + product.Offerimg) ?: ""
    var offerprice = product.offerprice.toString()
    var offername = product.offername
    var offerdescription = product.offerdescription

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(230.dp)
            .padding(4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            val context = LocalContext.current

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(170.dp)
            ) {
                // Product Image
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(hardcodedImageUrl)
                        .crossfade(true)
                        .diskCachePolicy(CachePolicy.ENABLED)
                        .memoryCachePolicy(CachePolicy.ENABLED)
                        .build(),
                    contentDescription = "Product Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxSize()
                        .clip(RoundedCornerShape(10.dp))
                        .clickable{
                            val intent6 = Intent(context, Offerredeem::class.java).apply {
                                putExtra("offerprice", offerprice)
                                putExtra("offername", offername)
                                putExtra("offerdescription", offerdescription)
                                putExtra("hardcodedImageUrl", hardcodedImageUrl)
                            }
                            context.startActivity(intent6)
                        }

                )



            }

            Spacer(modifier = Modifier.height(4.dp))

            androidx.compose.material3.Text(
                text = product.offername,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(4.dp)
            )


        }
    }

}

@Composable
@Preview(showBackground = true)
fun OfferScreenPreview(){
    OfferScreen()
}