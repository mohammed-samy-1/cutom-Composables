package com.devmo.customcomposecomponents.ui.theme

import androidx.compose.animation.*

import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import android.text.format.DateFormat
import java.text.SimpleDateFormat

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun TimerText(
    modifier: Modifier = Modifier,
    count: Long,
    style: TextStyle = TextStyle(fontSize = 25.sp, color = Color.White)
) {
    var oldCount by remember {
        mutableStateOf(count)
    }
    SideEffect {
        oldCount = count
    }
    Row(modifier) {
        val countString = DateFormat.format("mm:ss", count)
        val oldCountString = DateFormat.format("mm:ss", oldCount)
        for (i in countString.indices) {
            val oldChar = oldCountString.getOrNull(i)
            val newChar = countString[i]
            val char = if (oldChar == newChar) {
                oldCountString[i]
            } else {
                countString[i]
            }
            AnimatedContent(
                targetState = char,
                transitionSpec = { slideInVertically { it } with slideOutVertically { -it } }
            ) {
                Text(
                    text = it.toString(),
                    style = style,
                    softWrap = false
                )
            }
        }
    }

}