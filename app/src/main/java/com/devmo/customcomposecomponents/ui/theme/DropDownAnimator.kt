package com.devmo.customcomposecomponents.ui.theme

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun DropDownAnimator(
    modifier: Modifier = Modifier,
    isInitiallyOpened: Boolean = false,
    content: @Composable () -> Unit
) {
    var isOpened by remember {
        mutableStateOf(isInitiallyOpened)
    }
    val alpha = animateFloatAsState(
        targetValue = if (isOpened) 1f else 0f,
        animationSpec = tween(
            durationMillis = 300
        )
    )
    val scale = animateFloatAsState(
        targetValue = if (isOpened) -1f else 1f,
        animationSpec = tween(
            durationMillis = 300
        )
    )
    val rotateX = animateFloatAsState(
        targetValue = if (isOpened) 0f else -90f,
        animationSpec = tween(
            durationMillis = 300
        )
    )

    Column(Modifier.fillMaxWidth()) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
                ) {
            Text(
                text = if (isOpened) "Close" else "Open",
                color = Color.White
            )
            Icon(
                Icons.Default.ArrowDropDown,
                contentDescription = "Drop down",
                Modifier
                    .clickable {
                        isOpened = !isOpened
                    }
                    .scale(
                        scale.value
                    ),
                tint = Color.White
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Box(modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer {
                transformOrigin = TransformOrigin(0.5f, 0.0f)
                rotationX = rotateX.value
            }
            .alpha(alpha = alpha.value)
            .padding(5.dp),
            contentAlignment = Alignment.Center
        ){
            content()
        }
    }
}

@Preview
@Composable
fun PreviewParameter() {
    Box(modifier = Modifier.fillMaxSize()
        .background(Color.Black)
    ){
        DropDownAnimator() {
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(Color.Magenta),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = "this is my content",
                    color = Color.White
                )
            }
        }
    }
}