package com.nithesh.roomdatabase_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.room.Room
import com.nithesh.roomdatabase_compose.data.TodoDao
import com.nithesh.roomdatabase_compose.data.TodoDatabase
import com.nithesh.roomdatabase_compose.data.TodoRepository
import com.nithesh.roomdatabase_compose.ui.theme.AddTodoScreen
import com.nithesh.roomdatabase_compose.ui.theme.EditTodoScreen
import com.nithesh.roomdatabase_compose.ui.theme.RoomDatabase_ComposeTheme
import com.nithesh.roomdatabase_compose.ui.theme.TodoListScreen
import com.nithesh.roomdatabase_compose.viewmodel.TodoViewModel
import com.nithesh.roomdatabase_compose.viewmodel.TodoViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = Room.databaseBuilder(
            applicationContext,
            TodoDatabase::class.java,
            "todo_db"
        ).build()

        val repository = TodoRepository(db.todoDao())
        val factory = TodoViewModelFactory(repository)

        setContent {
            val viewModel: TodoViewModel = viewModel(factory = factory)

            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = "todo_list") {
                    composable("todo_list") {
                        TodoListScreen(
                            viewModel = viewModel,
                            onAddClick = { navController.navigate("add_todo") },
                            onEditClick = {todoId ->
                                navController.navigate("edit_todo/$todoId")
                            })
                    }
                    composable("add_todo") {
                        AddTodoScreen(
                            onSave = { title ->
                                viewModel.addTodo(title)
                                navController.popBackStack()
                            },
                            onCancel = {
                                navController.popBackStack()
                            }

                        )

                    }
                composable(
                    route = "edit_todo/{id}",
                    arguments = listOf(navArgument("id") { type = NavType.IntType })
                ){backStackEntry ->
                    val id = backStackEntry.arguments?.getInt("id") ?: return@composable
                    EditTodoScreen(
                        todoId = id,
                        viewModel = viewModel,
                        onDone = { navController.popBackStack() }
                    )
                }
                }
            }
        }

    }

