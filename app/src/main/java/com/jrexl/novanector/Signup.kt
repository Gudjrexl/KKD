package com.jrexl.novanector

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jrexl.novanector.modallView.UserViewModel
import com.jrexl.novanector.dataclass.UserSignUp


class Signup : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {

            SignUpScreen()
        }
    }
}
@Composable
fun SignUpScreen(vm: UserViewModel = viewModel()) {
    val systemUiController = rememberSystemUiController()

    // Force white status bar icons
    systemUiController.setStatusBarColor(Color.Transparent, darkIcons = true)
    val context = LocalContext.current
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var mobile by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var result by remember { mutableStateOf("") }



    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(brush = Brush.verticalGradient(colors = listOf(Color(0xFFB3E5FC),
                Color.White  )))
        .padding(start = 10.dp, top = 40.dp),
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 15.dp,start = 10.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ){
            Spacer(modifier = Modifier.height(30.dp))
            Text(text = "Sign Up",
                modifier = Modifier
                .padding(vertical = 10.dp),
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                )
            Text(text = "Create Your Account")


            Spacer(modifier = Modifier.height(14.dp))
            Text(text = "Full Name", fontWeight = FontWeight.Bold, modifier = Modifier
                .padding(start = 10.dp),)
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                placeholder = { Text("Full Name", fontSize = 11.sp,  modifier = Modifier.offset(y= -4.dp)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)

                ,

                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    disabledContainerColor = Color.White
                ),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(14.dp))
            Text(text = "Email Address",fontWeight = FontWeight.Bold, modifier = Modifier
                .padding(start = 10.dp),)
            Spacer(modifier = Modifier.height(4.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = { Text("Enter Your Email", fontSize = 11.sp,  modifier = Modifier.offset(y= -4.dp)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)

                ,

                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    disabledContainerColor = Color.White
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(14.dp))
            Text(text = "Mobile Number" , fontWeight = FontWeight.Bold, modifier = Modifier
                .padding(start = 10.dp),)
            Spacer(modifier = Modifier.height(4.dp))

            OutlinedTextField(
                value = mobile,
                onValueChange = { mobile = it },
                placeholder = { Text("Enter Your Mobile Number", fontSize = 11.sp,  modifier = Modifier.offset(y= -4.dp)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)

                ,

                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    disabledContainerColor = Color.White
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(14.dp))
            Text(text = "Create Password", fontWeight = FontWeight.Bold, modifier = Modifier
                .padding(start = 10.dp),)
            Spacer(modifier = Modifier.height(4.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = { Text("Enter Your Password", fontSize = 11.sp,  modifier = Modifier.offset(y= -4.dp)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)


                ,

                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    disabledContainerColor = Color.White
                ),                visualTransformation = PasswordVisualTransformation(),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(14.dp))

            Text(text = "Confirm Password", fontWeight = FontWeight.Bold, modifier = Modifier
                .padding(start = 10.dp),)
            Spacer(modifier = Modifier.height(4.dp))

            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                placeholder = { Text("Enter Confirm Password", fontSize = 11.sp,  modifier = Modifier.offset(y= -4.dp)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)

                ,

                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    disabledContainerColor = Color.White
                ),                visualTransformation = PasswordVisualTransformation(),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(50.dp))
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 16.dp)
                )
            }else{
            Button(
                onClick = {
                    isLoading = true
                    val user = UserSignUp(name, mobile, email, password, confirmPassword)
                    vm.registerUser(context,user) { serverMessage ->
                        result = serverMessage
                        isLoading = false

                        if (serverMessage.contains("success", ignoreCase = true) ||
                            serverMessage.contains("registered", ignoreCase = true)
                        ) {
                           val intent = Intent(context, Login::class.java)
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
                Text("Create Account")
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
                Text(text = "Already have an account?", modifier = Modifier
                    .padding(start = 70.dp)
                   ,)
                Text(text = " [Login]", modifier = Modifier
                    .clickable(onClick = {
                        context.startActivity(Intent(context, Login::class.java))
                    }),)
            }


        }


    }
}

@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    SignUpScreen()
}