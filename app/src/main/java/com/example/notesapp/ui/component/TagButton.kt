package com.example.notesapp.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.notesapp.ui.viewmodel.TagWState

@Composable
fun TagButton(tag: TagWState, modifier: Modifier, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (tag.isSelected) MaterialTheme.colors.surface else MaterialTheme.colors.background,
            contentColor = MaterialTheme.colors.onBackground
        ),
        border = BorderStroke(1.dp, MaterialTheme.colors.surface)
    ) {
        Text(
            text = tag.tag.name,
        )
    }
}
