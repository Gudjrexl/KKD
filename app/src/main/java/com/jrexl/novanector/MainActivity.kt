package com.jrexl.novanector

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.activity.compose.LocalActivity

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.jrexl.novanector.objectBuild.DataStoreManager
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
          SplashPage()
        }
    }
}

@Composable
fun SplashPage()  {
    val activity = LocalActivity.current
val context = LocalContext.current

    LaunchedEffect(Unit) {
        val phone = DataStoreManager.getUserPhone(context)
        delay(3000L)
        if (!phone.isNullOrEmpty()) {
            activity?.startActivity(Intent(activity, HomePage::class.java))
        } else {
            activity?.startActivity(Intent(activity, Signup::class.java))
        }
        activity?.finish()
    }
    Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(brush = Brush.verticalGradient(colors = listOf(Color(0xFFB3E5FC), // light blue at top
                    Color.White  ))),
        contentAlignment = Alignment.Center
    )
            {
                Image(
                    painter = painterResource(id = R.drawable.splashsvg),
                    contentDescription = "App Logo",
                    modifier = Modifier
                        .size(250.dp)
                        .padding(16.dp)
                )

        }
    }



@Preview(showBackground = true)
@Composable
fun SplashPagePreview() {
    SplashPage()
}