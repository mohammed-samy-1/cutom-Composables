package com.devmo.customcomposecomponents.ui.theme.premiumSwitch

import androidx.compose.animation.animateColor
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devmo.customcomposecomponents.R
import kotlinx.coroutines.delay


@Composable
private fun Clouds(
    modifier: Modifier, color: Color
) {
    BoxWithConstraints {
        val height = constraints.maxHeight.toFloat()
        val radius = listOf(
            height / 6f,
            height / 5f,
            height / 7f,
            height / 4f,
            height / 5f,
            height / 3f,
            height / 2f,
            height / 4f,
            height,
        )
        val centersX = listOf(
            0f,
            height / 6f,
            height / 5f + height / 6f,
            height / 7f + height / 5f + height / 6f,
            height / 4f + height / 5f + height / 6f + height / 7f,
            height / 3f + height / 4f + height / 5f + height / 6f + height / 7f,
            height / 2f + height / 3f + height / 4f + height / 5f + height / 6f + height / 7f,
            height / 4f + height / 2f + height / 3f + height / 4f + height / 5f + height / 6f + height / 7f,
            height + height / 4f + height / 2f + height / 3f + height / 4f + height / 5f + height / 6f + height / 7f,
        )
        Canvas(modifier = modifier.size(100.dp, 50.dp), onDraw = {
            repeat(9) {
                drawCircle(
                    color, radius[it], Offset(centersX[it], if (it == 8) height / 2f else height)
                )
            }
        })
    }
}

@Composable
fun MoonSun(
    modifier: Modifier = Modifier, checked: Boolean,
    animationDelay: Int
) {
    // TODO: animate the moon holes to shoe and hide
    BoxWithConstraints(modifier.aspectRatio(1f)) {
        val height = constraints.maxHeight.toFloat()
        val width = constraints.maxWidth.toFloat()
        var animate by remember { mutableStateOf(false) }
        val hole1X = animateFloatAsState(
            targetValue = if (animate) width / 7f else height / 2,
            animationSpec = tween(durationMillis = 100)
        )
        val hole2X = animateFloatAsState(
            targetValue = if (animate) width / 3f else height / 2,
            animationSpec = tween(durationMillis = 100)
        )
        val hole3X = animateFloatAsState(
            targetValue = if (animate) width / 3f else height / 2,
            animationSpec = tween(durationMillis = 100)
        )
        val hole1Y = animateFloatAsState(
            targetValue = if (animate) height / 2f else height / 2,
            animationSpec = tween(durationMillis = 100)
        )
        val hole2Y =
            animateFloatAsState(
                targetValue = if (animate) height - height / 3f else height / 2,
                animationSpec = tween(durationMillis = 100)
            )
        val hole3Y = animateFloatAsState(
            targetValue = if (animate) height / 4f else height / 2,
            animationSpec = tween(durationMillis = 100)
        )
        LaunchedEffect(key1 = checked, block = {
            delay(animationDelay.toLong() + 200)
            animate = true
        })
        Canvas(modifier = modifier, onDraw = {
            if (!checked) { // sun
                val brush = Brush.linearGradient(
                    listOf(Color(0xFFf8f776), Color(0xFFf3a000))
                )
                drawCircle(
                    brush,
                    height / 2f - 7f,
                    Offset(height / 2f, height / 2f)
                )
                drawCircle(
                    Color(0xFFf5d453),
                    height / 2f - 10f,
                    Offset(height / 2f, height / 2f)
                )
            } else {

                val brush = Brush.linearGradient(
                    listOf(Color(0xFFfeffff), Color(0xFF95a4b7))
                )
                drawCircle(
                    brush,
                    height / 2f - 7f,
                    Offset(height / 2f, height / 2f)
                )
                drawCircle(
                    Color(0xFFd3d8dd),
                    height / 2f - 10f,
                    Offset(height / 2f, height / 2f)
                )

            }
        })
        if (checked) {
            Box(
                modifier.graphicsLayer {
                    translationX = height /6
                }

            ) {
                MoonHole(radius = height / 8, center = Offset(hole1X.value, hole1Y.value))
                MoonHole(radius = height / 12, center = Offset(hole2X.value, hole2Y.value))
                MoonHole(radius = height / 15, center = Offset(hole3X.value, hole3Y.value))
            }
        }
    }

}

@Composable
private fun MoonHole(
    modifier: Modifier = Modifier,
    radius: Float,
    center: Offset
) {
    Canvas(modifier = modifier, onDraw = {
        drawCircle(
            color = Color(0xFFa3b2c3),
            radius = radius,
            center = center
        )
    })
}

@Composable
fun SunMoonContents(
    modifier: Modifier = Modifier,
    checked: Boolean = false,
    onCheckedChange: (Boolean) -> Unit,
    animationDuration: Int = 400
) {
    var switchState by remember {
        mutableStateOf(checked)
    }
    val height = 50f
    var showLight by remember {
        mutableStateOf(true)
    }
    val background =
        animateColorAsState(
            targetValue = if (switchState) Color(0xFF262e38) else Color(0xFF4a8ecc),
            animationSpec = tween(animationDuration),
            finishedListener = { showLight = true }
        )

    val radiosScale = animateFloatAsState(
        targetValue = if (showLight) 1f else 0f,
        animationSpec = tween(
            durationMillis = 200
        )
    )
    val translateCloudsLayer1 = animateFloatAsState(
        targetValue = if (switchState) height * 4.5f else -0f,
        animationSpec = tween(durationMillis = 100)
    )
    val translateCloudsLayer2 = animateFloatAsState(
        targetValue = if (switchState) height * 4.5f else -0f,
        animationSpec = tween(durationMillis = 200, delayMillis = 10)

    )

    Surface(
        shape = RoundedCornerShape(50), elevation = 10.dp, modifier = Modifier
            .width(150.dp)
            .height(55.dp)
            .clickable {
                switchState = !switchState
                showLight = false
                onCheckedChange(switchState)
            }

    ) {
        BoxWithConstraints(
            Modifier.background(background.value),
            contentAlignment = Alignment.CenterStart
        ) {
            val xOffset =
                animateFloatAsState(
                    targetValue = if (switchState) maxWidth.value - maxHeight.value * 1.25f else 0f,
                    animationSpec = tween(durationMillis = animationDuration)
                )

            Clouds(
                Modifier
                    .offset(y = (-7).dp, x = (5).dp)
                    .graphicsLayer { translationY = translateCloudsLayer1.value }, Color(0xFFabd3f4)
            )
            Clouds(
                Modifier
                    .offset(y = 3.dp, x = 20.dp)
                    .graphicsLayer {
                        translationY = translateCloudsLayer2.value
                    }, Color(0xFFd9ebfa)
            )
            Canvas(modifier = Modifier, onDraw = {
                repeat(3) {
                    drawCircle(
                        Color(0xDFFFFFF),
                        radius = height + (it + 1) * (height / 1.5f) * radiosScale.value,
                        Offset(constraints.maxHeight / 2f + xOffset.value * 1.9f, 0f)
                    )
                }

            })
            if(switchState){
                Star(radius = 3f , circle = true , offset = Offset(maxWidth.value/10f , 0f))
                Star(radius = 5f , circle = true , offset = Offset(maxWidth.value/5f , maxHeight.value/4))
                Star(radius = 7f , circle = false , offset = Offset(maxWidth.value/5f , -maxHeight.value/4))
                Star(radius = 4f , circle = true , offset = Offset(maxWidth.value/3f , 0f))
                Star(radius = 2f , circle = true , offset = Offset(maxWidth.value/2.25f , maxHeight.value/15))
                Star(radius = 3f , circle = true , offset = Offset(maxWidth.value/2.25f , maxHeight.value/-4))
                Star(radius = 3f , circle = true , offset = Offset(maxWidth.value/2f , maxHeight.value/-8))
                Star(radius = 7f , circle = false , offset = Offset(maxWidth.value/1.75f , maxHeight.value/3))
                Star(radius = 2f , circle = true , offset = Offset(maxWidth.value/1.70f , maxHeight.value/30))
                Star(radius = 8f , circle = false , offset = Offset(maxWidth.value/1.60f , maxHeight.value/-3))
            }
            MoonSun(
                modifier = Modifier
                    .height(maxHeight.value.dp)
                    .graphicsLayer {
                        translationX = xOffset.value
                    }, checked = switchState, animationDuration
            )

        }

    }
}



@Composable
fun Star(radius: Float, circle: Boolean , offset: Offset = Offset(0f,0f)) {
    val infiniteTransition = rememberInfiniteTransition()
    val starlight by infiniteTransition.animateColor(
        initialValue = Color(0x1ACBCFD5),
        targetValue = Color(0xFFcbcfd5),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 400),
            RepeatMode.Reverse
        )
    )
    if (circle)
        Canvas(modifier = Modifier
            .offset(offset.x.dp, offset.y.dp), onDraw = {
            drawCircle(
                Color(0xFFcbcfd5),
                radius / 5
            )
            drawCircle(
                starlight,
                radius
            )
        })
    else {
        Icon(
            painter = painterResource(id = R.drawable.star), contentDescription = "Star",
            tint = starlight,
            modifier = Modifier
                .size(radius.dp)
                .offset(offset.x.dp, offset.y.dp)
        )
    }
}

@Preview
@Composable
fun PremiumSwitchPrev() {
    val checked by remember {
        mutableStateOf(false)
    }
    SunMoonContents(
        modifier = Modifier,
        checked,
        onCheckedChange = {}
    )
}