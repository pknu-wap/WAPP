package com.wap.wapp.feature.survey.answer

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import com.wap.designsystem.WappTheme

// TODO 공용 모듈로 옮길 예정
@Composable
internal fun SurveyAnswerStateIndicator(index: Int, size: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .wrapContentSize()
            .padding(bottom = 40.dp),
    ) {
        SurveyAnswerStateProgressBar(index = index, size = size)
        SurveyAnswerStateText(index = index, size = size)
    }
}

@Composable
private fun SurveyAnswerStateText(index: Int, size: Int) {
    Row {
        Text(
            text = index.toString(),
            style = WappTheme.typography.contentMedium,
            color = WappTheme.colors.yellow34,
        )
        Text(
            text = "/ $size",
            style = WappTheme.typography.contentMedium,
            color = WappTheme.colors.white,
        )
    }
}

@Composable
private fun SurveyAnswerStateProgressBar(index: Int, size: Int) {
    val progress by animateFloatAsState(
        targetValue = index.toFloat() / size.toFloat(),
        animationSpec = spring(
            stiffness = Spring.StiffnessMediumLow,
            dampingRatio = Spring.DampingRatioMediumBouncy,
        ),
    )

    LinearProgressIndicator(
        color = WappTheme.colors.yellow34,
        trackColor = WappTheme.colors.white,
        progress = progress,
        strokeCap = StrokeCap.Round,
        modifier = Modifier
            .fillMaxWidth()
            .height(10.dp),
    )
}
