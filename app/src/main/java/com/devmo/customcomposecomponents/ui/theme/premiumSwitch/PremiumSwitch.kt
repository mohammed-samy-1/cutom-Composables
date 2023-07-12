package com.devmo.customcomposecomponents.ui.theme.premiumSwitch

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun PremiumSwitch(
    modifier: Modifier = Modifier,
    checked: Boolean,
    onCheckedChangeListener: (Boolean) -> Unit,
    style: PremiumSwitchStyle,
    animationDuration: Int = 400
) {
    when(style){
        PremiumSwitchStyle.SunMoonStyle -> SunMoonContents(
            onCheckedChange = onCheckedChangeListener,
            modifier = modifier,
            checked = checked,
            animationDuration = animationDuration
        )
        PremiumSwitchStyle.TextStyle -> TextDayNightSwitch(
            onCheckedChangeListener = onCheckedChangeListener,
            modifier = modifier,
            checked = checked,
            animationDuration = animationDuration
        )
    }
}

sealed class PremiumSwitchStyle{
    object SunMoonStyle:PremiumSwitchStyle()
    object TextStyle:PremiumSwitchStyle()
}