package com.wap.wapp.feature.auth.signup

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.wap.designsystem.WappTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpChipGroup() {
    val itemsList = listOf("1학기", "2학기")

    var selectedItem by remember {
        mutableStateOf(itemsList[0]) // initially, first item is selected
    }

    LazyRow(modifier = Modifier.fillMaxWidth()) {
        items(itemsList) { item ->
            FilterChip(
                modifier = Modifier.padding(horizontal = 6.dp),
                selected = (item == selectedItem),
                onClick = {
                    selectedItem = item
                },
                label = {
                    Text(
                        text = item,
                        color = WappTheme.colors.white,
                        style = WappTheme.typography.contentRegular,
                        textAlign = TextAlign.Center,
                    )
                },
                colors = FilterChipDefaults.filterChipColors(
                    containerColor = WappTheme.colors.black3,
                    selectedContainerColor = WappTheme.colors.yellow,
                ),
            )
        }
    }
}
