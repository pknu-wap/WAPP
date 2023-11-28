package com.wap.wapp.feature.management

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.wap.designsystem.WappTheme
import com.wap.designsystem.component.WappButton
import com.wap.wapp.core.designresource.R
import com.wap.wapp.core.model.survey.Survey
import com.wap.wapp.feature.management.R.string
import java.time.format.DateTimeFormatter

@Composable
internal fun ManagementSurveyContent(
    surveyList: List<Survey>,
    onCardClicked: (String) -> Unit,
    onAddSurveyButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = WappTheme.colors.black25,
        ),
        modifier = modifier,
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(16.dp),
        ) {
            Text(
                text = stringResource(string.survey),
                style = WappTheme.typography.titleBold,
                color = WappTheme.colors.white,
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                itemsIndexed(surveyList) { currentIndex, survey ->
                    ManagementSurveyItem(
                        item = survey,
                        cardColor = ManagementCardColor(currentIndex = currentIndex),
                        onCardClicked = { surveyId -> onCardClicked(surveyId) },
                    )
                }
            }

            WappButton(
                textRes = string.add_survey,
                onClick = { onAddSurveyButtonClicked() },
            )
        }
    }
}

@Composable
private fun ManagementSurveyItem(
    item: Survey,
    cardColor: Color,
    onCardClicked: (String) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .clickable { onCardClicked(item.surveyId) },
        colors = CardDefaults.cardColors(containerColor = cardColor),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = item.review,
                    style = WappTheme.typography.contentMedium,
                    color = WappTheme.colors.white,
                    maxLines = 1,
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    SurveyCaption(item.noticeName, WappTheme.colors.white)

                    CaptionDivider()

                    SurveyCaption(item.userName, WappTheme.colors.yellow)

                    CaptionDivider()

                    SurveyCaption(
                        text = item.surveyedAt.format(
                            DateTimeFormatter.ofPattern("yyyy.MM.dd"),
                        ),
                        WappTheme.colors.white,
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Icon(
                painter = painterResource(id = R.drawable.ic_forward),
                contentDescription = stringResource(string.detail_icon_description),
                tint = WappTheme.colors.yellow,
                modifier = Modifier
                    .clickable { onCardClicked(item.surveyId) }
                    .size(20.dp),
            )
        }
    }
}

@Composable
private fun SurveyCaption(
    text: String,
    color: Color,
) {
    Text(
        text = text.take(10), // Limit max Length
        style = WappTheme.typography.captionRegular,
        color = color,
    )
}

@Composable
private fun CaptionDivider() {
    Divider(
        modifier = Modifier.size(
            height = 14.dp,
            width = 1.dp,
        ),
    )
}
