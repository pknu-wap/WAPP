package com.wap.wapp.feature.survey

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.wap.designsystem.WappTheme
import com.wap.wapp.core.designresource.R
import com.wap.wapp.feature.survey.R.string
import com.wap.wapp.core.model.survey.SurveyForm

@Composable
internal fun SurveyContent(
    surveyFormList: List<SurveyForm>,
    isManager: Boolean,
    paddingValues: PaddingValues,
    selectedSurveyForm: (String) -> Unit,
    onSurveyCheckButtonClicked: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.End,
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
            .padding(paddingValues),
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
        ) {
            items(surveyFormList) { surveyForm ->
                if (surveyForm.isAfterDeadline()) {
                    SurveyFormItemCard(
                        surveyForm = surveyForm,
                        selectedSurveyForm = selectedSurveyForm,
                    )
                }
            }
        }

        if (isManager) {
            SurveyCheckButton(onSurveyCheckButtonClicked = onSurveyCheckButtonClicked)
        }
    }
}

@Composable
private fun SurveyCheckButton(
    onSurveyCheckButtonClicked: () -> Unit,
) {
    ElevatedButton(
        modifier = Modifier.height(48.dp),
        onClick = { onSurveyCheckButtonClicked() },
        colors = ButtonDefaults.buttonColors(
            contentColor = WappTheme.colors.black,
            containerColor = WappTheme.colors.yellow34,
            disabledContentColor = WappTheme.colors.white,
            disabledContainerColor = WappTheme.colors.grayA2,
        ),
        shape = RoundedCornerShape(10.dp),
        content = {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = stringResource(string.go_to_survey_check),
                    style = WappTheme.typography.contentRegular,
                )

                Icon(
                    painter = painterResource(id = R.drawable.ic_magnifier),
                    contentDescription = stringResource(string.survey_check_description),
                )
            }
        },
    )
}
