package com.example.notesapp.repository

import com.example.notesapp.db.NotesDatabase
import com.example.notesapp.entity.Tag
import javax.inject.Inject

class TagsRepository @Inject constructor(private val db: NotesDatabase) {

    suspend fun save(tag: Tag): Tag? {
        return if (tag.id === null) {
            val insertedId: Long = db.getTagsDao().insert(tag)
            db.getTagsDao().getById(insertedId)
        } else {
            db.getTagsDao().update(tag)
            db.getTagsDao().getById(tag.id!!)
        }
    }

    fun findAll() = db.getTagsDao().getAll()

    suspend fun delete(id: Long) = db.getTagsDao().delete(id)

    suspend fun findById(id: Long) = db.getTagsDao().getById(id)
}