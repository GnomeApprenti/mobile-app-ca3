package com.stu74529.mobile_app_ca3

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

@Composable
fun LoginScreen(navController : NavController, auth: FirebaseAuth) {
    Text("login")

    var email by remember{
        mutableStateOf("")
    }

    var password by remember{
        mutableStateOf("")
    }

    Column(modifier = Modifier
        .background(color = Color.LightGray)
        .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround

    ){
        Text("welcome back", fontSize = 32.sp)

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            OutlinedTextField(
                value = email,
                onValueChange = {email = it},
                label = {Text("Email Address")}
            )

            OutlinedTextField(
                value = password,
                onValueChange = {password = it},
                label = {Text("Password")}
            )

            TextButton(onClick =
            {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener{
                        if(it.isSuccessful){
                            navController.navigate(Routes.HomeScreen.route)
                        }
                    }

            },
                modifier = Modifier
                    .padding(0.dp, 16.dp)
                    .background(color = Color.White),
                ) {
                Text("Login")
            }
        }

        TextButton(onClick = {
            navController.navigate(Routes.RegistrationScreen.route)
                             },
            modifier = Modifier
                .padding(0.dp, 16.dp)
                .background(color = Color.White)) {
            Text("Sign up")
        }

    }
}