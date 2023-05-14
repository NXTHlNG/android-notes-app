package com.example.notesapp.ui.viewmodel

import com.example.notesapp.entity.NoteWithTags

data class NotesUIState(
    val notes: List<NoteWithTags> = emptyList(),
//    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
//    val isOrderSectionVisible: Boolean = false
)