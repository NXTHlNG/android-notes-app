package com.example.notesapp.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class TagWithNotes(
    @Embedded
    val tag: Tag,
    @Relation(
        parentColumn = "tag_id",
        entityColumn = "note_id",
        associateBy = Junction(NoteTagCrossRef::class)
    )
    val notes: List<Note>
)