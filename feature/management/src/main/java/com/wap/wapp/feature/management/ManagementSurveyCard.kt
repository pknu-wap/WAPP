package com.wap.wapp.feature.management

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.wap.designsystem.component.CircleLoader
import com.wap.designsystem.component.WappButton
import com.wap.wapp.core.commmon.util.DateUtil
import com.wap.wapp.core.designresource.R
import com.wap.wapp.core.model.survey.SurveyForm
import com.wap.wapp.feature.management.R.string

@Composable
internal fun ManagementSurveyCard(
    surveyFormsState: ManagementViewModel.SurveyFormsState,
    onCardClicked: (String) -> Unit,
    onAddSurveyButtonClicked: () -> Unit,
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = WappTheme.colors.black25),
        modifier = Modifier
            .padding(vertical = 20.dp)
            .padding(horizontal = 8.dp),
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
        ) {
            Text(
                text = stringResource(string.survey),
                style = WappTheme.typography.titleBold,
                color = WappTheme.colors.white,
            )

            ManagementSurveyContent(
                surveyFormsState = surveyFormsState,
                onCardClicked = onCardClicked,
                onAddSurveyButtonClicked = onAddSurveyButtonClicked,
            )
        }
    }
}

@Composable
private fun ManagementSurveyContent(
    surveyFormsState: ManagementViewModel.SurveyFormsState,
    onCardClicked: (String) -> Unit,
    onAddSurveyButtonClicked: () -> Unit,
) {
    when (surveyFormsState) {
        is ManagementViewModel.SurveyFormsState.Loading -> CircleLoader(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
        )

        is ManagementViewModel.SurveyFormsState.Success -> {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.heightIn(max = 166.dp),
            ) {
                itemsIndexed(
                    items = surveyFormsState.surveyForms,
                    key = { index, survey -> survey.surveyFormId },
                ) { currentIndex, survey ->
                    ManagementSurveyItemCard(
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

        is ManagementViewModel.SurveyFormsState.Failure -> {}
    }
}

@Composable
private fun ManagementSurveyItemCard(
    item: SurveyForm,
    cardColor: Color,
    onCardClicked: (String) -> Unit,
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxSize()
            .clickable { onCardClicked(item.surveyFormId) },
        colors = CardDefaults.cardColors(containerColor = cardColor),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .weight(1f),
            ) {
                Column {
                    Text(
                        text = item.title,
                        style = WappTheme.typography.titleBold,
                        color = WappTheme.colors.white,
                        maxLines = 1,
                    )

                    Text(
                        text = item.content,
                        style = WappTheme.typography.labelRegular,
                        color = WappTheme.colors.white,
                        maxLines = 1,
                    )
                }

                Text(
                    text = stringResource(
                        id = string.surveyForm_deadline,
                        item.deadline.format(DateUtil.yyyyMMddFormatter),
                    ),
                    style = WappTheme.typography.captionRegular,
                    color = WappTheme.colors.white,
                    maxLines = 1,
                )
            }

            Icon(
                painter = painterResource(id = R.drawable.ic_forward),
                contentDescription = stringResource(string.detail_icon_description),
                tint = WappTheme.colors.yellow34,
                modifier = Modifier.size(20.dp),
            )
        }
    }
}
