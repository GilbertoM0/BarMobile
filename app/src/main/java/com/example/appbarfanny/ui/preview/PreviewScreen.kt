package com.example.appbarfanny.ui.preview

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward // Necesario para el ícono
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset // Necesario para la sombra
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow // Necesario para la sombra
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle // Necesario para la sombra
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
// ¡Recuerda que si el GIF sigue estático, debes intentar descomentar la línea del decodificador!
// import coil.decode.ImageDecoderDecoder
import com.example.appbarfanny.R
import com.example.appbarfanny.navegation.AppScreens

@Composable
fun PreviewScreen(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize()) {

        // 1. Imagen de Fondo
        Image(
            painter = painterResource(id = R.drawable.fondobar),
            contentDescription = "Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // 2. Capa Oscura (Overlay) para Mejorar el Contraste
        Box(
            modifier = Modifier
                .fillMaxSize()
                // Aplica una capa negra semi-transparente (50% de opacidad)
                .background(Color.Black.copy(alpha = 0.5f))
        )

        // Contenedor principal para centrar el texto, el botón y el GIF
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 40.dp), // Padding vertical para no pegar el contenido a los bordes
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // 3. Texto de Bienvenida Mejorado (con Sombra)
            Text(
                text = "¡Bienvenido al Bar!",
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White,
                modifier = Modifier.padding(horizontal = 32.dp),
                style = TextStyle(
                    shadow = Shadow(
                        color = Color.Black.copy(alpha = 0.8f),
                        offset = Offset(3f, 3f), // Sombra sutil
                        blurRadius = 6f
                    )
                )
            )

            Spacer(modifier = Modifier.height(50.dp)) // Espacio entre el texto y el GIF

            // 4. GIF de Cerveza (Usando tu URL y tamaño más grande)
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://media.tenor.com/C8jvmBQk04gAAAAj/tagay-shot.gif")
                    .crossfade(true)
                    // Si el GIF sigue estático, intenta descomentar la línea de abajo:
                    // .decoderFactory(ImageDecoderDecoder.Factory())
                    .build(),
                contentDescription = "GIF de Cerveza Animado",
                modifier = Modifier
                    .size(200.dp) // Hacemos el GIF más grande para que sea protagonista
                    .padding(20.dp) // Espacio alrededor del GIF
            )

            Spacer(modifier = Modifier.height(50.dp)) // Espacio entre el GIF y el botón

            // 5. Botón Mejorado con Texto e Icono
            Button(
                onClick = {
                    navController.popBackStack()
                    navController.navigate(AppScreens.HomeView.route)
                },
                modifier = Modifier
                    .fillMaxWidth(0.8f) // Ocupa el 80% del ancho de la pantalla
                    .height(55.dp),
                shape = RoundedCornerShape(10.dp), // Bordes menos agresivos
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF00B0FF), // Azul vivo
                    contentColor = Color.White
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
            ) {
                // *** SOLUCIÓN: Usamos ROW para alinear el Texto y el Icono ***
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Entrar",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                    Spacer(modifier = Modifier.width(8.dp)) // Espacio entre el texto y el ícono
                    Icon(
                        Icons.Filled.ArrowForward,
                        contentDescription = "Continuar",
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}