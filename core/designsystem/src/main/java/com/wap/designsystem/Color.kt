package com.wap.designsystem

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

val White = Color(0xFFFFFFFF)
val Black = Color(0xFF000000)
val BackgroundBlack = Color(0xFF131313)
val Black3 = Color(0xFF828282)
val Black2 = Color(0xFF424242)
val Black1 = Color(0xFF252424)
val Gray2 = Color(0xFF252424)
val Gray1 = Color(0xFFA2A2A2)
val Yellow = Color(0xFFFBCF34)

@Stable
class WappColor(
    white: Color = White,
    black: Color = Black,
    backgroundBlack: Color = BackgroundBlack,
    black1: Color = Black1,
    black2: Color = Black2,
    black3: Color = Black3,
    gray1: Color = Gray1,
    gray2: Color = Gray2,
    yellow: Color = Yellow,
) {
    var white by mutableStateOf(white)
        private set
    var black by mutableStateOf(black)
        private set
    var backgroundBlack by mutableStateOf(backgroundBlack)
        private set
    var black1 by mutableStateOf(black1)
        private set
    var black2 by mutableStateOf(black2)
        private set
    var black3 by mutableStateOf(black3)
        private set
    var gray1 by mutableStateOf(gray1)
        private set
    var gray2 by mutableStateOf(gray2)
        private set
    var yellow by mutableStateOf(yellow)
        private set
}
