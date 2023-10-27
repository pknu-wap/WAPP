package com.wap.designsystem

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

val White = Color(0xFFFFFFFF)
val Black = Color(0xFF000000)
val BackgroundBlack = Color(0xFF131313)
val Black82 = Color(0xFF828282) // 3
val Black42 = Color(0xFF424242) // 2
val Black25 = Color(0xFF252424) // 1
val GrayF4 = Color(0xFFF4F4F4) // 2
val GrayA2 = Color(0xFFA2A2A2) // 1
val Yellow = Color(0xFFFBCF34)

@Stable
class WappColor(
    white: Color = White,
    black: Color = Black,
    backgroundBlack: Color = BackgroundBlack,
    black25: Color = Black25,
    black42: Color = Black42,
    black82: Color = Black82,
    grayA2: Color = GrayA2,
    grayF4: Color = GrayF4,
    yellow: Color = Yellow,
) {
    var white by mutableStateOf(white)
        private set
    var black by mutableStateOf(black)
        private set
    var backgroundBlack by mutableStateOf(backgroundBlack)
        private set
    var black25 by mutableStateOf(Black25)
        private set
    var black42 by mutableStateOf(Black42)
        private set
    var black82 by mutableStateOf(Black82)
        private set
    var grayA2 by mutableStateOf(GrayA2)
        private set
    var grayF4 by mutableStateOf(GrayF4)
        private set
    var yellow by mutableStateOf(yellow)
        private set
}
