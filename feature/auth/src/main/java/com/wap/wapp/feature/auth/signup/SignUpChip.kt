package com.wap.wapp.feature.auth.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.wap.designsystem.WappTheme
import com.wap.wapp.feature.auth.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SignUpChip(
    selectedItem: String,
    onSelected: (String) -> Unit,
) {
    val itemsList = listOf(
        stringResource(id = R.string.first_semester),
        stringResource(id = R.string.second_semester),
    )
    LazyRow(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth(),
    ) {
        items(itemsList) { item ->
            FilterChip(
                modifier = Modifier.padding(horizontal = 6.dp),
                selected = (item == selectedItem),
                onClick = { onSelected(item) },
                label = {
                    Text(
                        text = item,
                        color = WappTheme.colors.white,
                        style = WappTheme.typography.contentRegular,
                        textAlign = TextAlign.Center,
                    )
                },
                colors = FilterChipDefaults.filterChipColors(
                    containerColor = WappTheme.colors.black82,
                    selectedContainerColor = WappTheme.colors.yellow34,
                ),
            )
        }
    }
}
