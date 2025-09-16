package com.jrexl.novanector.thirdscreen

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.jrexl.novanector.dataclass.ProfileDataClass
import com.jrexl.novanector.modallView.ProfileModelView
import com.jrexl.novanector.objectBuild.Constf
import com.jrexl.novanector.objectBuild.DataStoreManager
import java.io.File
import java.io.FileOutputStream

class PersonalDet : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
           Personalinformtion()
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun Personalinformtion(vm: ProfileModelView = viewModel()) {
    val context = LocalContext.current
    val ScrolleState = rememberScrollState()
    var contactno by remember { mutableStateOf<String?>("") }
    var gmailid by remember { mutableStateOf<String?>("") }
    var dob by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var Pincode by remember { mutableStateOf("") }
    var State by remember { mutableStateOf("") }
    var Country by remember { mutableStateOf("") }
    var uname by remember { mutableStateOf("") }
    var designation by remember { mutableStateOf("") }
    var ProfilePic by remember { mutableStateOf<String?>(null) }
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(Color.Transparent, darkIcons = true)
    systemUiController.setNavigationBarColor(Color.White, darkIcons = true)




    LaunchedEffect(Unit) {
        contactno = DataStoreManager.getUserPhone(context)
        gmailid = DataStoreManager.getUserEmail(context)

    }

    Column(modifier = Modifier.fillMaxSize().verticalScroll(ScrolleState).fillMaxWidth().background(Brush.verticalGradient(colors = listOf(Color(0xFFB3E5FC),Color.White)))) {

Row( modifier = Modifier.statusBarsPadding().padding(start = 4.dp, top = 8.dp) ) {

    IconButton(
        onClick = {
            (context as? Activity)?.finish()      },
    ){
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Back",
            tint = Color.Black,
            modifier = Modifier.size(20.dp)
        ) }

    Text(text = "Personal Detail",  fontSize = 20.sp, color = Color.Black,
        fontWeight = FontWeight.Medium, modifier = Modifier.padding(start = 0.1.dp, top = 12.dp))
}
        Divider(color = Color.Gray, thickness = 0.8.dp, modifier = Modifier.padding(vertical = 8.dp))
        ProfileImagePicker()
        Spacer(modifier = Modifier.height(15.dp))

        androidx.compose.material3.Text(
            text = "Name", fontWeight = FontWeight.Medium,
            modifier = Modifier
                .padding(start = 18.dp),
        )
        Spacer(modifier = Modifier.height(4.dp))
        androidx.compose.material3.OutlinedTextField(
            value = uname?: "",
            onValueChange = { uname = it},
            placeholder = {
                androidx.compose.material.Text(
                    "Enter Your Name",
                    fontSize = 11.sp,
                )
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
            text = "Contact Number", fontWeight = FontWeight.Medium,
            modifier = Modifier
                .padding(start = 18.dp),
        )
        Spacer(modifier = Modifier.height(4.dp))
        androidx.compose.material3.OutlinedTextField(
            value = contactno ?: "",
            onValueChange = { },
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
            text = "Email id", fontWeight = FontWeight.Medium,
            modifier = Modifier
                .padding(start = 18.dp),
        )
        Spacer(modifier = Modifier.height(4.dp))
        androidx.compose.material3.OutlinedTextField(
            value = gmailid ?: "",
            onValueChange = { },
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
            text = "Date of birth", fontWeight = FontWeight.Medium,
            modifier = Modifier
                .padding(start = 18.dp),
        )
        Spacer(modifier = Modifier.height(4.dp))
        androidx.compose.material3.OutlinedTextField(
            value = dob?: "", onValueChange = {dob = it},
            placeholder = {
                androidx.compose.material.Text(
                    "Date of birth",
                    fontSize = 11.sp,
                )
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
            text = "Permanent Address", fontWeight = FontWeight.Medium,
            modifier = Modifier
                .padding(start = 18.dp),
        )
        Spacer(modifier = Modifier.height(4.dp))
        androidx.compose.material3.OutlinedTextField(
            value = address?: "", onValueChange = {address = it},
            placeholder = {
                androidx.compose.material.Text(
                    "Permanent Address",
                    fontSize = 11.sp,
                )
            },
            modifier = Modifier
                .fillMaxWidth().padding(start = 16.dp, end = 16.dp)
                .height(150.dp),

            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White,
                disabledContainerColor = Color.White
            ),
        )


        Spacer(modifier = Modifier.height(15.dp))

        androidx.compose.material3.Text(
            text = "Pincode", fontWeight = FontWeight.Medium,
            modifier = Modifier
                .padding(start = 18.dp),
        )
        Spacer(modifier = Modifier.height(4.dp))
        androidx.compose.material3.OutlinedTextField(
            value = Pincode?: "", onValueChange = {Pincode= it},
            placeholder = {
                androidx.compose.material.Text(
                    "Pincode",
                    fontSize = 11.sp,
                )
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
            text = "State", fontWeight = FontWeight.Medium,
            modifier = Modifier
                .padding(start = 18.dp),
        )
        Spacer(modifier = Modifier.height(4.dp))
        androidx.compose.material3.OutlinedTextField(
            value = State?: "", onValueChange = {State= it},
            placeholder = {
                androidx.compose.material.Text(
                    "State",
                    fontSize = 11.sp,
                )
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
            text = "Country", fontWeight = FontWeight.Medium,
            modifier = Modifier
                .padding(start = 18.dp),
        )
        Spacer(modifier = Modifier.height(4.dp))
        androidx.compose.material3.OutlinedTextField(
            value = Country?: "", onValueChange = {Country= it},
            placeholder = {
                androidx.compose.material.Text(
                    "Country",
                    fontSize = 11.sp,
                )
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
        Spacer(modifier = Modifier.height(10.dp))








        Button(onClick = {

       val ProfileDataClass = ProfileDataClass(
uname = uname,
           designation = designation?:"",
           ProfilePic = ProfilePic ?: "",
           gmailid = gmailid ?: "",
           dob = dob,
           contactno = contactno ?: "",
           address = address,
           Pincode = Pincode.toInt(),
           State = State,
           Country = Country,
           PanCardVerify = "",
           AadharCardVerify = "",
           BankAccount = "",
           BankHolderName = "",
           BankName = "",
           ifsc = "",
       )

       vm.profilePostdataviewmode(ProfileDataClass)
       val intent = android.content.Intent(context, com.jrexl.novanector.HomePage::class.java)
       context.startActivity(intent)


   }, colors = ButtonColors(
containerColor = Color.Black,
       contentColor = Color.White,
       disabledContainerColor = Color.Black,
       disabledContentColor = Color.White
   ),

            modifier = Modifier.fillMaxWidth().padding(16.dp).size(40.dp)){
       Text(text = "Submit")
   }
        Spacer(modifier = Modifier.height(40.dp))


    }
}


@Composable
fun ProfileImagePicker(vm: ProfileModelView = viewModel()) {
    val context = LocalContext.current
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    val contactno = remember { mutableStateOf<String?>(null) }
    LaunchedEffect(Unit) {
        contactno.value = DataStoreManager.getUserPhone(context)
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri

        if (uri != null && contactno.value != null) {
            val file = uriToFile(context, uri)
            vm.Updateprofilepic(
                context = context,
                contactno = contactno.value!!,
                imageFile = file
            )
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(Color(0xFFF5F5F5))
                .clickable { launcher.launch("image/*") },
            contentAlignment = Alignment.Center
        ) {
            val imageUrl = "${Constf.BASE_URL.trimEnd('/')}/profilepic/${vm.profilevm?.ProfilePic.orEmpty()}"
if (imageUrl.isNotEmpty()){
    Log.d("ProfilePicURL", "Image URL = $imageUrl")
    AsyncImage(
        ImageRequest.Builder(context)
            .data(imageUrl)
            .crossfade(true)
            .diskCachePolicy(CachePolicy.ENABLED)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .build(),
        contentDescription = null,

        modifier = Modifier.padding(8.dp).clip(CircleShape).size(50.dp)
    )
}


            if (selectedImageUri != null) {
                Image(
                    painter = rememberAsyncImagePainter(model = selectedImageUri),
                    contentDescription = "Selected Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add",
                    modifier = Modifier.size(40.dp),
                    tint = Color.DarkGray
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Upload your profile pic",
            fontSize = 14.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )
    }
}

fun uriToFile(context: Context, uri: Uri): File {
    val inputStream = context.contentResolver.openInputStream(uri)
    val file = File(context.cacheDir, "profile_${System.currentTimeMillis()}.jpg")
    inputStream?.use { input ->
        FileOutputStream(file).use { output -> input.copyTo(output) }
    }
    return file
}




