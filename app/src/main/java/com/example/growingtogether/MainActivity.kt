package com.example.growingtogether

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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
                    RequestForm()
                }
            }
        }
    }


    @Composable
    fun RequestForm() {
        var inputRequest by remember { mutableStateOf("") }
        var responseText by remember { mutableStateOf("") }
        val coroutineScope = rememberCoroutineScope()

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = inputRequest,
                onValueChange = { inputRequest = it },
                label = { Text("Request") }
            )

            Button(onClick = {
                coroutineScope.launch {
                    getGeminiResponse(inputRequest) { response ->
                        responseText = response  // Actualiza el estado de la UI
                    }
                }
            }) {
                Text(text = "Submit request")
            }

            Text(text = "Response: $responseText")
        }
    }

    private suspend fun getGeminiResponse(request: String, onResponse: (String) -> Unit) {
        val generativeModel = GenerativeModel(
            modelName = "gemini-1.5-flash",
            apiKey = apiKey
        )

        val response = generativeModel.generateContent(request)
        response.text?.let { onResponse(it) } // Devolver la respuesta usando el callback
    }

}