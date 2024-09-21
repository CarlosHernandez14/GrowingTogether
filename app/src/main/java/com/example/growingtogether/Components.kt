package com.example.growingtogether

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

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


@Composable
fun DatePickerInput(
    label: String,
    selectedDate: Date,
    onDateSelected: (Date) -> Unit
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    // Formato de fecha para mostrar en el Text
    val sdf = remember { SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()) }

    // Mostrar el diálogo de selección de fecha
    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            val newDate = Calendar.getInstance()
            newDate.set(year, month, dayOfMonth)
            onDateSelected(newDate.time)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)) {
        Text(text = "$label: ${sdf.format(selectedDate)}", modifier = Modifier.padding(bottom = 8.dp))
        Button(
            onClick = {
                datePickerDialog.show() // Mostrar el DatePicker cuando se presiona el botón
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFC472C6), // Color de fondo
                contentColor = Color.White // Color del contenido (icono)
            ),
        ) {
            Text("Seleccionar Fecha")
        }
    }
}

@Composable
fun ConfirmationDialog(
    showDialog: MutableState<Boolean>,  // Para mostrar u ocultar el diálogo
    onConfirm: () -> Unit,  // Acción cuando el usuario confirma
    onDismiss: () -> Unit,  // Acción cuando el usuario cancela
    title: String = "Confirmación",
    message: String = "¿Estás seguro que deseas continuar?"
) {
    if (showDialog.value) {  // Solo mostramos el diálogo si showDialog es true
        AlertDialog(
            onDismissRequest = {
                showDialog.value = false  // Cerrar el diálogo al hacer clic fuera de él
                onDismiss()
            },
            title = {
                Text(text = title)
            },
            text = {
                Text(text = message)
            },
            confirmButton = {

                Row (
                    modifier = Modifier.fillMaxWidth().padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = {
                            showDialog.value = false  // Cerrar el diálogo al confirmar
                            onConfirm()  // Ejecutar la acción de confirmación
                        }
                    ) {
                        Text("Confirmar")
                    }

                    Button(
                        onClick = {
                            showDialog.value = false  // Cerrar el diálogo al cancelar
                            onDismiss()  // Ejecutar la acción de cancelación
                        }
                    ) {
                        Text("Cancelar")
                    }
                }
            },
        )
    }
}
