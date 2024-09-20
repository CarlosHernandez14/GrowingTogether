package com.example.growingtogether

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

// Input
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputCard(cardName: String, inputValue: String, onValueChange: (String) -> Unit) {
    // Email input
    Card(
        shape = RoundedCornerShape(8.dp), // Ajustar el redondeo de las esquinas
        elevation = CardDefaults.cardElevation(5.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp) // Para que el campo ocupe el ancho del padre
    ) {
        OutlinedTextField(
            value = inputValue,
            onValueChange = onValueChange, // Actualizar el valor del estado externo
            label = { Text(text = cardName) },
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp), // Redondear el borde del campo de texto
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Transparent, // Elimina el borde al enfocar
                unfocusedBorderColor = Color.Transparent, // Elimina el borde sin enfoque
            )
        )
    }
}