package com.example.notesapp.service

import com.example.notesapp.entity.Note
import com.example.notesapp.entity.NoteWithTags
import com.example.notesapp.repository.NotesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NotesService @Inject constructor(
    private val notesRepository: NotesRepository
) {
    suspend fun getById(id: Long): Note? {
        return notesRepository.findById(id)
    }

    suspend fun getNoteWithTagsById(id: Long): NoteWithTags? {
        return notesRepository.findNoteWithTagsById(id)
    }

    fun getAll(): Flow<List<Note>> {
        return notesRepository.findAll()
    }

    fun getAllWithTags(): Flow<List<NoteWithTags>> {
        return notesRepository.findAllWithTags()
    }

    suspend fun save(note: Note): Note? {
        return notesRepository.save(note)
    }

    suspend fun save(note: NoteWithTags): NoteWithTags? {
        return notesRepository.save(note)
    }

    suspend fun delete(id: Long) {
        return notesRepository.deleteNote(id)
    }


}