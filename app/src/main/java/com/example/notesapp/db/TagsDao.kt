package com.example.notesapp.db

import androidx.room.*
import com.example.notesapp.entity.Tag
import com.example.notesapp.entity.TagWithNotes
import kotlinx.coroutines.flow.Flow

@Dao
interface TagsDao {
    @Insert
    suspend fun insert(note: Tag): Long

    @Update
    suspend fun update(note: Tag)

    @Query("select * from Tags")
    fun getAll(): Flow<List<Tag>>

    @Query("select * from Tags where tag_id=:id")
    suspend fun getById(id: Long): Tag?

    @Query("select * from Tags")
    fun getTagsWithNotes(): Flow<List<TagWithNotes>>

    @Query("delete from Tags where tag_id=:id")
    suspend fun delete(id: Long)
}