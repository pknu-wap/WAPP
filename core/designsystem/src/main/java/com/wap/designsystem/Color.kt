package com.wap.designsystem

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

val White = Color(0xFFFFFFFF)
val GrayF4 = Color(0xFFF4F4F4)
val GrayBD = Color(0xFFBDBDBD)
val Gray95 = Color(0xFF959595)
val Gray82 = Color(0xFF828282)
val Gray7C = Color(0xFF7C7C7C)
val Gray4A = Color(0xFF49494A)
val Black25 = Color(0xFF252424)
val Black42 = Color(0xFF424242)
val Black = Color(0xFF000000)
val BackgroundBlack = Color(0xFF131313)

val YellowA4 = Color(0xFFFEEBA4)
val Yellow3C = Color(0xFFFFBD3C)
val Yellow34 = Color(0xFFFBCF34)

val Red = Color(0xFFE7475D)

val Blue1FF = Color(0xFFEEF1FF)
val Blue4FF = Color(0xFFAAC4FF)
val Blue2FF = Color(0xFFB1B2FF)
val BlueA3 = Color(0xFF2F3EA3)

@Stable
class WappColor(
    white: Color = Color(0xFFFFFFFF),
    black: Color = Color(0xFF000000),
    backgroundBlack: Color = Color(0xFF131313),
    black25: Color = Color(0xFF252424),
    black42: Color = Color(0xFF424242),
    gray95: Color = Color(0xFF959595),
    black82: Color = Color(0xFF828282),
    grayA2: Color = Color(0xFFA2A2A2),
    grayF4: Color = Color(0xFFF4F4F4),
    grayBD: Color = Color(0xFFBDBDBD),
    gray82: Color = Color(0xFF828282),
    gray7C: Color = Color(0xFF7C7C7C),
    gray4A: Color = Color(0xFF49494A),
    yellow34: Color = Color(0xFFFBCF34),
    yellow3C: Color = Color(0xFFFFBD3C),
    yellowA4: Color = Color(0xFFFEEBA4),
    red: Color = Color(0xFFE7475D),
    blueA3: Color = Color(0xFF2F3EA3),
    blue2FF: Color = Color(0xFFB1B2FF),
    blue4FF: Color = Color(0xFFAAC4FF),
    blue1FF: Color = Color(0xFFEEF1FF),
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
    var gray95 by mutableStateOf(gray95)
        private set
    var black82 by mutableStateOf(black82)
        private set
    var grayA2 by mutableStateOf(grayA2)
        private set
    var grayF4 by mutableStateOf(grayF4)
        private set
    var gray4A by mutableStateOf(gray4A)
        private set
    var grayBD by mutableStateOf(grayBD)
        private set
    var gray82 by mutableStateOf(gray82)
        private set
    var gray7C by mutableStateOf(gray7C)
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
