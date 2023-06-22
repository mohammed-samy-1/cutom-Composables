package com.devmo.customcomposecomponents

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.devmo.customcomposecomponents.ui.theme.*

@Composable
fun MyApp() {
    val navController = rememberNavController()
    NavHost(
        navController = navController, startDestination = Navication.Home.name,
        modifier = Modifier.background(Color.Black)
    ) {
        composable(Navication.Home.name) { HomeScreen(navController = navController) }
        composable(Navication.TextCounter.name) { TextCounterScreen(navController = navController) }
        composable(Navication.Progressbar.name) { ProgressbarScreen(navController = navController) }
        composable(Navication.Dropdown.name) { DropdownScreen(navController = navController) }
        composable(Navication.Timer.name) { TimerScreen(navController = navController) }
        composable(Navication.VolumeController.name) { VolumeControllerScreen(navController = navController) }
        composable(Navication.CreditCard.name) { CreditCardScreen(navController = navController) }
        composable(Navication.DayNightSwitch.name) { DayNightSwitch(navController = navController) }
    }
}

sealed class Navication(val name: String) {
    object Home : Navication("home")
    object TextCounter : Navication("text_counter")
    object Progressbar : Navication("progressbar")
    object Dropdown : Navication("dropdown")
    object Timer : Navication("timer")
    object VolumeController : Navication("volume_controller")
    object CreditCard : Navication("card")
    object DayNightSwitch : Navication("Switch")
}