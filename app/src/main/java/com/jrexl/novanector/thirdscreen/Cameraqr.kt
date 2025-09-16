@file:kotlin.OptIn(ExperimentalPermissionsApi::class)

package com.jrexl.novanector.thirdscreen

import android.Manifest
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.OptIn
import androidx.camera.core.CameraControl
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FlashOff
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import com.jrexl.novanector.HomePage
import com.jrexl.novanector.R
import com.jrexl.novanector.dataclass.creditcoindata
import com.jrexl.novanector.modallView.TransactionModelView
import com.jrexl.novanector.objectBuild.DataStoreManager
import org.json.JSONObject
import java.util.concurrent.Executors



class Cameraqr : ComponentActivity() {
    @OptIn(ExperimentalGetImage::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Camerascanner()
        }

    }
}
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun Camerascanner(vm: TransactionModelView = viewModel()) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(Color.Transparent, darkIcons = true)
    systemUiController.setNavigationBarColor(Color.White, darkIcons = true)
    var hasCameraPermission by remember { mutableStateOf(false) }
    var scannedResult by remember { mutableStateOf<String?>(null) }
    var isTorchOn by remember { mutableStateOf(false) }
    var cameraControl: CameraControl? by remember { mutableStateOf(null) }
    var cameraProvider: ProcessCameraProvider? by remember { mutableStateOf(null) }
    var imageAnalysis: ImageAnalysis? by remember { mutableStateOf(null) }
var contactno: String? by remember { mutableStateOf("") }
    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)
    val galleryPermissionState = rememberPermissionState(Manifest.permission.READ_EXTERNAL_STORAGE) // For API < 33
    val photosPermissionState = rememberPermissionState(Manifest.permission.READ_MEDIA_IMAGES) // For API >= 33
var coins by remember { mutableIntStateOf(0) }
    var showCongrats by remember { mutableStateOf(false) }
    var showRegret by remember { mutableStateOf(false) }

    val coinResult by vm.coinFeedback.collectAsState()
    LaunchedEffect(coinResult) {
        coinResult?.let {
            if (it.creditcoininacount) {
                showCongrats = true
            } else {
                showRegret = true
            }
        }
    }


    LaunchedEffect(Unit) {
        contactno = DataStoreManager.getUserPhone(context)
    }
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            processImageFromUri(context, it) { result ->
                scannedResult = result

                try {
                    val jsonObject = JSONObject(scannedResult)
                    coins = jsonObject.getInt("coins")
                    val scanQRCodes = jsonObject.getString("scanQRCodes")

                    val creditCoinData = creditcoindata(contactno, coins, scanQRCodes)
                    vm.credit(creditCoinData, onSuccess = {
                        showCongrats = true
                        Toast.makeText(context, "Credit Successful", Toast.LENGTH_SHORT).show()
                    },
                        onFailure = { errorMessage ->
                            showRegret = true
                            Toast.makeText(context, "failureshow", Toast.LENGTH_SHORT).show()

                        })
                }catch (e: Exception){
                    print(e)
                    showRegret = true
                    Toast.makeText(context, "excdeption showl", Toast.LENGTH_SHORT).show()

                }


            }
        }
    }

    LaunchedEffect(cameraPermissionState.status) {
        hasCameraPermission = cameraPermissionState.status.isGranted
        if (!hasCameraPermission) {
            cameraPermissionState.launchPermissionRequest()
        }
    }

    DisposableEffect(lifecycleOwner) {
        onDispose {
            cameraProvider?.unbindAll()
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        if (hasCameraPermission) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                AndroidView(
                    modifier = Modifier.fillMaxSize(),
                    factory = { ctx ->
                        val previewView = PreviewView(ctx)
                        val cameraProviderFuture = ProcessCameraProvider.getInstance(ctx)

                        cameraProviderFuture.addListener({
                            cameraProvider = cameraProviderFuture.get()
                            val preview = Preview.Builder().build().also {
                                it.setSurfaceProvider(previewView.surfaceProvider)
                            }

                            imageAnalysis = ImageAnalysis.Builder()
                                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                                .build()
                                .also {
                                    it.setAnalyzer(Executors.newSingleThreadExecutor(), QrCodeAnalyzer { result ->
                                        scannedResult = result
                                        try {
                                            val jsonObject = JSONObject(scannedResult)
                                             coins = jsonObject.getInt("coins")
                                            val scanQRCodes = jsonObject.getString("scanQRCodes")

                                            val creditCoinData = creditcoindata(contactno, coins, scanQRCodes)
                                            vm.credit(creditCoinData, onSuccess = {
                                                showCongrats = true
                                            },
                                                onFailure = { errorMessage ->
                                                    showRegret = true
                                                })
                                        }catch (e: Exception){
                                            print(e)
                                            showRegret = true
                                        }


                                    })
                                }

                            try {
                                cameraProvider?.unbindAll()
                                val camera = cameraProvider?.bindToLifecycle(
                                    lifecycleOwner,
                                    CameraSelector.DEFAULT_BACK_CAMERA,
                                    preview,
                                    imageAnalysis
                                )
                                cameraControl = camera?.cameraControl
                                camera?.cameraInfo?.hasFlashUnit()?.let { hasFlash ->
                                    // You can use hasFlash to show/hide the torch icon
                                }
                            } catch (exc: Exception) {
                                scannedResult = "Failed to start camera: ${exc.message}"
                            }
                        }, ContextCompat.getMainExecutor(ctx))
                        previewView
                    }
                )
                Row(
                    modifier = Modifier.align(Alignment.TopStart).statusBarsPadding().padding(start = 16.dp, top =  10.dp)
                ) {
                    IconButton(onClick = {
                        val inten = Intent(context, HomePage::class.java)
                        context.startActivity(inten)
                        scannedResult = null
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "Close",
                            tint = Color.White,
                            modifier = Modifier.size(30.dp).clip(CircleShape)
                        )
                    }
                }

                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    ScannerOverlay()
                }

                Row(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(horizontal = 20.dp)
                        .offset(y = -200.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    IconButton(
                        onClick = {
                            isTorchOn = !isTorchOn
                            cameraControl?.enableTorch(isTorchOn)
                        },
                        modifier = Modifier.size(48.dp) // keep touch target size
                    ) {
                        Icon(
                            painter = rememberVectorPainter(if (isTorchOn) Icons.Filled.FlashOn else Icons.Filled.FlashOff),
                            contentDescription = if (isTorchOn) "Turn Flash Off" else "Turn Flash On",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }


                    Spacer(modifier = Modifier.width(10.dp))

                    IconButton(
                        onClick = {
                            val permissionToRequest = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                photosPermissionState
                            } else {
                                galleryPermissionState
                            }

                            if (permissionToRequest.status.isGranted) {
                                galleryLauncher.launch("image/*")
                            } else {
                                permissionToRequest.launchPermissionRequest()
                            }
                        },
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(
                            painter = rememberVectorPainter(Icons.Filled.Image),
                            contentDescription = "Select from Gallery",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                }
            }
            Spacer(modifier = Modifier.height(16.dp))

        }
    }
    if (showCongrats) {
        AlertDialog(
            onDismissRequest = { showCongrats = false },
            confirmButton = {},
            modifier = Modifier
                .width(280.dp) // 🔹 reduce width
                .wrapContentHeight(),
            title = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center, // 🔹 center
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Row {
                            Spacer(modifier = Modifier.weight(1f))

                            IconButton(onClick = {
                                showCongrats = false
                                val intent = Intent(context, HomePage::class.java)
                                context.startActivity(intent)
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Close"
                                )
                            }
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            Text(
                                "Congratulations!",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }


                    }




                }
            },
            text = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Spacer(modifier = Modifier.height(8.dp))

                    Image(
                        painter = painterResource(id = R.drawable.voinimg),
                        contentDescription = "Success Icon",
                        modifier = Modifier.size(70.dp) // 🔹 smaller
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        "$coins Coins ", fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        "Credited to your account.",
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        )
    }


    if (showRegret) {
        val username = coinResult?.coinusername ?: "someone"
        val date = coinResult?.coindate ?: "unknown date"

        AlertDialog(
            onDismissRequest = { showRegret = false },
            confirmButton = { }, // no confirm button
            modifier = Modifier
                .width(280.dp) // 🔹 control width
                .wrapContentHeight(), // 🔹 height only as needed
            title = {
                Column {
                    Row {
                        Spacer(modifier = Modifier.weight(1f))
                        IconButton(onClick = { showRegret = false }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close"
                            )
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Oops!",
                            fontSize = 20.sp, // 🔹 smaller text
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            },
            text = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.oops),
                        contentDescription = "Oops Icon",
                        modifier = Modifier.size(40.dp) // 🔹 smaller image
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        buildAnnotatedString {
                            append("This QR code has already been used\nby ")

                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append(username)
                            }

                            append(" on ")

                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append(date)
                            }
                        },
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    )

                }
            }
        )
    }


}

@Composable
fun ScannerOverlay() {
    Canvas(
        modifier = Modifier.size(250.dp) // scanning box size
    ) {
        val stroke = 3.dp.toPx()
        val corner = 40.dp.toPx()
        val dcolor = Color.White

        // Top-left corner
        drawLine(
            color = dcolor,
            start = Offset(0f, 0f),
            end = Offset(corner, 0f),
            strokeWidth = stroke
        )
        drawLine(
            color = dcolor,
            start = Offset(0f, 0f),
            end = Offset(0f, corner),
            strokeWidth = stroke
        )

        // Top-right
        drawLine(
            color = dcolor,
            start = Offset(size.width, 0f),
            end = Offset(size.width - corner, 0f),
            strokeWidth = stroke
        )
        drawLine(
            color = dcolor,
            start = Offset(size.width, 0f),
            end = Offset(size.width, corner),
            strokeWidth = stroke
        )

        // Bottom-left
        drawLine(
            color = dcolor,
            start = Offset(0f, size.height),
            end = Offset(corner, size.height),
            strokeWidth = stroke
        )
        drawLine(
            color = dcolor,
            start = Offset(0f, size.height),
            end = Offset(0f, size.height - corner),
            strokeWidth = stroke
        )

        // Bottom-right
        drawLine(
            color = dcolor,
            start = Offset(size.width, size.height),
            end = Offset(size.width - corner, size.height),
            strokeWidth = stroke
        )
        drawLine(
            color = dcolor,
            start = Offset(size.width, size.height),
            end = Offset(size.width, size.height - corner),
            strokeWidth = stroke
        )
    }
}



private fun processImageFromUri(context: Context, uri: Uri, onResult: (String) -> Unit) {
    val bitmap: Bitmap? = try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val source = ImageDecoder.createSource(context.contentResolver, uri)
            ImageDecoder.decodeBitmap(source)
        } else {
            MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
        }
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }

    bitmap?.let {
        val image = InputImage.fromBitmap(it, 0) // 0 rotation for gallery images, adjust if needed
        val options = BarcodeScannerOptions.Builder()
            .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
            .build()
        val scanner = BarcodeScanning.getClient(options)

        scanner.process(image)
            .addOnSuccessListener { barcodes ->
                if (barcodes.isNotEmpty()) {
                    for (barcode in barcodes) {
                        barcode.rawValue?.let { rawValue ->
                            onResult(rawValue)
                        }
                    }
                } else {
                    onResult("No QR code found in the selected image.")
                }
            }
            .addOnFailureListener { e ->
                onResult("Error scanning image: ${e.message}")
            }
    } ?: onResult("Could not load image from gallery.")
}




