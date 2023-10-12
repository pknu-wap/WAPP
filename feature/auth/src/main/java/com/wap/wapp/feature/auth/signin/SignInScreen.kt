package com.wap.wapp.feature.auth.signin

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wap.designsystem.WappTheme
import com.wap.wapp.core.designresource.R

@Composable
fun SignInScreen() {
    WappTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = WappTheme.colors.backgroundBlack,
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Spacer(modifier = Modifier.height(32.dp))
                Image(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .size(width = 230.dp, height = 230.dp),
                    painter = painterResource(id = R.drawable.ic_white_cat),
                    contentDescription = "WAPP ICON",
                )

                Row(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                ) {
                    Column {
                        Spacer(modifier = Modifier.height(40.dp))
                        Text(
                            text = "WAPP",
                            style = WappTheme.typography.titleBold,
                            fontSize = 48.sp,
                            color = WappTheme.colors.white,
                        )
                    }
                    Text(
                        text = "WAPP",
                        fontSize = 48.sp,
                        style = WappTheme.typography.titleBold,
                        color = WappTheme.colors.yellow,
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                ElevatedButton(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    onClick = {
                        // TODO
                    },
                    shape = RoundedCornerShape(10.dp),
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_github),
                        contentDescription = "Github SignIn Icon",
                        modifier = Modifier.size(40.dp),
                        tint = WappTheme.colors.black,
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = "Github 로그인",
                        style = WappTheme.typography.contentMedium,
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                ElevatedButton(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    onClick = {},
                    colors = ButtonDefaults.elevatedButtonColors(
                        containerColor = WappTheme.colors.yellow,
                        contentColor = WappTheme.colors.white,
                    ),
                    shape = RoundedCornerShape(10.dp),
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_balloon),
                        contentDescription = "Github SignIn Icon",
                        modifier = Modifier.size(40.dp),
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = "비회원으로 둘러보기",
                        style = WappTheme.typography.contentMedium,
                        color = WappTheme.colors.white,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun previewSignInScreen() {
    SignInScreen()
}
