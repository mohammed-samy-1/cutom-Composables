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
        navController = navController, startDestination = Navigation.Home.name,
        modifier = Modifier.background(Color.Black)
    ) {
        composable(Navigation.Home.name) { HomeScreen(navController = navController) }
        composable(Navigation.TextCounter.name) { TextCounterScreen(navController = navController) }
        composable(Navigation.Progressbar.name) { ProgressbarScreen(navController = navController) }
        composable(Navigation.Dropdown.name) { DropdownScreen(navController = navController) }
        composable(Navigation.Timer.name) { TimerScreen(navController = navController) }
        composable(Navigation.VolumeController.name) { VolumeControllerScreen(navController = navController) }
        composable(Navigation.CreditCard.name) { CreditCardScreen(navController = navController) }
        composable(Navigation.DayNightSwitch.name) { DayNightSwitch(navController = navController) }
        composable(Navigation.PremiumDayNightSwitch.name) { PremiumSwitchScreen(navController = navController) }
    }
}

sealed class Navigation(val name: String) {
    object Home : Navigation("home")
    object TextCounter : Navigation("text_counter")
    object Progressbar : Navigation("progressbar")
    object Dropdown : Navigation("dropdown")
    object Timer : Navigation("timer")
    object VolumeController : Navigation("volume_controller")
    object CreditCard : Navigation("card")
    object DayNightSwitch : Navigation("Switch")
    object PremiumDayNightSwitch : Navigation("PremiumSwitch")
}