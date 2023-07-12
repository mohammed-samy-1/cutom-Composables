package com.devmo.customcomposecomponents.ui.theme

import android.graphics.Color
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize


@Composable
fun LoadingButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    onLoading: (Boolean) -> Unit = {},
    LoadingState: Boolean,
    onComplete: () -> Unit,
    color: Color,
    textColor: Color,
    textStyle: SwitchStyle,
    canerRadios: Dp
) {
    var size:IntSize
    Box(
        modifier = modifier
            .onSizeChanged {
                size = it
            }
    ) {

    }
}