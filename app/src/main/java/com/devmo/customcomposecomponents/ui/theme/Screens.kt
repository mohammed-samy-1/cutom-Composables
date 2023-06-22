package com.devmo.customcomposecomponents.ui.theme

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
        Button(
            onClick = { navController.navigate(Navication.CreditCard.name) },
            Modifier.fillMaxWidth(.9f)
        ) {
            Text("Go to Credit Card")
        }
        Button(
            onClick = { navController.navigate(Navication.DayNightSwitch.name) },
            Modifier.fillMaxWidth(.9f)
        ) {
            Text("Go to DayNight Switch")
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
            TimerView(totalTime = 1000 * 3, animatedText = true) {
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

@Composable
fun CreditCardScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        CreditCardForm()
    }
}

@Composable
fun DayNightSwitch(navController: NavController) {
    var checked by remember {
        mutableStateOf(false)
    }
    val bg = animateColorAsState(
        targetValue =if (!checked) Color(0xFF0c1445) else Color(0xFF87CEEB),
        animationSpec = tween(1000)
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bg.value),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomSwitch(checked = checked, onCheckedChange = {checked = it}, switchStyle = SwitchStyle.Simple)
        CustomSwitch(checked = checked, onCheckedChange = {checked = it}, switchStyle = SwitchStyle.SunMoonFade)
        CustomSwitch(checked = checked, onCheckedChange = {checked = it})
    }
}