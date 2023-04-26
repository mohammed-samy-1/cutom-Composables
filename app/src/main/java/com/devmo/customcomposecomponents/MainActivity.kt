package com.devmo.customcomposecomponents

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.UiComposable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devmo.customcomposecomponents.ui.theme.ControlBar
import com.devmo.customcomposecomponents.ui.theme.CustomComposeComponentsTheme
import com.devmo.customcomposecomponents.ui.theme.CustomProgressBar
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CustomComposeComponentsTheme {
                // A surface container using the 'background' color from the theme
                Ui()
            }
        }
    }
}

@Composable
fun Ui() {
    Box(
        Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(horizontal = 15.dp, vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(){
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                CustomProgressBar(percentage = .9f, number = 100)
                Spacer(modifier = Modifier.size(30.dp))
                CustomProgressBar(
                    percentage = .7f,
                    number = 100,
                    animationDelay = 500
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Box(modifier = Modifier.fillMaxWidth()) {
                ControlBar(onValueChanged = {})
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CustomComposeComponentsTheme {
        Ui()
    }
}