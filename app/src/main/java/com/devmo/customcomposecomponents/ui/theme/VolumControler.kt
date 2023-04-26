package com.devmo.customcomposecomponents.ui.theme

import android.view.MotionEvent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.roundToInt

@OptIn(ExperimentalComposeUiApi::class)
@Composable

private fun Knob(
    modifier: Modifier = Modifier,
    limitingAngle: Float = 25f,
    painter: Painter = painterResource(id = com.devmo.customcomposecomponents.R.drawable.knob),
    onValueChanged: (Float) -> Unit
) {
    val haptic = LocalHapticFeedback.current
    var rotation by remember {
        mutableStateOf(limitingAngle)
    }

    var touchX by remember {
        mutableStateOf(0f)
    }
    var touchY by remember {
        mutableStateOf(0f)
    }
    var centerX by remember {
        mutableStateOf(0f)
    }
    var centerY by remember {
        mutableStateOf(0f)
    }
    Image(painter = painter,
        contentDescription = "knob",
        modifier = modifier
            .fillMaxSize()
            .onGloballyPositioned {
                val size = it.boundsInWindow().size
                centerX = size.width / 2f
                centerY = size.height / 2f
            }
            .pointerInteropFilter { event ->
                touchX = event.x
                touchY = event.y
                val angle = -atan2(centerX - touchX, centerY - touchY) * (180f / PI).toFloat()
                when (event.action) {
                    MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> if (angle !in -limitingAngle..limitingAngle) {
                        val fixedAngle = if (angle in -180f..-limitingAngle) {
                            360 + angle
                        } else angle
                        rotation = fixedAngle
                        haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                        val percent = (fixedAngle - limitingAngle) / (360f - 2 * limitingAngle)
                        onValueChanged(percent)
                        true
                    } else false
                    else -> false
                }
            }
            .rotate(rotation)
    )
}

@Composable
private fun Bar(
    modifier: Modifier = Modifier,
    activeBars: Int = 0,
    barCount: Int = 20,
    activeBarsColor: Color = Color.Blue,
    inActiveBarsColor: Color = Color.Gray,
    barCornerRadius: Float = 0f
) {
    BoxWithConstraints(
        modifier = modifier
            .padding(horizontal = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        val barWidth = remember {
            constraints.maxWidth / (2f * barCount)
        }
        Canvas(modifier = modifier) {
            for (i in 0..barCount) {
                drawRoundRect(
                    color = if (i in 0..activeBars) activeBarsColor else inActiveBarsColor,
                    topLeft = Offset(i * barWidth * 2f + barWidth / 2f, 0f),
                    size = Size(barWidth, constraints.maxHeight.toFloat()),
                    cornerRadius = CornerRadius(barCornerRadius)
                )
            }
        }
    }
}

/**
 * @author Mohammed Samy
 * @param barCount number of bars
 * @param activeBarsColor the color of active bars
 * @param inActiveBarsColor the color of the inactive bars
 * @param barCornerRadius to make the bars rounded
 * @param limitingAngle angel to block the knob from moving to make limit in the start and end of the cycle
 * @param painter Painter object to set the shape of the knob should be circular
 */
@Composable
fun ControlBar(
    modifier: Modifier = Modifier,
    barCount: Int = 20,
    activeBarsColor: Color = Color.Blue,
    inActiveBarsColor: Color = Color.Gray,
    barCornerRadius: Float = 0f,
    limitingAngle: Float = 25f,
    painter: Painter = painterResource(id = com.devmo.customcomposecomponents.R.drawable.knob),
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, activeBarsColor)
            .padding(horizontal = 10.dp)
            .height(120.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center

    ) {
        Spacer(modifier = Modifier.size(10.dp))
        var volume by remember {
            mutableStateOf(0f)
        }
        Box(modifier = Modifier.size(100.dp)) {
            Knob(
                modifier =Modifier,
                onValueChanged = { volume = it },
                limitingAngle = limitingAngle,
                painter = painter,
            )
        }
        Spacer(modifier = Modifier.size(10.dp))
        Bar(
            activeBars = (barCount * volume).roundToInt(), modifier = Modifier
                .fillMaxWidth()
                .height(30.dp),
            barCount = barCount,
            activeBarsColor = activeBarsColor,
            inActiveBarsColor = inActiveBarsColor,
            barCornerRadius = barCornerRadius
        )
        Spacer(modifier = modifier.size(10.dp))
    }
}
