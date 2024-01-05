package com.wap.wapp.feature.management.survey.check

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.wap.designsystem.WappTheme
import com.wap.wapp.core.designresource.R.drawable
import com.wap.wapp.feature.management.survey.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SurveyCheckTopBar(
    onBackButtonClicked: () -> Unit,
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(R.string.check_survey),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                style = WappTheme.typography.contentBold,
            )
        },
        navigationIcon = {
            Icon(
                painter = painterResource(id = drawable.ic_back),
                contentDescription = stringResource(
                    id = com.wap.wapp.core.designsystem.R.string.back_button,
                ),
                modifier = Modifier
                    .padding(start = 16.dp)
                    .clickable { onBackButtonClicked() },
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = WappTheme.colors.yellow34,
        ),
    )
}
