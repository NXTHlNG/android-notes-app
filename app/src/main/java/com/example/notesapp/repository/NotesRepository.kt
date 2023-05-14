package com.example.notesapp.repository

import com.example.notesapp.db.NotesDatabase
import com.example.notesapp.entity.Note
import com.example.notesapp.entity.NoteTagCrossRef
import com.example.notesapp.entity.NoteWithTags
import javax.inject.Inject

class NotesRepository @Inject constructor(
    private val db: NotesDatabase,
    private val tagsRepository: TagsRepository
) {

    suspend fun save(note: Note): Note? {
        return if (note.id === null) {
            val insertedId: Long = db.getNotesDao().insert(note)
            db.getNotesDao().getById(insertedId)
        } else {
            db.getNotesDao().update(note)
            db.getNotesDao().getById(note.id!!)
        }
    }

    suspend fun save(note: NoteWithTags): NoteWithTags? {
        val savedNote = if (note.note.id === null) {
            val insertedId: Long = db.getNotesDao().insert(note.note)
            db.getNotesDao().getNoteWithTagsById(insertedId)
        } else {
            db.getNotesDao().update(note.note)
            db.getNotesDao().getNoteWithTagsById(note.note.id!!)
        }

        db.getNotesDao().deleteNoteTagCrossRef(savedNote!!.note.id!!)

        note.tags.forEach { tag ->
            val savedTag = tagsRepository.save(tag)
            save(NoteTagCrossRef(savedNote!!.note.id!!, savedTag!!.id!!))
        }

        return savedNote
    }

    private suspend fun save(crossRef: NoteTagCrossRef) =
        db.getNotesDao().insertNoteTagCrossRef(crossRef)

    fun findAll() = db.getNotesDao().getAll()

    fun findAllWithTags() = db.getNotesDao().getNotesWithTags()

    suspend fun findById(id: Long) = db.getNotesDao().getById(id)

    suspend fun findNoteWithTagsById(id: Long) = db.getNotesDao().getNoteWithTagsById(id)

    suspend fun deleteNote(id: Long) = db.getNotesDao().delete(id)
}