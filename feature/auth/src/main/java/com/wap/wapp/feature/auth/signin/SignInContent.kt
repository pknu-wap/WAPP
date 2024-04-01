package com.wap.wapp.feature.auth.signin

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wap.designsystem.WappTheme
import com.wap.wapp.core.designresource.R
import com.wap.wapp.feature.auth.R.string

@Composable
internal fun SignInContent(
    openSignInSheet: () -> Unit,
    onGuestModeButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberScrollState()

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(scrollState),
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_white_cat),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(width = 230.dp, height = 230.dp),
                contentDescription = stringResource(string.wapp_icon_description),
            )
            Row(
                modifier = Modifier.align(Alignment.CenterHorizontally),
            ) {
                Column {
                    Spacer(modifier = Modifier.height(40.dp))

                    Text(
                        text = stringResource(id = string.application_name),
                        style = WappTheme.typography.titleBold,
                        fontSize = 48.sp,
                        color = WappTheme.colors.white,
                    )
                }
                Text(
                    text = stringResource(id = string.application_name),
                    fontSize = 48.sp,
                    style = WappTheme.typography.titleBold,
                    color = WappTheme.colors.yellow34,
                )
            }
        }
        ElevatedButton(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(),
            onClick = {
                openSignInSheet()
            },
            colors = ButtonDefaults.elevatedButtonColors(
                containerColor = WappTheme.colors.white,
            ),
            shape = RoundedCornerShape(10.dp),
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_github),
                contentDescription = stringResource(
                    id = string.sign_in_github_description,
                ),
                modifier = Modifier.size(40.dp),
                tint = WappTheme.colors.black,
            )
            Text(
                text = stringResource(id = string.sign_in_github_content),
                style = WappTheme.typography.contentMedium,
                modifier = Modifier.padding(start = 16.dp),
            )
        }

        ElevatedButton(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .padding(top = 20.dp, bottom = 60.dp),
            onClick = {
                onGuestModeButtonClicked()
            },
            colors = ButtonDefaults.elevatedButtonColors(
                containerColor = WappTheme.colors.yellow34,
                contentColor = WappTheme.colors.white,
            ),
            shape = RoundedCornerShape(10.dp),
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_balloon),
                contentDescription = stringResource(
                    id = string.sign_in_non_member_description,
                ),
                modifier = Modifier.size(40.dp),
            )
            Text(
                text = stringResource(id = string.sign_in_non_member_content),
                style = WappTheme.typography.contentMedium,
                color = WappTheme.colors.white,
                modifier = Modifier.padding(start = 16.dp),
            )
        }
    }
}
