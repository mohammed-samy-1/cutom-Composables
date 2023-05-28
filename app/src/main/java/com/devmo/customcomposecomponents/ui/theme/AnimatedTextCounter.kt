package com.devmo.customcomposecomponents.ui.theme

import androidx.compose.animation.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun TextCounter(
    modifier: Modifier = Modifier,
    count: Int,
    style: TextStyle = TextStyle(fontSize = 25.sp, color = Color.White)
) {
    var oldCount by remember {
        mutableStateOf(count)
    }
    SideEffect {
        oldCount = count
    }
    Row(modifier) {
        val countString = count.toString()
        val oldCountString = count.toString()
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

@Preview
@Composable
fun ppp() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var count by remember {
            mutableStateOf(0)
        }
        TextCounter(Modifier, count = count)
        Button(onClick = { count++ }) {
            Text(text = "Add 1")
        }

    }
}