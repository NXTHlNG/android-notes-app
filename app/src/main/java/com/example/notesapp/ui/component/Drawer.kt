package com.example.notesapp.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Label
import androidx.compose.material.icons.filled.Notes
import androidx.compose.material.icons.filled.Tag
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.notesapp.Screen

data class MenuItem(
    val route: String,
    val title: String,
    val contentDescription: String,
    val icon: ImageVector
)

private val items: List<MenuItem> = listOf(
    MenuItem(
        Screen.NotesScreen.route,
        "Notes",
        "navigate to notes",
        Icons.Default.Notes
    ),
    MenuItem(
        Screen.TagsScreen.route,
        "Tags",
        "navigate to tags",
        Icons.Default.Label
    )
)

@Composable
fun Drawer(
    modifier: Modifier = Modifier,
    itemTextStyle: TextStyle = TextStyle(fontSize = 18.sp),
    navController: NavController
) {
    val currentRoute = navController.currentBackStackEntry?.destination?.route

    Column(
        modifier = Modifier
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
        ) {
            Text(text = "NotesApp", style = MaterialTheme.typography.h5)
        }
        LazyColumn(modifier) {
            items(items) { item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate(item.route)
                        }
                        .background(
                            if (item.route === currentRoute)
                                MaterialTheme.colors.surface
                            else MaterialTheme.colors.background
                        )
                        .padding(16.dp, 8.dp)
                ) {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.contentDescription
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = item.title,
                        style = itemTextStyle,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}