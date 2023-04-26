package com.devmo.customcomposecomponents.ui.theme

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp

@Composable
fun TimerView(
    modifier: Modifier = Modifier,
    totalTime:Long = 1000 * 60,
    handleColor: Color = Color.Blue,
    activeColor: Color = Color.Blue,
    inActiveColor: Color = Color.Blue,
    initialValue:Float = 1f,
    strokeWidth :Dp = 5.dp
) {
    var size by remember {
        mutableStateOf(IntSize.Zero)
    }
    var value by remember {
        mutableStateOf(initialValue)
    }

}