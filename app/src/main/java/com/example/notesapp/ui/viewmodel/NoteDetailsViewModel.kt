package com.example.notesapp.ui.viewmodel

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.notesapp.entity.Note
import com.example.notesapp.entity.NoteWithTags
import com.example.notesapp.entity.Tag
import com.example.notesapp.service.NotesService
import com.example.notesapp.service.TagsService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteDetailsViewModel @Inject internal constructor(
    application: Application,
    savedStateHandle: SavedStateHandle,
    private val notesService: NotesService,
    private val tagsService: TagsService,
) : AndroidViewModel(application) {

    private val _state = MutableStateFlow(NoteDetailsUiState())
    val state: StateFlow<NoteDetailsUiState> = _state.asStateFlow()

    private var currentJob: Job? = null

    init {
        var job: Job? = null
        savedStateHandle.get<Long>("noteId")?.let { noteId ->
            if (noteId != -1L) {
                job = viewModelScope.launch {
                    notesService.getNoteWithTagsById(noteId)?.also { note ->
                        _state.update { currentState ->
                            currentState.copy(
                                noteId = note.note.id,
                                noteTitle = note.note.title,
                                noteText = note.note.description,
                                noteTags = note.tags
                            )
                        }
                    }
                }
            }
        }
        viewModelScope.launch {
            job?.join()
            tagsService.getAll()
                .onEach { tags ->
                    println("getTAgsasdasd")
                    _state.update { currentState ->
                        currentState.copy(
                            tags = tags.map { tag ->
                                TagWState(
                                    tag = tag,
                                    isSelected = currentState.noteTags.any { t -> t.id == tag.id })
                            }
                        )
                    }
                }
                .collect()
        }
    }

    fun onTitleChange(title: String) {
        _state.update { currentState ->
            currentState.copy(noteTitle = title)
        }
        onSaveNote()
    }

    fun onContentChange(content: String) {
        _state.update { currentState ->
            currentState.copy(noteText = content)
        }
        onSaveNote()
    }

    fun onDeleteNote() {
        state.value.noteId?.let {
            currentJob?.cancel()
            currentJob = viewModelScope.launch {
                notesService.delete(it)
            }
        }
    }

    fun onSaveNote() {
        if (state.value.noteTitle.isBlank() && state.value.noteText.isBlank()) return
        currentJob?.cancel()
        currentJob = viewModelScope.launch {
            val savedNote = notesService.save(
                NoteWithTags(
                    note = Note(
                        title = _state.value.noteTitle,
                        description = _state.value.noteText,
                        timestamp = System.currentTimeMillis(),
                        id = _state.value.noteId
                    ),
                    tags = state.value.tags
                        .filter { tagWState -> tagWState.isSelected }
                        .map { tagWState -> tagWState.tag }
                )
            )
            savedNote?.let {
                _state.update { it.copy(noteId = savedNote.note.id) }
            }
        }
    }

    fun onTagSelected(i: Int) {
        _state.update { currentState ->
            currentState.copy(
                tags = currentState.tags
                    .mapIndexed { j, item ->
                        if (i == j) {
                            item.copy(isSelected = !item.isSelected)
                        } else item
                    }
            )
        }
    }

    fun onAddTag() {
        currentJob = viewModelScope.launch {
            val savedTag = tagsService.save(
                Tag(
                    name = state.value.tagName
                )
            )
        }
    }

    fun onDeleteTag(id: Long?) {
        currentJob = viewModelScope.launch {
            tagsService.delete(id!!)
        }
    }

    fun onTagChange(tagName: String) {
        _state.update { currentState ->
            currentState.copy(
                tagName = tagName
            )
        }
    }
}
