package com.example.growingtogether

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import com.example.growingtogether.dataclasses.Bebe
import com.example.growingtogether.dataclasses.Usuario
import com.example.growingtogether.db.GTDao
import com.example.growingtogether.ui.theme.GrowingTogetherTheme
import com.example.growingtogether.ui.theme.gabrielaFontFamily
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.launch
import java.util.Date

class HomeActivity : ComponentActivity() {

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
                    val user = intent.getSerializableExtra("user") as? Usuario;
                    if (user != null) {
                        HommeView(user, gtDao)
                    }
                }
            }
        }
    }
}

@Composable
fun HommeView(user: Usuario, gtDao: GTDao) {
    val context = LocalContext.current;
    val babies : LiveData<List<Bebe>> = gtDao.getBabiesFromUser(user.id);
    val babiesList by babies.observeAsState(initial = emptyList())

    val coroutineScope = rememberCoroutineScope();

    var showDialog by remember {
        mutableStateOf(false)
    }

    // Variables to register form state
    var name by remember {
        mutableStateOf("")
    }

    var birthDate by remember {
        mutableStateOf(Date())
    }

    var genre by remember {
        mutableStateOf("")
    }

    var babieWeight by remember {
        mutableStateOf("")
    }

    var edad by remember {
        mutableStateOf("")
    }

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
                Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "Profile",
                    Modifier
                        .width(50.dp)
                        .height(40.dp))
            }
        }


        if (babiesList.isEmpty()) {
            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.8f),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "Aun no tienes registrado a ningun bebe, agrega uno para comenzar",
                    fontFamily = gabrielaFontFamily,
                    fontSize = 30.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 50.sp,
                    modifier = Modifier.padding(10.dp, 0.dp),
                    color = Color.White
                )

                Image(
                    painter = painterResource(id = R.drawable.logo_bebe_main),
                    contentDescription = "logo bebe",
                    modifier = Modifier
                        .size(800.dp)  // Cambia el tamaño del logo
                        .padding(top = 25.dp),  // Añade un margen superior
                    contentScale = ContentScale.Fit
                )
            }
        } else {
            // Lazy column with the babies list
            LazyColumn {
                items(babiesList) {bebe ->
                    BabieCard(babie = bebe, gtDao)
                }
            }
        }

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(
                    10.dp, 10.dp
                ),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                onClick = {
                    // Abrimos el modal
                    showDialog = true
                },
                modifier = Modifier.size(60.dp),  // Evitar la forma ovalada
                shape = CircleShape,
                border = BorderStroke(1.dp, Color(0xFFC472C6)), // Borde similar a un OutlinedButton
                contentPadding = PaddingValues(0.dp),  // Evitar espacio para el icono
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFC472C6), // Color de fondo
                    contentColor = Color.White // Color del contenido (icono)
                ),
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar")
            }
        }

    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Registrar un nuevo bebé") },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Nombre del bebé
                    InputCard(
                        cardName = "Nombre",
                        inputValue = name,
                        onValueChange = { name = it }
                    )

                    // Fecha de nacimiento con DatePicker
                    DatePickerInput(
                        label = "Fecha de Nacimiento",
                        selectedDate = birthDate,
                        onDateSelected = { birthDate = it }
                    )

                    // Género del bebé
                    InputCard(
                        cardName = "Género (M/F)",
                        inputValue = genre,
                        onValueChange = { genre = it }
                    )

                    // Peso del bebé
                    InputCard(
                        cardName = "Peso (kg)",
                        inputValue = babieWeight,
                        onValueChange = { babieWeight = it }
                    )
                }
            },
            confirmButton = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(onClick = {
                            // Lógica para guardar el bebé en la base de datos
                            if (name.isNotEmpty() && babieWeight.isNotEmpty() && genre.isNotEmpty()) {
                                coroutineScope.launch {
                                    gtDao.insertBaby(
                                        Bebe(
                                            idUsuario = user.id,
                                            nombre = name,
                                            fechanacimiento = birthDate,
                                            peso = babieWeight.toDoubleOrNull() ?: 0.0,
                                            genero = genre.firstOrNull() ?: 'M', // Por defecto 'M' si no se especifica
                                            idBitacora = 0, // Cambia esto según sea necesario
                                            createdAt = Date()
                                        )
                                    )

                                    Toast.makeText(context, "Bebé $name guardado", Toast.LENGTH_SHORT).show()
                                }
                            } else {
                                Toast.makeText(context, "Por favor llena todos los campos", Toast.LENGTH_LONG).show()
                            }

                            showDialog = false // Cerrar el diálogo después de registrar
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFC472C6), // Color de fondo
                            contentColor = Color.White // Color del contenido (icono)
                        ),
                    ) {
                        Text("Confirmar")
                    }

                    Button(
                        onClick = {
                            showDialog = false
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Red, // Color de fondo
                            contentColor = Color.White // Color del contenido (icono)
                        ),
                    ) {
                        Text("Cancelar")
                    }
                }
            }
        )
    }
}

@Composable
fun BabieCard(babie: Bebe, gtDao: GTDao) {
    
    val coroutineScope = rememberCoroutineScope();

    val showConfirmation = remember {
        mutableStateOf(false)
    }
    Surface(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        color = Color(0xFFF0F8FF),
        shadowElevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .background(
                    brush = Brush.horizontalGradient(
                        listOf(
                            Color(0xFFE6F7FF),
                            Color(0xFFD1EEFF)
                        )
                    )
                )
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Imagen circular del bebé (puedes sustituir con un avatar personalizado o la imagen del bebé)
            Image(
                painter = painterResource(id = R.drawable.logo_bebe_main), // Placeholder
                contentDescription = "Avatar del bebé",
                modifier = Modifier
                    .size(80.dp)
                    .background(Color.White, CircleShape)
                    .padding(2.dp)
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Nombre del bebé
                    Text(
                        text = babie.nombre,
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 20.sp,
                        fontFamily = gabrielaFontFamily,
                        color = Color(0xFF4B91CE)
                    )

                    // Button de eliminar
                    IconButton(onClick = {
                        // MOSTRAMOS LA CONFIRMACION
                        showConfirmation.value = true
                    }) {
                        Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                    }
                }

                // Fecha de nacimiento
                Text(
                    text = "Nacimiento: ${babie.fechanacimiento}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF4B91CE)
                )

                // Peso del bebé
                Text(
                    text = "Peso: ${babie.peso} kg",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF4B91CE)
                )

                // Género
                Text(
                    text = "Género: ${babie.genero}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF4B91CE)
                )
            }
        }
    }

    val context = LocalContext.current
    ConfirmationDialog(
        showDialog = showConfirmation,
        onConfirm = {
            
            coroutineScope.launch { 
                gtDao.deleteBaby(babie.id)

                Toast.makeText(context, "Se elimino el bebe ", Toast.LENGTH_LONG).show()
            }
        },
        onDismiss = {
        }
    )
}

