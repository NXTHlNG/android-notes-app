package com.example.notesapp.entity;

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = ["note_id", "tag_id"], tableName = "NoteTagCrossRef")
data class NoteTagCrossRef(
    @ColumnInfo(name = "note_id")
    val noteId: Long,
    @ColumnInfo(name = "tag_id")
    val tagId: Long
)