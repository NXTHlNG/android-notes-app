package com.example.notesapp.ui.viewmodel

import com.example.notesapp.entity.NoteWithTags
import com.example.notesapp.entity.Tag

data class NoteDetailsUiState(
    val noteId: Long? = null,
    val noteTitle: String = "",
    val noteText: String = "",
    val noteTags: List<Tag> = emptyList(),
    val tags: List<TagWState> = emptyList(),
    val tagName: String = ""
)