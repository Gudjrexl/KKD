package com.jrexl.novanector

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.jrexl.novanector.dataclass.LoginData
import com.jrexl.novanector.modallView.UserViewModel

class Login : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginScreen()
        }
    }
    }

@SuppressLint("SuspiciousIndentation")
@Composable
fun LoginScreen(vm: UserViewModel = viewModel()) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(Color.Transparent, darkIcons = true)
    var uniqueid by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var result by remember { mutableStateOf("") }
val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(brush = Brush.verticalGradient(colors = listOf(Color(0xFFB3E5FC), // light blue at top
                Color.White  )))
            .padding(start = 10.dp, top = 40.dp),
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 15.dp,start = 10.dp), // right padding (optional)
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ){
            Spacer(modifier = Modifier.height(30.dp))

            Text(text = "Welcome Back!",
                modifier = Modifier
                    .padding(vertical = 10.dp),
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            )

            Text(text = "Login to continue using your account.")
            Spacer(modifier = Modifier.height(20.dp))

            Text(text = "Email / Mobile Number", modifier = Modifier
                .padding(start = 10.dp),
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                ))
            Spacer(modifier = Modifier.height(4.dp))

            OutlinedTextField(
                value = uniqueid,
                onValueChange = { uniqueid = it },
                placeholder = { Text("Enter Your Email / Mobile number", fontSize = 11.sp,  modifier = Modifier.offset(y= -4.dp)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    disabledContainerColor = Color.White
                ),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "Password", modifier = Modifier
                .padding(start = 10.dp),
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                ))
            Spacer(modifier = Modifier.height(4.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                textStyle = LocalTextStyle.current.copy(fontSize = 14.sp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    ,
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    disabledContainerColor = Color.White
                ),
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true,
                        placeholder = { Text("Enter Your Password", fontSize = 11.sp, modifier = Modifier.offset(y= -4.dp)) },

                )

            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 16.dp)
                )
            }else{
                Spacer(modifier = Modifier.height(300.dp))

                Button(
                    onClick = {
                        isLoading = true
                        vm.loginuser(context,LoginData(uniqueid, password)) { response ->
                            result = response

                            if (response.contains("success", ignoreCase = true)
                            ) {
                                val intent = Intent(context, HomePage::class.java)
                                context.startActivity(intent)
                                (context as? Activity)?.finish()

                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black,
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    enabled = !isLoading
                ) {
                    Text("Login")
                }}
            if (result.isNotEmpty()) {
                Text(
                    text = result,
                    color = if (result.contains("success", true)) Color.Green else Color.Red,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
            Spacer(modifier = Modifier.height(23.dp))

            Row {
                Text(text = "Don’t have an account?", modifier = Modifier
                    .padding(start = 70.dp)
                   ,)

                Text(text = " [Sign Up]", modifier = Modifier
                    .clickable(onClick = {
                        context.startActivity(Intent(context, Signup::class.java))
                    }),)
            }


        }


    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen()
}