package com.example.notesapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.notesapp.entity.Note
import com.example.notesapp.entity.NoteTagCrossRef
import com.example.notesapp.entity.Tag

@Database(
    entities = [Note::class, Tag::class, NoteTagCrossRef::class],
    version = 1,
    exportSchema = true
)
abstract class NotesDatabase : RoomDatabase() {
    abstract fun getNotesDao(): NotesDao
    abstract fun getTagsDao(): TagsDao
}