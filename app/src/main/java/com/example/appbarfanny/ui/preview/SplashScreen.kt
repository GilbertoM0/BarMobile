package com.example.appbarfanny.ui.preview

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appbarfanny.R
import com.example.appbarfanny.ui.theme.AppBarFannyTheme
import com.example.appbarfanny.ui.theme.Purple40
import com.example.appbarfanny.ui.theme.PurpleGrey80

@Composable
fun SplashScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PurpleGrey80),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.cerveza_jarro),
            contentDescription = "Bar Logo",
            modifier = Modifier.size(250.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Bar",
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold,
            color = Purple40
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    AppBarFannyTheme {
        SplashScreen()
    }
}