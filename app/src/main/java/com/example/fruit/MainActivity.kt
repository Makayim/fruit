package com.example.fruit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.example.fruit.ui.theme.FruitTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            FruitTheme {
                FruitApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FruitApp() {
    // Menyimpan langkah aktif saat ini (Step 1 sampai 6)
    var currentStep by rememberSaveable { mutableIntStateOf(1) }

    // Hitungan klik yang sudah dilakukan pada langkah saat ini
    var clickCount by rememberSaveable { mutableIntStateOf(0) }

    // Target klik acak untuk Step 1 (2 - 4 kali)
    var targetStep1Clicks by rememberSaveable { mutableIntStateOf((2..4).random()) }

    // Target klik acak untuk Step 2 (2 - 4 kali)
    var targetStep2Clicks by rememberSaveable { mutableIntStateOf((2..4).random()) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Membuat Jus Mangga",
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            color = MaterialTheme.colorScheme.background
        ) {
            when (currentStep) {
                1 -> {
                    // Step 1: Mangga Utuh (Sistem acak 2 - 4 klik)
                    FruitStepLayout(
                        textLabel = "Ketuk mangga utuh ($clickCount / $targetStep1Clicks)",
                        drawableResourceId = R.drawable.mangga_utuh,
                        onImageClick = {
                            clickCount++
                            if (clickCount >= targetStep1Clicks) {
                                currentStep = 2
                                clickCount = 0
                                targetStep2Clicks = (2..4).random() // Acak target untuk step 2
                            }
                        }
                    )
                }

                2 -> {
                    // Step 2: Potong Mangga dengan Pisau (Sistem acak 2 - 4 klik)
                    FruitStepLayout(
                        textLabel = "Ketuk untuk memotong mangga ($clickCount / $targetStep2Clicks)",
                        drawableResourceId = R.drawable.mangga_potong,
                        onImageClick = {
                            clickCount++
                            if (clickCount >= targetStep2Clicks) {
                                currentStep = 3
                                clickCount = 0
                            }
                        }
                    )
                }

                3 -> {
                    // Step 3: Masukkan ke Blender (1 klik)
                    FruitStepLayout(
                        textLabel = "Ketuk blender untuk menghaluskan",
                        drawableResourceId = R.drawable.blender_buah,
                        onImageClick = {
                            currentStep = 4
                        }
                    )
                }

                4 -> {
                    // Step 4: Tuang Jus ke Gelas (1 klik)
                    FruitStepLayout(
                        textLabel = "Ketuk untuk menuangkan jus ke gelas",
                        drawableResourceId = R.drawable.tuang_jus,
                        onImageClick = {
                            currentStep = 5
                            clickCount = 0
                        }
                    )
                }

                5 -> {
                    // Step 5: Minum Jus (3 klik)
                    FruitStepLayout(
                        textLabel = "Ketuk untuk meminum jus (${clickCount + 1} / 3)",
                        drawableResourceId = R.drawable.jus_penuh,
                        onImageClick = {
                            clickCount++
                            if (clickCount >= 3) {
                                currentStep = 6
                                clickCount = 0
                            }
                        }
                    )
                }

                6 -> {
                    // Step 6: Gelas Kosong (1 klik) -> Kembali ke Step 1
                    FruitStepLayout(
                        textLabel = "Gelas sudah kosong! Ketuk untuk membuat lagi",
                        drawableResourceId = R.drawable.gelas_kosong,
                        onImageClick = {
                            currentStep = 1
                            clickCount = 0
                            targetStep1Clicks = (2..4).random() // Acak ulang target step 1
                            targetStep2Clicks = (2..4).random() // Acak ulang target step 2
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun FruitStepLayout(
    textLabel: String,
    drawableResourceId: Int,
    onImageClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Button(
            onClick = onImageClick,
            shape = RoundedCornerShape(24.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            )
        ) {
            Image(
                painter = painterResource(drawableResourceId),
                contentDescription = textLabel,
                modifier = Modifier
                    .size(200.dp)
                    .padding(16.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = textLabel,
            style = MaterialTheme.typography.titleMedium,
            fontSize = 18.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FruitAppPreview() {
    FruitTheme {
        FruitApp()
    }
}