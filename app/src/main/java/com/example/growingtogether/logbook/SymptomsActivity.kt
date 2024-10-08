package com.example.growingtogether.logbook

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.growingtogether.InputCard
import com.example.growingtogether.MainApplication
import com.example.growingtogether.R
import com.example.growingtogether.dataclasses.Bebe
import com.example.growingtogether.dataclasses.Usuario
import com.example.growingtogether.db.GTDao
import com.example.growingtogether.logbook.components.Mood
import com.example.growingtogether.logbook.components.MoodSelection
import com.example.growingtogether.logbook.ui.theme.GrowingTogetherTheme
import com.example.growingtogether.ui.theme.gabrielaFontFamily

class SymptomsActivity : ComponentActivity() {

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
                    val baby = intent.getSerializableExtra("baby") as? Bebe;
                    val user = intent.getSerializableExtra("user") as? Usuario
                    if(baby != null && user != null) {
                        SymptomsView(baby = baby, gtDao = gtDao, user = user);
                    }
                }
            }
        }
    }
}

@Composable
fun SymptomsView(baby: Bebe, user: Usuario, gtDao: GTDao) {
    val context = LocalContext.current
    // Variable para almacenar el estado de ánimo seleccionado
    var selectedMood by remember { mutableStateOf<Mood?>(null) }
    var selectedMoodParent by remember { mutableStateOf<Mood?>(null) }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    listOf(
                        Color(0xFFC472C6),
                        Color(0xFFD0AAD1),
                        Color(0xFFF9E0E3),
                        Color(0xFFECC7CE)
                    )
                )
            ),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        // Aquí usamos weight para darle prioridad al contenido y dejar espacio para el botón
        Column(
            modifier = Modifier
                .weight(1f) // El contenido principal ocupa el espacio disponible
                .padding(20.dp)
                .verticalScroll(rememberScrollState()), // Hacemos el contenido scrolleable
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {

            // Contenido principal

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Box (
                    modifier = Modifier.background(Color.White, shape = CircleShape)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.bebe_bitacora),
                        contentDescription = "logo bebe bitacora",
                        modifier = Modifier
                            .size(70.dp),
                        contentScale = ContentScale.Fit
                    )
                }

                Spacer(modifier = Modifier.width(15.dp))

                Text(
                    text = baby.nombre,
                    fontFamily = gabrielaFontFamily,
                    fontSize = 30.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(10.dp))
            AddSymptomsBox()

            Spacer(modifier = Modifier.height(10.dp))

            val moods = listOf(
                Mood("Satisfacción", R.drawable.ic_satisfaccion),
                Mood("Malestar", R.drawable.ic_malestar),
                Mood("Tristeza", R.drawable.ic_tristeza),
                Mood("Interés", R.drawable.ic_interes)
            )

            MoodSelection(
                selectedMood = selectedMood,
                onMoodSelected = { mood ->
                    selectedMood = mood
                },
                moods = moods
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Ánimo del padre",
                    modifier = Modifier.size(70.dp),
                    tint = Color.White
                )

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = user.nombre,
                    fontFamily = gabrielaFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                    color = Color.White
                )
            }

            val moodsParent = listOf(
                Mood("Feliz", R.drawable.ic_p_feliz),
                Mood("Enojado", R.drawable.ic_p_enojado),
                Mood("Cansado", R.drawable.ic_p_cansado),
                Mood("Preocupado", R.drawable.ic_p_preocupado),
                Mood("Nervioso", R.drawable.ic_p_nervioso),
                Mood("Triste", R.drawable.ic_p_triste)
            )
            MoodSelection(
                selectedMood = selectedMoodParent,
                onMoodSelected = { mood ->
                    selectedMoodParent = mood
                },
                moods = moodsParent
            )

        }

        // Botón en la parte inferior con espacio disponible
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom
        ) {
            Button(
                onClick = {
                    // De momento solo regresa al activity anterior
                    (context as? Activity)?.finish()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFC472C6),
                    contentColor = Color.White
                )
            ) {
                Text("Agregar")
            }
        }
    }
}

@Composable
fun AddSymptomsBox() {

    // Estado para manejar el texto ingresado en el AlertDialog
    var symptomText by remember { mutableStateOf("") }
    // Estado para mostrar u ocultar el AlertDialog
    var showDialog by remember { mutableStateOf(false) }
    // Estado para guardar los síntomas
    val symptomsList = remember { mutableStateListOf<String>() }

    Column (
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(10.dp))
    ) {
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "Sintomas", fontFamily = gabrielaFontFamily, fontWeight = FontWeight.Bold, fontSize = 20.sp, color = Color(0xFFC472C6))

            Spacer(modifier = Modifier.height(5.dp))

            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
                        // Open the alert dialog to add symptom
                        showDialog = true;
                    },
                    modifier = Modifier
                        .size(45.dp)
                        .padding(vertical = 1.dp),  // Evitar la forma ovalada
                    shape = CircleShape,
                    contentPadding = PaddingValues(0.dp),  // Evitar espacio para el icono
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFC472C6).copy(alpha = 0.6f), // Color de fondo
                        contentColor = Color.White // Color del contenido (icono)
                    ),
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Agregar")
                }

                Spacer(modifier = Modifier.width(15.dp))

                Text(text = "Agregar nota", fontFamily = gabrielaFontFamily, fontWeight = FontWeight.Bold, fontSize = 25.sp, color = Color(0xFFC472C6));
            }

            Spacer(modifier = Modifier.height(10.dp))

            // LazyColumn para mostrar la lista de síntomas guardados
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .drawWithContent {
                        // Dibuja el contenido de la LazyColumn
                        drawContent()

                        // Dibuja la sombra superior
                        drawRect(
                            brush = Brush.verticalGradient(
                                colors = listOf(Color.Black.copy(alpha = 0.1f), Color.Transparent),
                                startY = 0f,
                                endY = 20f
                            ),
                            size = size
                        )

                        // Dibuja la sombra inferior
                        drawRect(
                            brush = Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.1f)),
                                startY = size.height - 20f,
                                endY = size.height
                            ),
                            size = size
                        )
                    },
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(symptomsList) { symptom ->
                    SymptomItem(symptom = symptom)
                }
            }

        }
    }

    // AlertDialog para agregar síntomas
    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
                showDialog = false
            },
            title = {
                Text(text = "Agregar síntoma", fontFamily = gabrielaFontFamily, fontWeight = FontWeight.Bold, color = Color(0xFFC472C6))
            },
            text = {
                Column {
                    InputCard(
                        cardName = "Escribe el sintoma",
                        inputValue = symptomText,
                        onValueChange = { symptomText = it }
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (symptomText.isNotBlank()) {
                            symptomsList.add(symptomText) // Guardar el síntoma
                            symptomText = "" // Limpiar el campo
                        }
                        showDialog = false // Cerrar el diálogo
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFC472C6), // Color de fondo
                        contentColor = Color.White // Color del contenido (icono)
                    )
                ) {
                    Text("Guardar")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        showDialog = false // Cerrar el diálogo sin guardar
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFC472C6), // Color de fondo
                        contentColor = Color.White // Color del contenido (icono)
                    )
                ) {
                    Text("Cancelar")
                }
            }
        )
    }
}


@Composable
fun SymptomItem(symptom: String) {
    Card(
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF1E1F5) // Color de fondo de la tarjeta
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Favorite, // Puedes cambiar el ícono según prefieras
                contentDescription = "Síntoma",
                tint = Color(0xFFC472C6),
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = symptom,
                fontFamily = gabrielaFontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp,
                color = Color.Black
            )
        }
    }
}
