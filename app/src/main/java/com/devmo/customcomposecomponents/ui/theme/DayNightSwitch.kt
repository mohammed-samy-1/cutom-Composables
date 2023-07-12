package com.devmo.customcomposecomponents.ui.theme

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.lightColors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.devmo.customcomposecomponents.R
import kotlinx.coroutines.delay

@Composable
fun CustomSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: Dp = 4.dp,
    duration: Int = 500,
    switchStyle: SwitchStyle = SwitchStyle.MoonCoverSun
) {
    var switchState by remember { mutableStateOf(checked) }
    val interactionSource = remember { MutableInteractionSource() }

    Row(
        modifier = modifier
            .padding(contentPadding)
            .clickable(interactionSource = interactionSource, indication = null, onClick = {
                switchState = !switchState
                onCheckedChange(switchState)
            })
            .size(width = 300.dp, height = 120.dp)
    ) {
        Surface(
            shape = RoundedCornerShape(50), elevation = 10.dp, modifier = Modifier.fillMaxSize()
        ) {
            when (switchStyle) {
                SwitchStyle.MoonCoverSun -> SunMoonSwitchContents(
                    checked = checked, duration = duration
                )
                SwitchStyle.SunMoonFade -> SunMoonSwitchContents(
                    checked = checked, style = 1, duration = duration
                )
                SwitchStyle.Simple -> SimpleSwitchContents(checked = !checked, duration = duration)
            }
        }
    }
}

@Composable
private fun SimpleSwitchContents(checked: Boolean, duration: Int) {
    val color = animateColorAsState(
        targetValue = if (checked) Color(0xFF002233) else Color(0xFF00BCD4), animationSpec = tween(
            durationMillis = duration, easing = LinearEasing
        )
    )

    BoxWithConstraints {
        val height = constraints.maxHeight
        val width = constraints.maxWidth
        val sunMoonColor = animateColorAsState(
            targetValue = if (checked) Color(0xFFF6F1D5) else Color(0xFFFFC107),
            animationSpec = tween(duration)
        )
        val centerX = animateFloatAsState(
            targetValue = if (checked) width - (height / 2f) else height / 2f,
            animationSpec = tween(
                duration
            )
        )

        Canvas(modifier = Modifier
            .fillMaxSize()
            .background(color = color.value), onDraw = {
            drawCircle(
                sunMoonColor.value,
                radius = height / 2 - 5f,
                center = Offset(centerX.value, height / 2f),
                alpha = 1f,
                style = Fill
            )

        })
    }

}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun SunMoonSwitchContents(checked: Boolean, duration: Int = 500, style: Int = 0) {
    val color = animateColorAsState(
        targetValue = if (!checked) Color(0xFF0c1445) else Color(0xFF87CEEB), animationSpec = tween(
            durationMillis = duration, easing = LinearEasing
        )
    )
    var showBackground by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(key1 = checked, block = {
        delay(timeMillis = duration / 5L)
        showBackground = checked
    })
    BoxWithConstraints(
        Modifier
            .background(color.value)
            .fillMaxWidth(), contentAlignment = Alignment.CenterStart
    ) {

        val imageWidth = constraints.maxHeight.toFloat() * 0.5f // Adjust the width as needed
        val animatedOffset = animateFloatAsState(
            targetValue = if (checked) constraints.maxWidth.toFloat() - (imageWidth * 3.3f) else 10f,
            animationSpec = tween(duration)
        )

        val resId = if (!checked) R.drawable.moon2 else R.drawable.sun
        AnimatedContent(targetState = !showBackground, transitionSpec = {
            slideInVertically { if (!checked) -it else it } with slideOutVertically { if (checked) it else -it }
        }) {

            if (it) {
                Image(
                    painter = painterResource(id = R.drawable.stars2),
                    contentDescription = "stars bg",
                    Modifier
                        .fillMaxWidth()
                        .padding(start = 100.dp),
                    contentScale = ContentScale.FillBounds
                )
            }
        }
        if (style == 1) {
            Box(
                modifier = Modifier
                    .width(imageWidth.dp)
                    .offset(x = animatedOffset.value.dp)
                    .aspectRatio(1f)
            ) {
                Crossfade(
                    targetState = resId,
                    animationSpec = tween(durationMillis = 100),

                    ) { id ->
                    Image(
                        painter = painterResource(id = id),
                        contentDescription = "moon",
                        contentScale = ContentScale.Inside,
                        modifier = Modifier.scale(
                            if (id == R.drawable.moon2) 1.37f
                            else 1f
                        )
                    )
                }
            }
        } else if (style == 0) SunMoonSwitcher(
            Modifier.offset(x = animatedOffset.value.dp), checked = checked, duration
        )
    }
    BoxWithConstraints {
        val backgroundPosition = animateFloatAsState(
            targetValue = if (checked) 0f else -constraints.maxWidth.toFloat(),
            animationSpec = tween(durationMillis = duration)
        )
        Box(Modifier.graphicsLayer {
            translationX = backgroundPosition.value
        }) {
            Image(
                painter = painterResource(id = R.drawable.clouds2),
                contentDescription = "clouds bg",
                Modifier.fillMaxSize(),
                contentScale = ContentScale.FillWidth
            )
            Image(
                painter = painterResource(id = R.drawable.clouds),
                contentDescription = "clouds bg",
                Modifier
                    .fillMaxSize()
                    .alpha(.85f),
                contentScale = ContentScale.Crop
            )
        }
    }

}


@Composable
private fun SunMoonSwitcher(
    modifier: Modifier = Modifier, checked: Boolean, duration: Int
) {
    BoxWithConstraints(
        modifier.clip(CircleShape)
    ) {
        val start = 0f
        val end = -constraints.maxWidth.toFloat()
        val centerX = animateFloatAsState(
            targetValue = if (!checked) start else end, animationSpec = tween(
                durationMillis = (duration / 5) * 3
            )
        )

        Image(
            painter = painterResource(id = R.drawable.sun), contentDescription = ""
        )

        Image(painter = painterResource(id = R.drawable.moon2),
            contentDescription = "",
            Modifier.graphicsLayer {
                translationX = centerX.value
                scaleX = 1.35f
                scaleY = 1.35f
            })
    }
}

sealed class SwitchStyle {
    object Simple : SwitchStyle()
    object SunMoonFade : SwitchStyle()
    object MoonCoverSun : SwitchStyle()
}


@Preview
@Composable
fun DayNightSwitchPrev() {
    val checked by remember {
        mutableStateOf(false)
    }
//    SunMoonContents(
//        modifier = Modifier,
//        checked,
//        onCheckedChange = {}
//    )

}