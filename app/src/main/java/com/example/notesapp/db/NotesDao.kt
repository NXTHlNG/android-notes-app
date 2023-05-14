package com.example.notesapp.db

import androidx.room.*
import com.example.notesapp.entity.Note
import com.example.notesapp.entity.NoteTagCrossRef
import com.example.notesapp.entity.NoteWithTags
import com.example.notesapp.entity.Tag
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {

    @Insert
    suspend fun insert(note: Note): Long

    @Update
    suspend fun update(note: Note)

    @Query("select * from Notes order by timestamp desc")
    fun getAll(): Flow<List<Note>>

    @Query("select * from Notes where note_id=:id")
    suspend fun getById(id: Long): Note?

    @Query("select * from Notes order by timestamp desc")
    fun getNotesWithTags(): Flow<List<NoteWithTags>>

    @Query("select * from Notes where note_id=:id")
    suspend fun getNoteWithTagsById(id: Long): NoteWithTags?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNoteTagCrossRef(note: NoteTagCrossRef)

    @Query("delete from NoteTagCrossRef where note_id=:id")
    suspend fun deleteNoteTagCrossRef(id: Long)

    @Query("delete from Notes where note_id=:id")
    suspend fun delete(id: Long)

//    @Insert
//    suspend fun insertNoteWithTags(note: Note, tags: List<Tag>): Long

    @Update
    suspend fun updateNoteWithTags(note: Note, tags: List<Tag>)
}