package com.example.growingtogether

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.growingtogether.ui.theme.GrowingTogetherTheme

class RegisterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GrowingTogetherTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RegisterView()
                }
            }
        }
    }
}

@Composable
fun RegisterView() {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFD6EDF5)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_bebe),
            contentDescription = "logo bebe",
            modifier = Modifier
                .size(200.dp)  // Cambia el tamaño del logo
                .padding(top = 25.dp),  // Añade un margen superior
            contentScale = ContentScale.Fit
        )
        RegisterForm()
    }
}

@Composable
fun RegisterForm() {

    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    var passwordRepeat by remember {
        mutableStateOf("")
    }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
            .background(Color.White, shape = RoundedCornerShape(15)),
        verticalArrangement = Arrangement.spacedBy(15.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //TITTLE
        Text(text = "Crear cuenta", fontSize = 35.sp, fontFamily = FontFamily.Serif, color = Color(0xFFC472C6))

        // Email input
        InputCard(cardName = "Correo", email, onValueChange = {email = it})

        InputCard(cardName = "Contrasena", password, onValueChange = { password = it })
        
        InputCard(cardName = "Repite tu contrasena", inputValue = passwordRepeat, onValueChange = { passwordRepeat = it })

        // BUtton to access
        Button(
            onClick = { /* Acción del botón */ },
            modifier = Modifier
                .shadow(8.dp, RoundedCornerShape(5.dp)) // Agregar sombra
                .height(45.dp), // Altura del botón
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFC472C6)
            ),
            shape = RoundedCornerShape(8.dp), // Redondeado del botón
        ) {
            Text(text = "Acceso", fontSize = 18.sp)
        }


    }
}