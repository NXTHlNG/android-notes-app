package com.example.notesapp.ui.viewmodel

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.service.NotesService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject internal constructor(
    application: Application,
    private val notesService: NotesService,
) : AndroidViewModel(application) {

    private val _state = MutableStateFlow(NotesUIState())
    val state: StateFlow<NotesUIState> = _state.asStateFlow()

    private var getNotesJob: Job? = null

    init {
        getNotes()
    }

    fun getNotes() {
        getNotesJob?.cancel()
        getNotesJob = notesService.getAllWithTags()
            .onEach { notes ->
                _state.update { currentState ->
                    currentState.copy(
                        notes = notes
                    )
                }
            }
            .launchIn(viewModelScope)
    }
}