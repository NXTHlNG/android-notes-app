package com.example.notesapp.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.notesapp.ui.component.TagButton
import com.example.notesapp.ui.component.TagItem
import com.example.notesapp.ui.viewmodel.NoteDetailsViewModel
import kotlinx.coroutines.launch
import java.lang.Integer.min

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NoteDetailsScreen(
    navController: NavController,
//    noteColor: Int,
    viewModel: NoteDetailsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    val scaffoldState = rememberScaffoldState()

    val scope = rememberCoroutineScope()

    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
    )
    val coroutineScope = rememberCoroutineScope()

    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetPeekHeight = 0.dp,
        sheetBackgroundColor = MaterialTheme.colors.background,
        sheetContentColor = MaterialTheme.colors.onBackground,
        sheetContent = {
            Column(modifier = Modifier.heightIn(min = 0.dp, max = 500.dp)) {
                Row(modifier = Modifier) {
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(0.dp),
                        value = state.tagName,
                        onValueChange = {
                            viewModel.onTagChange(it)
                        },
                        placeholder = {
                            Text(text = "New tag", color = Color.Gray)
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            backgroundColor = Color.Transparent,
                        )
                    )
                }
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    item(state.tagName) {
                        if (state.tagName.isNotBlank())
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp, 8.dp)
                                    .clickable { viewModel.onAddTag() }
                            ) {
                                Text(text = "Add a tag \"${state.tagName}\"")
                            }
                        else {
                        }
                    }
                    items(state.tags.size) { i ->
                        TagItem(
                            tag = state.tags[i],
                            modifier = Modifier.clickable { viewModel.onTagSelected(i) },
                            onDeleteButtonClick = { viewModel.onDeleteTag(state.tags[i].tag.id) }
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                    }
                }
            }
        },
        topBar = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = {
                    viewModel.onSaveNote()
                    navController.navigate("notes")
                }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back"
                    )
                }
                IconButton(onClick = {
                    viewModel.onDeleteNote()
                    navController.navigate("notes")
                }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete note"
                    )
                }
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .statusBarsPadding()
                .navigationBarsPadding()
                .imePadding(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .imePadding()
                    .verticalScroll(rememberScrollState())
                    .weight(10f)
            ) {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp),
                    value = state.noteTitle,
                    onValueChange = { viewModel.onTitleChange(it) },
                    placeholder = {
                        Text(
                            text = "Title",
                            style = MaterialTheme.typography.h6,
                            color = Color.Gray
                        )
                    },
                    textStyle = MaterialTheme.typography.h6,
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        backgroundColor = Color.Transparent,
                    )
                )
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp),
                    value = state.noteText,
                    onValueChange = { viewModel.onContentChange(it) },
                    placeholder = {
                        Text("Text", color = Color.Gray)
                    },
                    textStyle = MaterialTheme.typography.body1,
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        backgroundColor = Color.Transparent,
                    )
                )
            }
            LazyRow(
                modifier = Modifier
//                    .fillMaxWidth()
                    .imePadding()
                    .fillMaxSize()
                    .weight(1f),
                verticalAlignment = Alignment.Bottom
            ) {
                items(min(state.tags.size, 5)) { i ->
                    TagButton(
                        tag = state.tags[i],
                        onClick = { viewModel.onTagSelected(i) },
                        modifier = Modifier
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                }
                item {
                    Button(onClick = {
                        coroutineScope.launch {
                            if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                                bottomSheetScaffoldState.bottomSheetState.expand()
                            } else {
                                bottomSheetScaffoldState.bottomSheetState.collapse()
                            }
                        }
                    }) {
                        Text(text = "...")
                    }
                }
            }
        }
    }
}
