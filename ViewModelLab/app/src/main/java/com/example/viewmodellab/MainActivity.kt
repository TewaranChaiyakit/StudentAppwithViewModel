package com.example.viewmodellab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.viewmodellab.ui.theme.ViewModelLabTheme

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ViewModelLabTheme {
                StudentApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentApp() {
    // Remember the ViewModel to maintain its state across recompositions
    var viewModel = remember { StudentViewModel() }

    // State to control the visibility of the input dialog
    var isShowDialog by remember { mutableStateOf(false) }

    // State for text fields in the input dialog
    var inputName by remember { mutableStateOf(TextFieldValue()) }
    var inputId by remember { mutableStateOf(TextFieldValue()) }

    // Show the input dialog when isShowDialog is true
    if (isShowDialog) {
        InputDialog(
            onCancel = {
                // Hide the dialog if canceled
                isShowDialog = false
            },
            onAddButton = {
                // Hide the dialog and add the student to the ViewModel
                isShowDialog = false
                viewModel.addStudent(inputName.text, inputId.text)
                // Clear the text fields
                inputName = TextFieldValue()
                inputId = TextFieldValue()
            },
            inputName = inputName,
            onNameChange = { inputName = it },
            inputId = inputId,
            onIdChange = { inputId = it }
        )
    }

    // Scaffold displaying the top app bar, FAB, and the student list
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Student App") },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color.Cyan,
                    titleContentColor = Color.Black
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { isShowDialog = true },
                containerColor = Color.Cyan,
                contentColor = Color.Black
            ) {
                Icon(Icons.Filled.Add, "Add new student")
            }
        }
    ) {
        // LazyColumn displaying the list of students from the ViewModel
        LazyColumn(
            modifier = Modifier.padding(it)
        ) {
            items(viewModel.data) { item ->
                // Display both student name and ID
                Column {
                    Text("Name: ${item.name}")
                    Text("ID: ${item.studentId}")
                    Divider(
                        color = Color.Gray,
                        thickness = 2.dp,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputDialog(
    onCancel: () -> Unit,
    onAddButton: () -> Unit,
    inputName: TextFieldValue,
    onNameChange: (TextFieldValue) -> Unit,
    inputId: TextFieldValue,
    onIdChange: (TextFieldValue) -> Unit
) {
    Dialog(onDismissRequest = onCancel) {
        Card(
            modifier = Modifier.padding(10.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                OutlinedTextField(
                    value = inputId,
                    onValueChange = onIdChange,
                    label = { Text("Student id") }
                )
                OutlinedTextField(
                    value = inputName,
                    onValueChange = onNameChange,
                    label = { Text("Student name") }
                )
                TextButton(
                    onClick = onAddButton,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Magenta
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                ) {
                    Text("Add")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    ViewModelLabTheme {
        StudentApp()
    }
}
