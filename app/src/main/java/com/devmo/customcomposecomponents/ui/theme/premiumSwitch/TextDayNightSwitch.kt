package com.devmo.customcomposecomponents.ui.theme.premiumSwitch

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TextDayNightSwitch(
    modifier: Modifier = Modifier,
    checked: Boolean = false,
    onCheckedChangeListener: (Boolean) -> Unit,
    animationDuration: Int = 500
) {
    var switchState by remember {
        mutableStateOf(checked)
    }
    val background =
        animateColorAsState(
            targetValue = if (!switchState) Color(0xFFd0d0d0) else Color(0xFF0a1929),
            animationSpec = tween(animationDuration)
        )
    val color1 = animateColorAsState(
        targetValue = if (switchState) Color(0xFF8983F7) else Color(0xFFf7195e),
        animationSpec = tween(durationMillis = animationDuration)
    )

    val color2 = animateColorAsState(
        targetValue = if (switchState) Color(0xFFBBC4C9) else Color(0xFFCDDC39),
        animationSpec = tween(durationMillis = animationDuration)
    )
    val boxGradient = Brush.radialGradient(
        listOf(
            color1.value, color2.value,
        ),
        radius = 180f
    )
    val shColor1 = animateColorAsState(
        targetValue = if (switchState) Color(0x6653569E) else Color(0xFFe7e76c),
        animationSpec = tween(durationMillis = animationDuration)
    )
    val shColor2 = animateColorAsState(
        targetValue = if (switchState) Color(0x6653569E) else Color(0x66E7E76C),
        animationSpec = tween(durationMillis = animationDuration)
    )

    val shadow = Brush.radialGradient(
        listOf(shColor1.value, shColor2.value),
        radius = 200f
    )
    Surface(shape = RoundedCornerShape(50),
        elevation = 10.dp,
        modifier = modifier
            .width(150.dp)
            .height(55.dp)
            .clickable {
                switchState = !switchState
                onCheckedChangeListener(switchState)
            }) {
        BoxWithConstraints(
            Modifier
                .fillMaxSize()
                .background(background.value)
        ) {
            val height = constraints.maxHeight
            val width = constraints.maxWidth
            val translationX = animateFloatAsState(
                targetValue = if (switchState) width / 2f else 0f,
                animationSpec = tween(animationDuration)
            )
            val extraSize = animateFloatAsState(
                targetValue =if(switchState) 20f else 0f,
                animationSpec = tween(animationDuration)
            )

            Canvas(modifier = Modifier
                .wrapContentWidth()
                .graphicsLayer {
                    this@graphicsLayer.translationX =
                        translationX.value - extraSize.value
                }, onDraw = {

                drawRoundRect(
                    shadow,
                    Offset(0f, 0f),
                    Size(width = width / 2f + extraSize.value, height = height.toFloat()),
                    cornerRadius = CornerRadius(80f, 80f)
                )
                drawRoundRect(
                    boxGradient,
                    Offset(10f, 10f),
                    Size(width = width / 2f + extraSize.value - 20 , height = height.toFloat() - 20f),
                    cornerRadius = CornerRadius(80f, 80f)
                )
            })
            Row(
                Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Text(text = "Day", color = if (switchState) Color.White else Color.Black)
                Text(text = "Night", color = if (switchState) Color.White else Color.Black)
            }
        }
    }
}

@Preview
@Composable
fun TextSwitchPrev() {
    TextDayNightSwitch(onCheckedChangeListener = {})
}