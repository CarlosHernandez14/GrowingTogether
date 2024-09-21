package com.example.growingtogether

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.growingtogether.dataclasses.Usuario
import com.example.growingtogether.ui.theme.GrowingTogetherTheme
import com.example.growingtogether.ui.theme.gabrielaFontFamily

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GrowingTogetherTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val user = intent.getSerializableExtra("user") as? Usuario;
                    if (user != null) {
                        HommeView(user)
                    }
                }
            }
        }
    }
}

@Composable
fun HommeView(user: Usuario) {

    

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    listOf(
                        Color(0xFFB0D0E7),
                        Color(0xFFD6EDF5),
                        Color(0xFFE1F5DA),
                        Color(0xFFCEE9BE)
                    )
                )
            ),
        verticalArrangement =  Arrangement.spacedBy(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp, 15.dp, 10.dp, 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.End)
        ) {
            Text(text = "Bienvenido ${user.nombre}", fontSize = 25.sp, fontFamily = gabrielaFontFamily, modifier = Modifier.fillMaxWidth(0.7f), textAlign = TextAlign.Center);
            IconButton(onClick = {
                // Button profile action
            }) {
                Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "Profile", Modifier.width(50.dp).height(40.dp))
            }
        }


    }
}