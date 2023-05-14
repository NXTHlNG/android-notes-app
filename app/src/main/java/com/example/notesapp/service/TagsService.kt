package com.example.notesapp.service

import com.example.notesapp.entity.Note
import com.example.notesapp.entity.Tag
import com.example.notesapp.repository.NotesRepository
import com.example.notesapp.repository.TagsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TagsService @Inject constructor(private val tagsRepository: TagsRepository) {
    suspend fun getById(id: Long): Tag? {
        return tagsRepository.findById(id)
    }

    fun getAll(): Flow<List<Tag>> {
        return tagsRepository.findAll()
    }

    suspend fun save(tag: Tag): Tag? {
        return tagsRepository.save(tag)
    }

    suspend fun delete(id: Long) {
        return tagsRepository.delete(id)
    }

}