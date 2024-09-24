package com.example.growingtogether.logbook

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.growingtogether.MainApplication
import com.example.growingtogether.R
import com.example.growingtogether.dataclasses.Bebe
import com.example.growingtogether.db.GTDao
import com.example.growingtogether.logbook.components.MoodBarChart
import com.example.growingtogether.logbook.ui.theme.GrowingTogetherTheme
import com.example.growingtogether.ui.theme.gabrielaFontFamily

class LogBookActivity : ComponentActivity() {

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
                    if (baby != null){
                        LogBookView(baby = baby, gtDao);
                    }
                }
            }
        }
    }
}

@Composable
fun LogBookView(baby: Bebe, gtDao: GTDao) {

    val barChartColor: Color = Color(0xFFB0D0E7);
    val context = LocalContext.current;

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
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Title
        Text(
            text = "Bitacora",
            fontFamily = gabrielaFontFamily,
            fontSize = 30.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.White,
            modifier = Modifier.padding(vertical = 5.dp)
        );

        val exampleData = listOf(80f, 100f, 40f, 30f, 50f)
        val days = listOf("L", "M", "M", "J", "V")


        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(10)
                ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){

            Row (
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 25.dp, end = 16.dp, bottom = 10.dp)
            ) {
                Text(text = "Grafica del animo", fontFamily = gabrielaFontFamily, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = "Ultimos 30 dias", fontFamily = gabrielaFontFamily, fontSize = 12.sp);
            }

            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 5.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Box(
                        modifier = Modifier
                            .width(14.dp)
                            .height(5.dp)
                            .background(barChartColor, shape = RoundedCornerShape(12.dp))
                    );

                    Spacer(modifier = Modifier.width(10.dp))


                    Text(text = "Buenas emociones", fontFamily = gabrielaFontFamily, fontSize = 14.sp)
                }

                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Box(
                        modifier = Modifier
                            .width(14.dp)
                            .height(5.dp)
                            .background(
                                barChartColor.copy(alpha = 0.3f),
                                shape = RoundedCornerShape(10)
                            )
                    );

                    Spacer(modifier = Modifier.width(10.dp))

                    Text(text = "Otras emociones", fontFamily = gabrielaFontFamily, fontSize = 14.sp)
                }
            }

            MoodBarChart(data = exampleData, days = days, barChartColor)


        }

        // Button to add log
        Row (
            modifier = Modifier.fillMaxWidth().fillMaxHeight().padding(16.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.Bottom
        ) {
            Button(
                onClick = {
                    // Open the symptoms activity
                    val intent = Intent(context, SymptomsActivity::class.java)
                    intent.putExtra("baby", baby);
                    context.startActivity(intent);
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
}

