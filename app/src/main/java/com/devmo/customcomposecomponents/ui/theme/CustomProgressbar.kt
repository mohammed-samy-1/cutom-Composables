package com.devmo.customcomposecomponents.ui.theme

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/***
 * @author Mohammed Samy
 * @param percentage float value between 0 , 1  to set the loading you can use mutableState to change it depending on a task
 * @param number max value of the progress for example if you set it to 10 the loading progress will be from 0 to 10
 * @param strokeColor th color of the progress stroke
 * @param textColor the color of the progress text
 * @param fontSize progress text fontSize
 * @param 
 */
@Composable
fun CustomProgressBar(
    percentage: Float,
    number: Int,
    strokeColor: Color = Color.Blue,
    textColor: Color = Color.Blue,
    fontSize: TextUnit = 28.sp,
    radios: Dp = 50.dp,
    strokeWidth: Dp = 8.dp,
    fill: Boolean = false,
    roundStroke: Boolean = false,
    animationDuration: Int = 2000,
    animationDelay: Int = 0
) {
    var animationPlayed by remember {
        mutableStateOf(false)
    }
    val currentPercentage = animateFloatAsState(
        targetValue = if (animationPlayed) percentage else 0f, animationSpec = tween(
            durationMillis = animationDuration, delayMillis = animationDelay
        )
    )
    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }
    Box(modifier = Modifier.size(radios * 2), contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.size(radios * 2)) {
            drawArc(
                color = strokeColor,
                startAngle = -90f,
                sweepAngle = (currentPercentage.value * 360f),
                useCenter = fill,
                style = if (fill) Fill else Stroke(
                    width = strokeWidth.toPx(),
                    cap = if (roundStroke) StrokeCap.Round else StrokeCap.Butt
                )
            )
        }
        Text(
            text = (currentPercentage.value * number).toInt().toString(),
            fontSize = fontSize,
            fontWeight = FontWeight.Bold,
            color = textColor
        )
    }
}
