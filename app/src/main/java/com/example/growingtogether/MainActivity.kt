package com.example.growingtogether

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.growingtogether.ui.theme.GrowingTogetherTheme
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val apiKey = "AIzaSyB-ZYA2HPV_ol-x0KRl4dbj__GXPx2drfc"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GrowingTogetherTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LoginView()
                }
            }
        }
    }

}

@Composable
fun LoginView() {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFECC7CE)),
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
        LoginForm()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginForm() {

    val context = LocalContext.current;
    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
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
        Text(text = "Inicia sesion", fontSize = 35.sp, fontFamily = FontFamily.Serif, color = Color(0xFFC472C6))
        
        // Email input
        InputCard(cardName = "Correo", email, onValueChange = {email = it})

        InputCard(cardName = "Contrasena", password, onValueChange = { password = it })

        //Buttons for register and forgot password
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ClickableText(
                text = AnnotatedString("No tienes una cuenta?"),
                modifier = Modifier.padding(8.dp), // Agregar un padding si lo deseas
                style = TextStyle(
                    color = Color(0xFFC472C6), // Color en hexadecimal (morado en este ejemplo)
                    fontSize = 13.sp, // Tamaño de la fuente
                    fontFamily = FontFamily.Serif // Tipo de fuente (puedes cambiarla)
                ),
                onClick = {
                    // Start the register activity
                    val intent = Intent(context, RegisterActivity::class.java);
                    context.startActivity(intent);
                }
            )

            ClickableText(
                text = AnnotatedString("Olvidaste tu contrasena?"),
                modifier = Modifier.padding(8.dp), // Agregar un padding si lo deseas
                style = TextStyle(
                    color = Color(0xFFC472C6), // Color en hexadecimal (morado en este ejemplo)
                    fontSize = 13.sp, // Tamaño de la fuente
                    fontFamily = FontFamily.Serif // Tipo de fuente (puedes cambiarla)
                ),
                onClick = {
                    // Go to forgot password activity
                    val intent = Intent(context, ForgotPasswordActivity::class.java)
                    context.startActivity(intent)
                }
            )
        }

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