package com.devmo.customcomposecomponents.ui.theme

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

@Composable
fun TimerView(
    modifier: Modifier = Modifier,
    totalTime: Long = 1000 * 60,
    handleColor: Color = Color.Blue,
    activeColor: Color = Color.Blue,
    inActiveColor: Color = Color.Gray,
    initialValue: Float = 1.0f,
    strokeWidth: Dp = 5.dp,
    animatedText: Boolean = false,
    onFinished: () -> Unit
) {
    var size by remember {
        mutableStateOf(IntSize.Zero)
    }
    var value by remember {
        mutableStateOf(initialValue)
    }
    var currentTime by remember {
        mutableStateOf(totalTime)
    }
    var isTimerRunning by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(key1 = currentTime, key2 = isTimerRunning) {
        if (currentTime > 0 && isTimerRunning) {
            Log.e("mohammed samy", "TimerView: $currentTime  $isTimerRunning")
            delay(30L)
            currentTime -= 30L
            Log.e("mohammed samy", "TimerView: $currentTime ")
            value = currentTime / totalTime.toFloat()
        } else {
            onFinished()
        }
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .onSizeChanged {
                size = it
            }
            .aspectRatio(1f)
    ) {
        Canvas(modifier = modifier.fillMaxSize()) {
            drawArc(
                color = inActiveColor,
                startAngle = -215f,
                sweepAngle = 250f,
                useCenter = false,
                style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round),
                size = Size(size.width.toFloat(), size.height.toFloat())
            )
            drawArc(
                color = activeColor,
                startAngle = -215f,
                sweepAngle = 250f * value,
                useCenter = false,
                style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round),
                size = Size(size.width.toFloat(), size.height.toFloat())
            )
            val center = Offset(size.width / 2f, size.height / 2f)
            val angle = (250f * value + 145f) * (PI / 180f).toFloat()
            val radios = size.width / 2f
            val a = cos(angle) * radios
            val b = sin(angle) * radios
            drawPoints(
                points =
                listOf(Offset(center.x + a, center.y + b)),
                pointMode = PointMode.Points,
                color = activeColor,
                strokeWidth = (strokeWidth * 3f).toPx(),
                cap = StrokeCap.Round
            )
        }
        if (animatedText) {
            TimerText(count = currentTime)
        } else Text(
            text = (currentTime / 1000f).roundToInt().toString(),
            fontSize = 30.sp,
            color = Color.Blue
        )
        Button(
            onClick = {
                when {
                    currentTime <= 0L -> {
                        currentTime = totalTime
                        isTimerRunning = true
                        value = initialValue
                    }
                    else -> {
                        isTimerRunning = isTimerRunning.not()
                        Log.e("mohammed samy", "TimerView: $isTimerRunning ")
                    }

                }
            },
            colors = ButtonDefaults.buttonColors(
                if (!isTimerRunning || currentTime <= 0) activeColor else Color.Red
            ),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 10.dp)
        ) {
            Text(
                text = when {
                    isTimerRunning && currentTime > 0L -> "Pause"
                    !isTimerRunning && currentTime > 0L -> "Start"
                    else -> "Restart"
                },
                color = Color.White
            )
        }
    }
}


@Preview
@Composable
fun P() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Box(modifier = Modifier.size(250.dp)) {
            TimerView {

            }
        }
    }

}