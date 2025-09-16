package com.jrexl.novanector.screen

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.HeadsetMic
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.jrexl.novanector.R
import com.jrexl.novanector.modallView.HomeContentViewmodal
import com.jrexl.novanector.modallView.Producrviewmodel
import com.jrexl.novanector.objectBuild.Constf
import com.jrexl.novanector.objectBuild.DataStoreManager.getUserPhone
import com.jrexl.novanector.objectBuild.SmallData
import com.jrexl.novanector.secondscreen.offeractivity
import com.jrexl.novanector.secondscreen.transactioncompose
import kotlinx.coroutines.delay


@SuppressLint("SuspiciousIndentation")
@Composable
 fun HomeContent(navController: NavController) {
val vm: HomeContentViewmodal = viewModel()
    val vmp: Producrviewmodel = viewModel()

    var searchText by remember { mutableStateOf("") }
    val context = LocalContext.current
    var showTransactionScreen by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }

    if (showTransactionScreen) {
        transactioncompose(
            onBack = { showTransactionScreen = false }
        )
        return
    }

    val systemUiController = rememberSystemUiController()
        LaunchedEffect(Unit) {
            systemUiController.setStatusBarColor(Color.Transparent, darkIcons = true)
            vmp.categoryviewmodel()
            vm.fetchImages()
            val phone = getUserPhone(context as Activity)
            phone?.let {
                vm.fetchCardInfo(it)
            }
            Toast.makeText(context, phone, Toast.LENGTH_LONG).show()
        }

    val homeRowItems = vmp.category

if (showDialog){


    AlertDialog(
        onDismissRequest = { showDialog = false },
        title = {
            Text(text = "Kkd Company details", fontWeight = FontWeight.Medium, fontSize = 18.sp)
        },
        text = {
            Column {
                Text(text = "companyDescription")
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Contact: info@novanectar.com")
                Text(text = "Phone: +91-9876543210")
            }
        },
        confirmButton = {
            TextButton(onClick = { showDialog = false }) {
                Text("Close")
            }
        }
    )
}
    Column(modifier = Modifier
        .fillMaxSize()
        .background(brush = Brush.verticalGradient(colors = listOf(Color(0xFFB3E5FC),
            Color.White  )))
    ) {

        Row(modifier = Modifier.fillMaxWidth().statusBarsPadding().padding(start = 16.dp,top= 8.dp)) {
            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                placeholder = { Text("Search...",  fontSize = 11.sp, modifier = Modifier.offset(x= -8.dp,y= -4.dp)) },

                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    disabledContainerColor = Color.White,
                    cursorColor = MaterialTheme.colorScheme.primary,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                singleLine = true,
                shape = RoundedCornerShape(60.dp),
                modifier = Modifier.weight(10f)
                    .height(48.dp),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null
                    )
                })
            Spacer(modifier = Modifier.size(20.dp))
            Box(
                modifier = Modifier
                    .padding(end = 16.dp)
                    .height(48.dp)
                    .width(48.dp)
                    .background(Color.White, shape = CircleShape)
                    ,
            ) {
                IconButton(
                    onClick = { showDialog = true  },
                    modifier = Modifier

                        .height(48.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.HeadsetMic,
                        contentDescription = "Support"
                    )
                }
            }
        }

        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
        Spacer(modifier = Modifier.height(16.dp))
        vm.cardInfo.let { info ->
            KKDCard(
                userName = info?.name,
                coin = info?.coins,
                cardno = info?.cardNumber,
                onViewDetailsClick = {
                    showTransactionScreen = true
                }
            )
        }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                homeRowItems.forEach{
                        item ->
                    Column(horizontalAlignment = Alignment.CenterHorizontally){
                        AsyncImage(
                            model = Constf.BASE_URL + item.ctimg,
                            contentDescription = item.name,
                            modifier = Modifier
                                .size(64.dp)
                                .clip(CircleShape).clickable(onClick = {
                                    navController.navigate("product/${item.name}")


                                }),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = item.name ?: "",
                            style = MaterialTheme.typography.labelMedium,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            val imageList = vm.images.value
        val baseUrl = "${Constf.BASE_URL}/uploads/"
            ImageSlider(imageList = imageList.map { it.images ?: "" },
                baseUrl = baseUrl)
            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(start = 16.dp, end = 16.dp)
                    .background(
                        Brush.horizontalGradient(
                            listOf(
                                Color(0xFFE2FAFF),
                                Color(0xFFD3EB9A)
                            )
                        )
                    )
                    .padding(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxSize()
                ) {


                    Spacer(modifier = Modifier.width(12.dp))

                    Column(
                        modifier = Modifier
                            .weight(2f)
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = "We’ve Got a Special Gift Just for You",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.Black
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "To thank you for being with us, here’s a surprise waiting to be unwrapped.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.DarkGray
                        )

                        Spacer(modifier = Modifier.height(8.dp))
Row {    Text(
    text = "Redeem Now",
    fontSize = 17.sp,
    style = MaterialTheme.typography.labelSmall.copy(
        textDecoration = TextDecoration.Underline
    ),    color = Color.Black, modifier = Modifier.clickable(onClick = {
        val intet2 = Intent(context, offeractivity::class.java)
        context.startActivity(intet2)
    })
)



}

                    }
                    Image(
                        painter = painterResource(id = R.drawable.offer),
                        contentDescription = "Left Image",
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
        }
    }

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ImageSlider(imageList: List<String>, baseUrl: String) {
    if (imageList.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(14.dp)
        ) {
            Text("No images available", modifier = Modifier.align(Alignment.Center))
        }
        return
    }

    val pagerState = rememberPagerState()

    if (imageList.size > 1) {
        LaunchedEffect(pagerState) {
            while (true) {
                delay(3000)
                val nextPage = (pagerState.currentPage + 1) % imageList.size
                pagerState.animateScrollToPage(nextPage)
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
            .padding(start = 16.dp, end = 16.dp)
    ) {
        HorizontalPager(
            count = imageList.size,
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            val imageUrl = baseUrl + (imageList[page])
            AsyncImage(
                model = imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(12.dp))
            )
        }

        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset(y= -10.dp)
                .padding(8.dp),
        )
    }
}



@Composable
fun KKDCard(userName: String?, coin: Int?, cardno: String?,     onViewDetailsClick: () -> Unit
) {
val context = LocalContext.current
    if (coin != null) {
        SmallData.coinCount = coin
    }
    Box(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFFE4C1F9),
                        Color(0xFFA9DEF9),
                        Color(0xFFFFD6A5)
                    )
                )
            )
            .fillMaxWidth()
            .height(180.dp)
    ){
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxSize()
        ){
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(
                    text = "TOTAL COINS",
                    style = MaterialTheme.typography.labelMedium.copy(fontSize = 12.sp),
                    color = Color.Black
                )
                Text(
                    text = "KKD Card",
                    style = MaterialTheme.typography.labelMedium.copy(fontSize = 12.sp),
                    color = Color.Black
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            Row(modifier = Modifier.padding(top = 6.dp)){
//                Icon(
//                    imageVector = Icons.Default.MonetizationOn,
//                    contentDescription = "Coin Icon",
//                    tint = Color(0xFFFFC107),
//                    modifier = Modifier.size(28.dp).offset(x = -6.dp).height(4.dp)
//                )
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(R.raw.dollsvg)
                        .decoderFactory(SvgDecoder.Factory())
                        .build(),
                    contentDescription = "Doll SVG",
                    modifier = Modifier.size(24.dp).offset(y = 4.dp) // keep fixed size
                )

                Spacer(modifier = Modifier.width(6.dp)) // spacing between icon and text

                Text(
                    text = coin?.toString() ?: "00",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )


            }
            Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = cardno?.chunked(4)?.joinToString(" ") ?:"XXXX  XXXX  XXXX  XXXX",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            Spacer(modifier = Modifier.height(1.dp))
                Text(
                    text = userName?:"KKD user",
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.Black
                )
            Spacer(modifier = Modifier.weight(2f))

            Text(
                text = "View Details",
                style = MaterialTheme.typography.labelSmall.copy(textDecoration = TextDecoration.Underline),
                color = Color.DarkGray,
                modifier = Modifier
                .clickable {
                    onViewDetailsClick()

            }

            )
        }
    }
}

