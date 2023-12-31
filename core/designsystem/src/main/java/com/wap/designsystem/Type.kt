package com.wap.designsystem

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.wap.wapp.core.designsystem.R

val NotoSansRegular: FontFamily =
    FontFamily(
        Font(
            resId = R.font.notosanskr_regular,
            weight = FontWeight.Normal,
            style = FontStyle.Normal,
        ),
    )

val NotoSansMedium: FontFamily =
    FontFamily(
        Font(
            resId = R.font.notosanskr_medium,
            weight = FontWeight.Medium,
            style = FontStyle.Normal,
        ),
    )

val NotoSansBold: FontFamily =
    FontFamily(
        Font(
            resId = R.font.notosanskr_bold,
            weight = FontWeight.Bold,
            style = FontStyle.Normal,
        ),
    )

@Stable
class WappTypography internal constructor(
    titleBold: TextStyle,
    titleMedium: TextStyle,
    titleRegular: TextStyle,
    contentBold: TextStyle,
    contentMedium: TextStyle,
    contentRegular: TextStyle,
    labelBold: TextStyle,
    labelMedium: TextStyle,
    labelRegular: TextStyle,
    captionBold: TextStyle,
    captionMedium: TextStyle,
    captionRegular: TextStyle,
) {
    var titleBold: TextStyle by mutableStateOf(titleBold)
        private set
    var titleMedium: TextStyle by mutableStateOf(titleMedium)
        private set
    var titleRegular: TextStyle by mutableStateOf(titleRegular)
        private set
    var contentBold: TextStyle by mutableStateOf(contentBold)
        private set
    var contentMedium: TextStyle by mutableStateOf(contentMedium)
        private set
    var contentRegular: TextStyle by mutableStateOf(contentRegular)
        private set
    var labelBold: TextStyle by mutableStateOf(labelBold)
        private set
    var labelMedium: TextStyle by mutableStateOf(labelMedium)
        private set
    var labelRegular: TextStyle by mutableStateOf(labelRegular)
        private set
    var captionBold: TextStyle by mutableStateOf(captionBold)
        private set
    var captionMedium: TextStyle by mutableStateOf(captionMedium)
        private set
    var captionRegular: TextStyle by mutableStateOf(captionRegular)
        private set
}

@Composable
fun WappTypography(): WappTypography {
    return WappTypography(
        titleBold = TextStyle(
            fontSize = 18.sp,
            fontFamily = NotoSansBold,
            platformStyle = PlatformTextStyle(
                includeFontPadding = false,
            ),
        ),
        titleMedium = TextStyle(
            fontSize = 18.sp,
            fontFamily = NotoSansMedium,
            platformStyle = PlatformTextStyle(
                includeFontPadding = false,
            ),
        ),
        titleRegular = TextStyle(
            fontSize = 18.sp,
            fontFamily = NotoSansRegular,
            platformStyle = PlatformTextStyle(
                includeFontPadding = false,
            ),
        ),
        contentBold = TextStyle(
            fontSize = 16.sp,
            fontFamily = NotoSansBold,
            platformStyle = PlatformTextStyle(
                includeFontPadding = false,
            ),
        ),
        contentMedium = TextStyle(
            fontSize = 16.sp,
            fontFamily = NotoSansMedium,
            platformStyle = PlatformTextStyle(
                includeFontPadding = false,
            ),
        ),
        contentRegular = TextStyle(
            fontSize = 16.sp,
            fontFamily = NotoSansRegular,
            platformStyle = PlatformTextStyle(
                includeFontPadding = false,
            ),
        ),
        labelBold = TextStyle(
            fontSize = 14.sp,
            fontFamily = NotoSansBold,
            platformStyle = PlatformTextStyle(
                includeFontPadding = false,
            ),
        ),
        labelMedium = TextStyle(
            fontSize = 14.sp,
            fontFamily = NotoSansMedium,
            platformStyle = PlatformTextStyle(
                includeFontPadding = false,
            ),
        ),
        labelRegular = TextStyle(
            fontSize = 14.sp,
            fontFamily = NotoSansRegular,
            platformStyle = PlatformTextStyle(
                includeFontPadding = false,
            ),
        ),
        captionBold = TextStyle(
            fontSize = 12.sp,
            fontFamily = NotoSansBold,
            platformStyle = PlatformTextStyle(
                includeFontPadding = false,
            ),
        ),
        captionMedium = TextStyle(
            fontSize = 12.sp,
            fontFamily = NotoSansMedium,
            platformStyle = PlatformTextStyle(
                includeFontPadding = false,
            ),
        ),
        captionRegular = TextStyle(
            fontSize = 12.sp,
            fontFamily = NotoSansRegular,
            platformStyle = PlatformTextStyle(
                includeFontPadding = false,
            ),
        ),
    )
}
