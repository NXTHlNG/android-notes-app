package com.example.notesapp.ui.viewmodel

data class TagsState(
    val tags: List<TagWState> = emptyList(),
//    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
//    val isOrderSectionVisible: Boolean = false
)