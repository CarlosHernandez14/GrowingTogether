package com.example.growingtogether

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import com.example.growingtogether.dataclasses.Bebe
import com.example.growingtogether.db.GTDao
import com.example.growingtogether.ui.theme.GrowingTogetherTheme
import kotlinx.coroutines.launch
import java.util.Date
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.livedata.observeAsState

class ForgotPasswordActivity : ComponentActivity() {
    private val gtDao = MainApplication.gtDatabase.getGTDao();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GrowingTogetherTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BebeForm(this.gtDao)
                }
            }
        }
    }
}

@Composable
fun BebeForm(gtDao: GTDao) {
    var nombre by remember { mutableStateOf("") }
    var fechaNacimiento by remember { mutableStateOf(Date()) }
    var peso by remember { mutableStateOf("") }
    var genero by remember { mutableStateOf('M') }
    var idBitacora by remember { mutableStateOf("") }

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    // Observa el LiveData de los bebés y lo convierte a un estado composable
    val babies: LiveData<List<Bebe>> = gtDao.getAllBabies()
    val babiesList by babies.observeAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Text(text = "Formulario para registrar Bebé")

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = peso,
            onValueChange = { peso = it },
            label = { Text("Peso (kg)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = idBitacora,
            onValueChange = { idBitacora = it },
            label = { Text("ID Bitácora") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Género selector (M o F)
        Row {
            Text(text = "Género: ")
            RadioButton(
                selected = genero == 'M',
                onClick = { genero = 'M' }
            )
            Text(text = "Masculino")
            Spacer(modifier = Modifier.width(8.dp))
            RadioButton(
                selected = genero == 'F',
                onClick = { genero = 'F' }
            )
            Text(text = "Femenino")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if (nombre.isNotEmpty() && peso.isNotEmpty() && idBitacora.isNotEmpty()) {
                val bebe = Bebe(
                    nombre = nombre,
                    fechanacimiento = fechaNacimiento,
                    peso = peso.toDouble(),
                    genero = genero,
                    idBitacora = idBitacora.toInt(),
                    createdAt = Date()
                )

                // Insertar bebé en la base de datos usando una corrutina
                coroutineScope.launch {
                    gtDao.insertBaby(bebe)
                    Toast.makeText(context, "Bebé guardado", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "Por favor llena todos los campos", Toast.LENGTH_SHORT).show()
            }
        }) {
            Text(text = "Guardar Bebé")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Lista de bebés guardados
        Text(text = "Lista de Bebés Registrados")
        LazyColumn {
            items(babiesList) { bebe ->
                Text(text = "Nombre: ${bebe.nombre}, Peso: ${bebe.peso} kg, Género: ${bebe.genero}")
            }
        }
    }
}
