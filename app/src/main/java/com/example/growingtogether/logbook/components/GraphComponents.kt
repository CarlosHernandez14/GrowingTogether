package com.example.growingtogether.logbook.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MoodBarChart(data: List<Float>, days: List<String>, color: Color) {
    val barColors = listOf(
        color,
        color,
        color,
        color,
        color
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.Top,  // Alinear en la parte superior
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Columna de los días y las barras
        Column(
            modifier = Modifier
                .fillMaxWidth(0.8f)
        ) {
            data.forEachIndexed { index, value ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .fillMaxWidth()
                ) {
                    // Texto del día
                    Text(
                        text = days.getOrNull(index) ?: "",
                        fontSize = 16.sp,
                        modifier = Modifier.width(40.dp)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    // Barra de estado de ánimo con sombreado
                    // Fondo pálido detrás de la barra
                    Box(
                        modifier = Modifier
                            .height(24.dp)
                            .fillMaxWidth()  // Fondo completo en el ancho disponible
                            .background(
                                color.copy(alpha = 0.3f),  // Fondo morado semitransparente
                                shape = RoundedCornerShape(0.dp)
                            )
                    ) {
                        // Barra de estado de ánimo con sombreado
                        Box(
                            modifier = Modifier
                                .height(24.dp)
                                .fillMaxWidth(value / 100)  // Ajustar tamaño de la barra según el valor
                                .background(
                                    brush = Brush.horizontalGradient(
                                        colors = listOf(
                                            barColors[index % barColors.size],
                                            barColors[index % barColors.size].copy(alpha = 0.6f)  // Sombreado de la barra
                                        )
                                    ),
                                    shape = RoundedCornerShape(0.dp, 12.dp, 12.dp, 0.dp)
                                )
                        )
                    }

                }
            }
        }

        // Columna de los porcentajes
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            data.forEachIndexed { index, value ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    // Línea de separación
                    Divider(
                        color = Color.Gray,
                        modifier = Modifier
                            .height(24.dp)
                            .width(1.dp)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    // Texto del porcentaje
                    Text(
                        text = "${value.toInt()}%",  // Mostrar el valor como porcentaje
                        fontSize = 16.sp,
                        modifier = Modifier.width(48.dp),
                        maxLines = 1
                    )
                }
            }
        }
    }
}