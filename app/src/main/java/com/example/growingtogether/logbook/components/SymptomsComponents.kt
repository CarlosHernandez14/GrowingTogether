package com.example.growingtogether.logbook.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.growingtogether.R
import com.example.growingtogether.ui.theme.gabrielaFontFamily

@Composable
fun MoodSelection(
    selectedMood: Mood?,   // Parámetro para recibir el estado de ánimo seleccionado
    onMoodSelected: (Mood) -> Unit
) {
    // Lista de estados de ánimo y sus íconos
    val moods = listOf(
        Mood("Satisfacción", R.drawable.ic_satisfaccion), // Reemplaza con tus imágenes
        Mood("Malestar", R.drawable.ic_malestar),
        Mood("Tristeza", R.drawable.ic_tristeza),
        Mood("Interés", R.drawable.ic_interes)
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(10.dp))
            .padding(16.dp)
    ) {
        Text(
            text = "Ánimo",
            fontFamily = gabrielaFontFamily,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFC472C6),
            modifier = Modifier.align(Alignment.Start)
        )

        Spacer(modifier = Modifier.height(20.dp))

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items(moods) { mood ->
                MoodItem(
                    mood = mood,
                    isSelected = mood == selectedMood,
                    onClick = {
                        onMoodSelected(mood)
                    }
                )
            }
        }
    }
}

@Composable
fun MoodItem(
    mood: Mood,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .background(
                    if (isSelected) Color(0xFFC472C6).copy(alpha = 0.2f) else Color.Transparent,
                    shape = CircleShape
                )
                .padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = mood.iconRes),
                contentDescription = mood.name,
                modifier = Modifier.size(60.dp)
            )
        }

        Text(
            text = mood.name,
            fontFamily = gabrielaFontFamily,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = if (isSelected) Color(0xFFC472C6) else Color.Gray
        )
    }
}

// Clase de datos para almacenar los estados de ánimo
data class Mood(val name: String, val iconRes: Int)