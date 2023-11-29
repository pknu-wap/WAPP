package com.wap.wapp.feature.auth.signin

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
    navigateToNotice: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(32.dp),
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Image(
            painter = painterResource(id = R.drawable.img_white_cat),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(width = 230.dp, height = 230.dp),
            contentDescription = "WAPP ICON",
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

        Column {
            ElevatedButton(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                onClick = {
                    openSignInSheet()
                },
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
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = stringResource(id = string.sign_in_github_content),
                    style = WappTheme.typography.contentMedium,
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            ElevatedButton(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                onClick = {
                    navigateToNotice()
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
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = stringResource(id = string.sign_in_non_member_content),
                    style = WappTheme.typography.contentMedium,
                    color = WappTheme.colors.white,
                )
            }
        }
    }
}

/*@Preview
@Composable
fun previewSignInScreen() {
    SignInScreen(

        navigateToSignUp = { },
        navigateToNotice = { },
    )
}*/
