package com.jrexl.novanector.thirdscreen

import android.annotation.SuppressLint
import android.app.Activity
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.jrexl.novanector.dataclass.kycadpan
import com.jrexl.novanector.modallView.ProfileModelView
import com.jrexl.novanector.objectBuild.DataStoreManager
import com.jrexl.novanector.objectBuild.DataStoreManager.getUserPhone

class Kycverification : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            adhapankyc()

        }
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun adhapankyc(vmk: ProfileModelView = viewModel(),vm: ProfileModelView = viewModel()) {
    val ScrolleState = rememberScrollState()
    var adharno by remember { mutableStateOf("") }
    var Panno by remember { mutableStateOf("") }
    val context = LocalContext.current
    var showimg by remember { mutableStateOf(false) }
    var contactno: String? by remember { mutableStateOf("") }
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(Color.Transparent, darkIcons = true)
    systemUiController.setNavigationBarColor(Color.White, darkIcons = true)

    LaunchedEffect(Unit) {
         contactno = DataStoreManager.getUserPhone(context)
vmk.profilevm
        vmk.fetchAdharPan(contactno)

    }
    var a = vmk.adharPanDetails.value?.adharno
    var b = vmk.adharPanDetails.value?.Panno



    Column(modifier = Modifier.fillMaxWidth().fillMaxSize().verticalScroll(ScrolleState).background(brush = Brush.verticalGradient(colors = listOf(Color(0xFFB3E5FC),Color.White)))) {
        Row( modifier = Modifier.statusBarsPadding().padding(start = 4.dp, top = 8.dp) ) {
            IconButton(
                onClick = {
                    (context as? Activity)?.finish()
                },
            ){
                Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.Black,
                modifier = Modifier.size(20.dp)
            ) }
            androidx.compose.material3.Text(
                text = "Know Your Customer",
                fontSize = 20.sp,
                color = Color.Black,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(start = 0.1.dp, top = 12.dp)
            )
        }
        Divider(color = Color.Gray, thickness = 0.8.dp, modifier = Modifier.padding(vertical = 8.dp))

        Spacer(modifier = Modifier.height(15.dp))





        androidx.compose.material3.Text(
            text = "Enter Adhar No", fontWeight = FontWeight.Medium,
            modifier = Modifier
                .padding(start = 18.dp),
        )
        Spacer(modifier = Modifier.height(4.dp))
        var isError by remember { mutableStateOf(false) }

        androidx.compose.material3.OutlinedTextField(
            value = adharno?: "",
            onValueChange = { input ->
                if (input.length <= 12 && input.all { it.isDigit() }) {
                    adharno = input
                    isError = input.length != 12 && input.isNotEmpty()
                }
            },
            placeholder = {
                if ( a!= null){
                    Text(a,
                        fontSize = 11.sp,
                    )
                }
                else{

                    androidx.compose.material.Text(
                        "Enter Adhar No",
                        fontSize = 11.sp,
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth().padding(start = 16.dp, end = 16.dp)
                .height(48.dp),

            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White,
                disabledContainerColor = Color.White
            ),
            singleLine = true
        )





        Spacer(modifier = Modifier.height(15.dp))





        androidx.compose.material3.Text(
            text = "Enter Pan No.", fontWeight = FontWeight.Medium,
            modifier = Modifier
                .padding(start = 18.dp),
        )
        Spacer(modifier = Modifier.height(4.dp))

        androidx.compose.material3.OutlinedTextField(
            value = Panno?: "",
            onValueChange = { Panno = it },

            placeholder = {
                if ( b!= null){
                    Text(b,
                        fontSize = 11.sp,
                    )
                }
                else{

                    androidx.compose.material.Text(
                        "Enter Pan No",
                        fontSize = 11.sp,
                    )
                }

            },
            modifier = Modifier
                .fillMaxWidth().padding(start = 16.dp, end = 16.dp)
                .height(48.dp),

            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White,
                disabledContainerColor = Color.White
            ),
            singleLine = true
        )


        Spacer(modifier = Modifier.height(15.dp))





        Button(onClick = {
            var detailskyc = kycadpan(
                adharno = adharno,
                Panno = Panno,
                contactno = contactno,
                adharimg = "",
                panimg = ""
            )

            vm.Kydata(detailskyc)
            showimg = true

        },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Black,
                contentColor = Color.White
            ),
            modifier = Modifier.align(Alignment.CenterHorizontally).padding(start = 10.dp, end= 10.dp).fillMaxWidth()) { Text(text = "Submit")}
        if (showimg){
            AdharImage()
            PanImage()
Button( modifier = Modifier.align(Alignment.CenterHorizontally).fillMaxWidth(),onClick = {
    val intent = android.content.Intent(context, com.jrexl.novanector.HomePage::class.java)
    context.startActivity(intent)
},

    colors = ButtonDefaults.buttonColors(
        backgroundColor = Color.Black,
        contentColor = Color.White
    ),
) {Text(text = "Submit adhar/pan image") }
        }
        Spacer(modifier = Modifier.height(16.dp))

    }

}

@Composable
fun PanImage(vm: ProfileModelView = viewModel()) {
    var selectedurl by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    val contactno = remember { mutableStateOf<String?>(null) }
    LaunchedEffect(Unit) {
        contactno.value = getUserPhone(context)
    }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedurl = uri

        if (uri != null && contactno.value != null) {
            val file = uriToFiles(context, uri)
            vm.kycpanimg(
                context = context,
                contactno = contactno.value!!,
                imageFile = file
            )
        }
    }
    Column(horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center, modifier = Modifier.padding(16.dp).fillMaxWidth()) {

        Box(modifier = Modifier.size(150.dp).clip(RectangleShape).background(Color.Cyan)
            .clickable{ launcher.launch("image/*") },
            contentAlignment = Alignment.Center
        ){
            if (selectedurl != null){
                Image(painter = rememberAsyncImagePainter(model = selectedurl),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(150.dp))

            }
            else{
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(150.dp)
                )
            }

        }
        Spacer(modifier = Modifier.height(8.dp))
        androidx.compose.material3.Text(
            text = "Upload Pan image",
            fontSize = 14.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )

    }}

@Composable
fun AdharImage(vm: ProfileModelView = viewModel()) {
    var selectedurl by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    val contactno = remember { mutableStateOf<String?>(null) }
    LaunchedEffect(Unit) {
        contactno.value = getUserPhone(context)
    }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedurl = uri

        if (uri != null && contactno.value != null) {
            val file = uriToFiles(context, uri)
            vm.kycadharimg(
                context = context,
                contactno = contactno.value!!,
                adharimgfile = file
            )
        }
    }
    Column(horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center, modifier = Modifier.padding(16.dp).fillMaxWidth()) {

        Box(modifier = Modifier.size(150.dp).clip(RectangleShape).background(Color.Cyan)
            .clickable{ launcher.launch("image/*") },
            contentAlignment = Alignment.Center
        ){
            if (selectedurl != null){
                Image(painter = rememberAsyncImagePainter(model = selectedurl),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(150.dp))

            }
            else{
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(150.dp)
                )
            }

        }
        Spacer(modifier = Modifier.height(8.dp))
        androidx.compose.material3.Text(
            text = "Upload Aadhar image",
            fontSize = 14.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )

    }}

@Composable
@Preview(showBackground = true)
fun adhapankycPreview() {
    adhapankyc()
}
