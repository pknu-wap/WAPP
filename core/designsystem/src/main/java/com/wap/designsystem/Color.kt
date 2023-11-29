package com.wap.designsystem

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

val White = Color(0xFFFFFFFF)
val Black = Color(0xFF000000)
val BackgroundBlack = Color(0xFF131313)
val Black82 = Color(0xFF828282)
val Black42 = Color(0xFF424242)
val Black25 = Color(0xFF252424)
val Gray82 = Color(0xFF828282)
val GrayBD = Color(0xFFBDBDBD)
val GrayF4 = Color(0xFFF4F4F4)
val GrayA2 = Color(0xFFA2A2A2)
val Yellow34 = Color(0xFFFBCF34)
val Yellow3C = Color(0xFFFFBD3C)
val YellowA4 = Color(0xFFFEEBA4)
val Red = Color(0xFFE7475D)
val BlueA3 = Color(0xFF2F3EA3)
val Blue2FF = Color(0xFFB1B2FF)
val Blue4FF = Color(0xFFAAC4FF)
val Blue1FF = Color(0xFFEEF1FF)

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
    grayBD: Color = GrayBD,
    gray82: Color = Gray82,
    yellow34: Color = Yellow34,
    yellow3C: Color = Yellow3C,
    yellowA4: Color = YellowA4,
    red: Color = Red,
    blueA3: Color = BlueA3,
    blue2FF: Color = Blue2FF,
    blue4FF: Color = Blue4FF,
    blue1FF: Color = Blue1FF,
) {
    var white by mutableStateOf(white)
        private set
    var black by mutableStateOf(black)
        private set
    var backgroundBlack by mutableStateOf(backgroundBlack)
        private set
    var black25 by mutableStateOf(black25)
        private set
    var black42 by mutableStateOf(black42)
        private set
    var black82 by mutableStateOf(black82)
        private set
    var grayA2 by mutableStateOf(grayA2)
        private set
    var grayF4 by mutableStateOf(grayF4)
        private set
    var grayBD by mutableStateOf(grayBD)
        private set
    var gray82 by mutableStateOf(gray82)
        private set
    var yellow34 by mutableStateOf(yellow34)
        private set
    var yellow3C by mutableStateOf(yellow3C)
        private set
    var yellowA4 by mutableStateOf(yellowA4)
        private set
    var red by mutableStateOf(red)
        private set
    var blueA3 by mutableStateOf(blueA3)
        private set
    var blue2FF by mutableStateOf(blue2FF)
        private set
    var blue4FF by mutableStateOf(blue4FF)
        private set
    var blue1FF by mutableStateOf(blue1FF)
        private set
}
