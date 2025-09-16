package com.jrexl.novanector.screen

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.contentColorFor
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.jrexl.novanector.R
import com.jrexl.novanector.dataclass.ProfileDataClass
import com.jrexl.novanector.modallView.ProfileModelView
import com.jrexl.novanector.objectBuild.Constf
import com.jrexl.novanector.objectBuild.DataStoreManager
import com.jrexl.novanector.thirdscreen.Kycverification
import com.jrexl.novanector.thirdscreen.PersonalDet
import com.jrexl.novanector.thirdscreen.Verificationkyc

@Composable
fun ProfileScreen(vm: ProfileModelView = viewModel()) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        val contactno = DataStoreManager.getUserPhone(context)
        vm.profiledatagetviewmode(contactno)
    }
    Column(modifier = Modifier.background(
        brush = Brush.verticalGradient(
            colors = listOf(
                Color(0xFFB3E5FC),
                Color.White
            )
        )
    )) {
        var personalInfoCompleted by remember { mutableStateOf(false) }
        var kycInfoCompleted by remember { mutableStateOf(false) }
        var documentInfoCompleted by remember { mutableStateOf(false) }
        var progressint by remember { mutableStateOf(0) }

        LaunchedEffect(personalInfoCompleted, kycInfoCompleted, documentInfoCompleted) {
            progressint = listOf(
                personalInfoCompleted,
                kycInfoCompleted,
                documentInfoCompleted
            ).count { it }
        }

        Column(Modifier.statusBarsPadding().verticalScroll(scrollState)) {

        Profileheader(pf = vm.profilevm)
        Spacer(modifier = Modifier.height(3.dp))
        if (progressint != 3) {
            Progressbox(pf = vm.profilevm, progressint = progressint)
            Spacer(modifier = Modifier.height(3.dp))
        }

        PersonalInfo(pf = vm.profilevm) { completed ->
            personalInfoCompleted = completed
        }

        Spacer(modifier = Modifier.height(3.dp))
        Kyc(pf = vm.profilevm) { completed ->
            kycInfoCompleted = completed
        }
        Spacer(modifier = Modifier.height(3.dp))
        Documents(pf = vm.profilevm) { completed ->
            documentInfoCompleted = completed
        }
        Spacer(modifier = Modifier.height(100.dp))


    }
    }
}

@Composable
fun Documents(pf: ProfileDataClass?, onCompletedChanged: (Boolean) -> Unit) {
    var passbookicon by remember { mutableStateOf(true) }
    LaunchedEffect(pf) {
        val isComplete = !pf?.BankAccount.isNullOrEmpty() &&
                !pf.BankHolderName.isNullOrEmpty() &&
                !pf.BankName.isNullOrEmpty() &&
                !pf.ifsc.isNullOrEmpty()

        onCompletedChanged(isComplete)
    }
    val context = LocalContext.current
    Box(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp).fillMaxSize().clip(RoundedCornerShape(12.dp)).background(Color(0x4FD5DDF4).copy(alpha = 0.3f))){
        Column(modifier = Modifier.fillMaxSize().padding(12.dp), verticalArrangement = Arrangement.SpaceBetween){
            Text(text = "Bank details", fontSize = 16.sp, fontWeight = FontWeight.Medium, color = Color.Black, modifier = Modifier.clickable(onClick = {
                val inten = Intent(context, Verificationkyc::class.java)
                context.startActivity(inten)
            }))
            Divider(color = Color.Black, thickness = 1.dp, modifier = Modifier.padding(vertical = 3.dp))
            Spacer(modifier = Modifier.height(10.dp))
            Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
                Text(text = "Account Number", fontSize = 12.sp, color = Color.Black, fontWeight = FontWeight.Medium)
                Text(text = pf?.BankAccount ?:"" , fontSize = 12.sp, color = Color.Black, fontWeight = FontWeight.Medium)
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
                Text(text = "Account Holder", fontSize = 12.sp, color = Color.Black, fontWeight = FontWeight.Medium)
                Text(text = pf?.BankHolderName ?:"" , fontSize = 12.sp, color = Color.Black, fontWeight = FontWeight.Medium)
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
                Text(text = "Bank Name", fontSize = 12.sp, color = Color.Black, fontWeight = FontWeight.Medium)
                Text(text = pf?.BankName ?:"" , fontSize = 12.sp, color = Color.Black, fontWeight = FontWeight.Medium)
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
                Text(text = "IFSC ", fontSize = 12.sp, color = Color.Black, fontWeight = FontWeight.Medium)
                Text(text = pf?.ifsc ?:"" , fontSize = 12.sp, color = Color.Black, fontWeight = FontWeight.Medium)
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row {
if (passbookicon){
    Image(
        painter = painterResource(R.drawable.poassbook),
        contentDescription = "passbook",
        modifier = Modifier.size(15.dp)
    )
    Spacer(modifier = Modifier.width(5.dp))
}

                Text("PassBook", modifier = Modifier.size(70.dp,15.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
if(pf?.BankAccount?.isNotEmpty() == true && pf.BankHolderName?.isNotEmpty() == true && pf.BankName?.isNotEmpty() == true && pf.ifsc?.isNotEmpty() == true){
    Text("Verified", fontSize = 12.sp, color = Color(0xFF006400))
    passbookicon = false;

}
            }

        }
}}

@Composable
fun Kyc(pf: ProfileDataClass?, onCompletedChanged: (Boolean) -> Unit) {
val context = LocalContext.current

    LaunchedEffect(pf) {
        val isComplete = !pf?.PanCardVerify.isNullOrEmpty() &&
                !pf.AadharCardVerify.isNullOrEmpty() && pf?.PanCardVerify == "Verified" && pf.AadharCardVerify == "Verified"
        onCompletedChanged(isComplete)
    }
Box(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp).fillMaxSize().clip(RoundedCornerShape(12.dp)).fillMaxSize().background(Color(0x4FD5DDF4).copy(alpha = 0.3f))){
    Column(modifier = Modifier.fillMaxSize().padding(12.dp), verticalArrangement = Arrangement.SpaceBetween){
        Text(text = "KYC", fontSize = 16.sp, fontWeight = FontWeight.Medium, color = Color.Black, modifier = Modifier.clickable(onClick = {
            if (pf?.PanCardVerify != "Verified" && pf?.AadharCardVerify != "Verified") {
                val inte = Intent(context, Kycverification::class.java)
                context.startActivity(inte)
            }



        }))
        Divider(color = Color.Black, thickness = 1.dp, modifier = Modifier.padding(vertical = 8.dp))
        Spacer(modifier = Modifier.height(10.dp))
        Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
            Text(text = "AAdhar Card", fontSize = 12.sp, color = Color.Black, fontWeight = FontWeight.Medium)
            Text(text = pf?.AadharCardVerify ?:"" , fontSize = 12.sp, color = Color.Black, fontWeight = FontWeight.Medium)
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
            Text(text = "Pan Card", fontSize = 12.sp, color = Color.Black, fontWeight = FontWeight.Medium)
            Text(text = pf?.PanCardVerify ?:"" , fontSize = 12.sp, color = Color.Black, fontWeight = FontWeight.Medium)
        }
    }
}
}

@Composable
fun PersonalInfo(pf: ProfileDataClass?, onCompletedChanged: (Boolean) -> Unit) {
    LaunchedEffect(pf) {
        val isComplete = !pf?.contactno.isNullOrEmpty() &&
                !pf.gmailid.isNullOrEmpty() &&
                !pf.dob.isNullOrEmpty() &&
                !pf.address.isNullOrEmpty() &&
                pf.Pincode != null &&
                !pf.State.isNullOrEmpty() &&
                !pf.Country.isNullOrEmpty()
        onCompletedChanged(isComplete)
    }

    val context = LocalContext.current

    Box(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp).fillMaxSize().clip(RoundedCornerShape(12.dp)).background(Color(0x4FD5DDF4).copy(alpha = 0.3f))){
        Column(modifier = Modifier.fillMaxSize().padding(12.dp), verticalArrangement = Arrangement.SpaceBetween) {
            Text(text = "Personal Details", fontSize = 16.sp, fontWeight = FontWeight.Medium, color = Color.Black, modifier = Modifier.clickable(onClick = {
val inte = Intent(context, PersonalDet::class.java)
                context.startActivity(inte)
            }))
            Divider(color = Color.Black, thickness = 1.dp, modifier = Modifier.padding(vertical = 8.dp))
            Spacer(modifier = Modifier.height(10.dp))
            Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
                Text(text = "Contact Number", fontSize = 12.sp, color = Color.Black, fontWeight = FontWeight.Medium)
                Text(text = pf?.contactno ?:"" , fontSize = 12.sp, color = Color.Black, fontWeight = FontWeight.Medium)
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
                Text(text = "Email id", fontSize = 12.sp, color = Color.Black, fontWeight = FontWeight.Medium)
                Text(text = pf?.gmailid ?:"" , fontSize = 12.sp, color = Color.Black, fontWeight = FontWeight.Medium)
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
                Text(text = "Date of Birth", fontSize = 12.sp, color = Color.Black, fontWeight = FontWeight.Medium)
                Text(text = pf?.dob ?:"" , fontSize = 12.sp, color = Color.Black, fontWeight = FontWeight.Medium)
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
                Text(text = "Permanent Address", fontSize = 12.sp, color = Color.Black, fontWeight = FontWeight.Medium)
                Text(text = pf?.address ?:"" , fontSize = 12.sp, color = Color.Black, fontWeight = FontWeight.Medium)
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
                Text(text = "Pin Code", fontSize = 12.sp, color = Color.Black, fontWeight = FontWeight.Medium)
                pf?.Pincode?.let {
                    Text(
                        text = it.toString(),
                        fontSize = 12.sp,
                        color = Color.Black,
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
                Text(text = "State", fontSize = 12.sp, color = Color.Black, fontWeight = FontWeight.Medium)
                Text(text = pf?.State ?:"" , fontSize = 12.sp, color = Color.Black, fontWeight = FontWeight.Medium)
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
                Text(text = "Country", fontSize = 12.sp, color = Color.Black, fontWeight = FontWeight.Medium)
                Text(text = pf?.Country ?:"" , fontSize = 12.sp, color = Color.Black, fontWeight = FontWeight.Medium)
            }
    }

}}

@Composable
fun Progressbox(pf: ProfileDataClass?, progressint: Int) {
    val totalSteps = 3
    val completedSteps = progressint.coerceIn(0, totalSteps)

    Box(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 8.dp)
            .fillMaxSize()
            .clip(RoundedCornerShape(12.dp))
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xFF1CD8D2), Color(0xFF93EDC7))
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Complete Your Profile",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "This helps build trust, encouraging members",
                fontSize = 14.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "$completedSteps out of $totalSteps completed",
                fontSize = 15.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                repeat(totalSteps) { index ->
                    val isComplete = index < completedSteps

                    LinearProgressIndicator(
                        progress = 1f,
                        modifier = Modifier
                            .weight(1f)
                            .height(6.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        color = if (isComplete) Color(0xFF4CAF50) else Color.LightGray,
                        trackColor = Color.Transparent
                    )

                    if (index < totalSteps - 1) {
                        Spacer(modifier = Modifier.width(10.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}


@Composable
fun Profileheader(pf: ProfileDataClass?) {


    Row(modifier = Modifier.statusBarsPadding().fillMaxWidth().padding(16.dp),verticalAlignment = Alignment.Top) {
        val context = LocalContext.current
        val imageUrl = "${Constf.BASE_URL.trimEnd('/')}/profilepic/${pf?.ProfilePic.orEmpty()}"
        var showDialog by remember { mutableStateOf(false) }


        Log.d("ProfilePicURL", "Image URL = $imageUrl")
        AsyncImage(
            ImageRequest.Builder(context)
                .data(imageUrl)
                .crossfade(true)
                .diskCachePolicy(CachePolicy.ENABLED)
                .memoryCachePolicy(CachePolicy.ENABLED)
                .build(),
            contentDescription = null,

            modifier = Modifier.padding(3.dp).clip(CircleShape).size(50.dp).clickable(onClick = {
                showDialog = true
            }),
            contentScale = ContentScale.Crop
        )

            if (showDialog) {
                Dialog(onDismissRequest = { showDialog = false }) {
                    Box(
                        modifier = Modifier
                            .size(350.dp)
                           ,
                        contentAlignment = Alignment.Center
                    ) {
                        AsyncImage(
                            model = imageUrl,
                            contentDescription = null,
                            modifier = Modifier
                                .size(350.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .clickable { showDialog = false }, // close on click
                            contentScale = ContentScale.Crop
                        )
                    }
                }
        }
        Spacer(modifier = Modifier.width(10.dp))

        Column(verticalArrangement = Arrangement.Center,
            modifier = Modifier.align(Alignment.CenterVertically)){
            Text(text = pf?.uname ?:"" , fontSize = 20.sp, fontWeight = FontWeight.Bold )
//            Text(text = pf?.designation ?:"" , fontSize = 12.sp, color = Color.Gray)



    }
}}