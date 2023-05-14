package com.example.notesapp.ui.viewmodel

import com.example.notesapp.entity.Tag

data class TagWState(
    val tag: Tag,
    val isSelected: Boolean = false,
)
