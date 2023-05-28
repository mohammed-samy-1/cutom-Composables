package com.devmo.customcomposecomponents.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp

import androidx.navigation.NavController
import com.devmo.customcomposecomponents.Navication

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = { navController.navigate(Navication.TextCounter.name) },
            Modifier.fillMaxWidth(.9f)
        ) {
            Text("Go to Text Counter")
        }
        Button(
            onClick = { navController.navigate(Navication.Progressbar.name) },
            Modifier.fillMaxWidth(.9f)
        ) {
            Text("Go to Progress Bar")
        }
        Button(
            onClick = { navController.navigate(Navication.Timer.name) },
            Modifier.fillMaxWidth(.9f)
        ) {
            Text("Go to Timer")
        }
        Button(
            onClick = { navController.navigate(Navication.Dropdown.name) },
            Modifier.fillMaxWidth(.9f)
        ) {
            Text("Go to Dropdown")
        }
        Button(
            onClick = { navController.navigate(Navication.VolumeController.name) },
            Modifier.fillMaxWidth(.9f)
        ) {
            Text("Go to Volume Controller")
        }
    }
}


@Composable
fun TextCounterScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var count by remember {
            mutableStateOf(0)
        }
        TextCounter(Modifier, count = count)
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = { count++ }) {
            Text(text = "Add 1")
        }
    }
}

@Composable
fun ProgressbarScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
        ) {
            CustomProgressBar(percentage = .9f, number = 100)
            Spacer(modifier = Modifier.size(30.dp))
            CustomProgressBar(
                percentage = .7f, number = 100, animationDelay = 500
            )
        }
    }
}

@Composable
fun DropdownScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val vibrate = remember {
            mutableStateOf(false)
        }
        if (vibrate.value) {
            LocalHapticFeedback.current.performHapticFeedback(HapticFeedbackType.LongPress)
            vibrate.value = false
        }
        DropDownAnimator {
            TimerView(totalTime = 1000 * 3 ,  animatedText = true) {
                vibrate.value = true
            }
        }
    }
}

@Composable
fun TimerScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val vibrate = remember {
            mutableStateOf(false)
        }
        if (vibrate.value) {
            LocalHapticFeedback.current.performHapticFeedback(HapticFeedbackType.LongPress)
            vibrate.value = false
        }
        Box(modifier = Modifier.size(250.dp)) {
            TimerView(totalTime = 60 * 1000L, onFinished = {
                vibrate.value = true
            })
        }
        Box(modifier = Modifier.size(250.dp)) {
            TimerView(
                totalTime = 60 * 1000L, onFinished = {
                    vibrate.value = true
                },
                animatedText = true
            )
        }

    }
}

@Composable
fun VolumeControllerScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
        ) {
            ControlBar()
        }
    }
}