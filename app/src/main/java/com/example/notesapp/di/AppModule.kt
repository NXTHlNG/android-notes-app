package com.example.notesapp.di

import android.content.Context
import androidx.room.Room
import com.example.notesapp.db.NotesDao
import com.example.notesapp.db.NotesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

//    @Singleton
//    @Provides
//    fun providePreferenceManager(@ApplicationContext context: Context): UIModeImpl {
//        return UIModeDataStore(context)
//    }

    @Singleton
    @Provides
    fun provideNotesDao(database: NotesDatabase): NotesDao = database.getNotesDao()

    @Singleton
    @Provides
    fun provideTagsDao(database: NotesDatabase): NotesDao = database.getNotesDao()

    @Singleton
    @Provides
    fun provideNoteDatabase(@ApplicationContext context: Context): NotesDatabase =
        Room.databaseBuilder(
            context,
            NotesDatabase::class.java,
            "notes_db.db"
        ).fallbackToDestructiveMigration().build()
}