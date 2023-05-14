package com.example.notesapp.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.notesapp.entity.NoteWithTags

@Composable
fun NoteItem(
    note: NoteWithTags,
    modifier: Modifier,
    backgroundColor: Color = MaterialTheme.colors.background,
    borderStroke: BorderStroke = BorderStroke(1.dp, MaterialTheme.colors.surface)
) {
    Card(modifier = modifier, backgroundColor = backgroundColor, border = borderStroke) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            if (note.note.title.isNotBlank()) {
                Text(
                    text = note.note.title,
                    style = MaterialTheme.typography.h6,
                    color = MaterialTheme.colors.onBackground,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
            if (note.note.description.isNotBlank()) {
                Text(
                    text = note.note.description,
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onBackground,
                    maxLines = 10,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
            if (note.tags.isNotEmpty()) {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.Bottom
                ) {
                    items(note.tags) { tag ->
                        Card(
                            backgroundColor = MaterialTheme.colors.surface,
                            contentColor = MaterialTheme.colors.onSurface
                        ) {
                            Text(text = tag.name, style = MaterialTheme.typography.body2, modifier = Modifier.padding(6.dp, 2.dp))
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
            }
        }
    }
}