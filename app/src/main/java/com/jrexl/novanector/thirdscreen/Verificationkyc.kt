package com.jrexl.novanector.thirdscreen

import android.R.attr.background
import android.app.Activity
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddAPhoto
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
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.jrexl.novanector.HomePage
import com.jrexl.novanector.R
import com.jrexl.novanector.modallView.ProfileModelView
import com.jrexl.novanector.objectBuild.DataStoreManager
import java.io.File
import kotlin.contracts.contract

class Verificationkyc : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            knowyourcustomer()
        }
    }
}

@Composable
private fun knowyourcustomer(vmp: ProfileModelView = viewModel(),vm: com.jrexl.novanector.modallView.ProfileModelView = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val ScrolleState = rememberScrollState()
    var showPassbookUploader by remember { mutableStateOf(false) }
    var holdername by remember { mutableStateOf("") }
    var accountno by remember { mutableStateOf("") }
    var ifsccode by remember { mutableStateOf("") }
    var bankname by remember { mutableStateOf("") }

val context = LocalContext.current
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(Color.Transparent, darkIcons = true)
    systemUiController.setNavigationBarColor(Color.White, darkIcons = true)

    LaunchedEffect(Unit) {
        val contactno = DataStoreManager.getUserPhone(context)
        vm.profiledatagetviewmode(contactno)
        vm.profilevm
    }


    Column(modifier = Modifier.fillMaxSize().verticalScroll(ScrolleState).background(Brush.verticalGradient(colors = listOf(Color(0xFFB3E5FC),Color.White)))){
        Spacer(modifier = Modifier.height(15.dp))

        Row( modifier = Modifier.statusBarsPadding().padding(start = 3.dp, top = 8.dp) ) {

            IconButton(
                onClick = {
                    (context as? Activity)?.finish()      },
            ){
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black,
                    modifier = Modifier.size(20.dp).offset(y = -13.dp)
                ) }
            Text(text = "Enter Your bank Details here", modifier = Modifier.fillMaxWidth().padding(start = 0.5.dp),fontWeight = FontWeight.Medium, fontSize = 18.sp)

        }
        Divider(color = Color.Gray, thickness = 0.8.dp, modifier = Modifier.padding(vertical = 6.dp).offset(y = -10.dp))


        androidx.compose.material3.Text(
            text = "Enter Bank Holder Name", fontWeight = FontWeight.Medium,
            modifier = Modifier
                .padding(start = 18.dp),
        )
        Spacer(modifier = Modifier.height(4.dp))
        androidx.compose.material3.OutlinedTextField(
            value = holdername,
            onValueChange = { holdername = it },
            placeholder = {
                Text(
                    text = vm.profilevm?.BankHolderName.takeIf { !it.isNullOrEmpty() }
                        ?: "Enter Bank Holder Name",
                    fontSize = 11.sp
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
            text = "Enter Bank Name", fontWeight = FontWeight.Medium,
            modifier = Modifier
                .padding(start = 18.dp),
        )
        Spacer(modifier = Modifier.height(4.dp))
        androidx.compose.material3.OutlinedTextField(
            value = bankname,
            onValueChange = { bankname = it },
            placeholder = {
                Text(
                    text = vm.profilevm?.BankName.takeIf { !it.isNullOrEmpty() }
                        ?:"Enter Bank Name",
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
            text = "Enter Account Number", fontWeight = FontWeight.Medium,
            modifier = Modifier
                .padding(start = 18.dp),
        )
        Spacer(modifier = Modifier.height(4.dp))
        androidx.compose.material3.OutlinedTextField(
            value = accountno,
            onValueChange = { accountno = it },
            placeholder = {
                Text(
                    text = vm.profilevm?.BankName.takeIf { !it.isNullOrEmpty() }
                        ?: "Enter Account Number",
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
            text = "Enter IFSC Code", fontWeight = FontWeight.Medium,
            modifier = Modifier
                .padding(start = 18.dp),
        )
        Spacer(modifier = Modifier.height(4.dp))
        androidx.compose.material3.OutlinedTextField(
            value = ifsccode,
            onValueChange = { ifsccode = it },
            placeholder = {
                Text(
                    text = vm.profilevm?.ifsc.takeIf { !it.isNullOrEmpty() }
                        ?:"Enter IFSC Code",
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

//if(vm.profilevm?.BankAccount.isNullOrEmpty() && !showPassbookUploader){
        Button(onClick = {
val Bankverificationdata = com.jrexl.novanector.dataclass.Bankverificationdata(
    BankHolderName = holdername,
    BankName = bankname,
    BankAccount = accountno,
    ifsc = ifsccode,
    passbookimg = "",
    contactno = vm.profilevm?.contactno ?: ""
)
            vm.bankdatapost(Bankverificationdata)
            showPassbookUploader = true


        },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Black,
                contentColor = Color.White
            ), modifier = Modifier.fillMaxWidth().padding(16.dp).size(40.dp).clip(RoundedCornerShape(12.dp))){
            Text(text = "Submit bank details")
            Spacer(modifier = Modifier.height(20.dp))
        }
//}

        if (showPassbookUploader){
            PassbookImage()
            Spacer(modifier = Modifier.padding(3.dp))
        }

    }
}

@Composable
fun PassbookImage(vm: com.jrexl.novanector.modallView.ProfileModelView = androidx.lifecycle.viewmodel.compose.viewModel()) {
var selectedurl by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    val contactno = remember { mutableStateOf<String?>(null) }
    LaunchedEffect(Unit) {
        contactno.value = DataStoreManager.getUserPhone(context)
    }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedurl = uri

        if (uri != null && contactno.value != null) {
            val file = uriToFiles(context, uri)
            if (file.length() <= 5 * 1024 * 1024){
                vm.bankverifyimg(
                    context = context,
                    contactno = contactno.value!!,
                    imageFile = file
                )
            }
            else{
                Toast.makeText(context, "File too large! Max 5 MB allowed.", Toast.LENGTH_SHORT).show()

            }

        }
    }
   Column(horizontalAlignment = Alignment.CenterHorizontally,
       verticalArrangement = Arrangement.Center, modifier = Modifier.padding(16.dp).fillMaxWidth()) {

       Box(modifier = Modifier.fillMaxWidth().height(200.dp).padding(16.dp).clip(RectangleShape).background(Color.White)
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
               Text(text = "+ Upload passbook image", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center, fontSize = 20.sp)
           }

       }
       Spacer(modifier = Modifier.height(8.dp))
       androidx.compose.material3.Text(
           text = "Only JPG, PNG, or PDF files. Max size: 5 MB",
           fontSize = 14.sp,
           color = Color.Gray,
           textAlign = TextAlign.Center
       )

       Spacer(modifier = Modifier.height(10.dp))
       Button(modifier = Modifier.fillMaxWidth().padding(16.dp),onClick = {
           val inte = android.content.Intent(context, HomePage::class.java)
           context.startActivity(inte)
       },  colors = ButtonDefaults.buttonColors(
           backgroundColor = Color.Black,
           contentColor = Color.White
       )
       ) { Text(text = "Submit Passbook")}
       Spacer(modifier = Modifier.height(10.dp))
   }

}

fun uriToFiles(context: Context, uri: Uri): File {
    val inputStream = context.contentResolver.openInputStream(uri)
    val tempFile = File.createTempFile("passbook", ".jpg", context.cacheDir)
    inputStream?.use { input ->
        tempFile.outputStream().use { output ->
            input.copyTo(output)
        }
    }
    return tempFile
}

@Composable
@Preview(showBackground = true)
fun knowyourcustomerPreview() {
    knowyourcustomer()
}


