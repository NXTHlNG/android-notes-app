package com.example.notesapp.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.notesapp.ui.component.Drawer
import com.example.notesapp.ui.component.NoteItem
import com.example.notesapp.ui.viewmodel.NotesViewModel
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TagsScreen(
    viewModel: NotesViewModel = hiltViewModel(),
    navController: NavController
) {
    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
        backgroundColor = MaterialTheme.colors.background,
        drawerBackgroundColor = MaterialTheme.colors.background,
        drawerContentColor = MaterialTheme.colors.onBackground,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("note")
                },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add note")
            }
        },
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colors.background,
                title = {
                },
                navigationIcon = {
                    IconButton(onClick = {
                        scope.launch {
                            scaffoldState.drawerState.open()
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Toggle drawer"
                        )
                    }
                }
            )
        },
        drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
        drawerContent = {
            Drawer(
                navController = navController)
        },
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(state.notes) { note ->
                    NoteItem(
                        note = note,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate(
                                    "note" +
                                            "?noteId=${note.note.id}"
                                )
                            },
//                        onDeleteClick = {
//                            viewModel.onEvent(NotesEvent.DeleteNote(note))
//                            scope.launch {
//                                val result = scaffoldState.snackbarHostState.showSnackbar(
//                                    message = "Note deleted",
//                                    actionLabel = "Undo"
//                                )
//                                if(result == SnackbarResult.ActionPerformed) {
//                                    viewModel.onEvent(NotesEvent.RestoreNote)
//                                }
//                            }
//                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}