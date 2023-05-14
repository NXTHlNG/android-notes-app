package com.example.notesapp.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.notesapp.ui.viewmodel.TagWState

@Composable
fun TagItem(
    tag: TagWState, modifier: Modifier, onDeleteButtonClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .background(if (tag.isSelected) MaterialTheme.colors.surface else MaterialTheme.colors.background)
            .padding(16.dp, 8.dp),
    ) {
        Text(
            text = tag.tag.name
        )
        IconButton(onClick = onDeleteButtonClick) {
            Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete tag")
        }
    }
}