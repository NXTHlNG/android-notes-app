package com.example.notesapp.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.io.Serializable

@Entity(
    tableName = "Tags"
)
data class Tag(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "tag_id")
    var id: Long? = null,
    @ColumnInfo(name = "title")
    var name: String
) : Serializable
