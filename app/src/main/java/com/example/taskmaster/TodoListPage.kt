package com.example.taskmaster

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.reflect.KProperty

@Composable
fun TodoListPage(viewModel : TodoViewModel) {
//    val todoList = getFakeTodo()
    val todoList by viewModel.todoList.observeAsState()

    var inputText by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            OutlinedTextField(
                value = inputText,
                onValueChange = {
                    inputText = it
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 12.dp)
            )
            Button(onClick = {
                viewModel.addTodo(inputText)
                inputText = ""
            }) {
                Text(text = "Add")
            }
        }

        //Doing this safe call because LazyColumn doesn't accept a nullable list
        todoList?.let {
            LazyColumn(
                content = {
                    itemsIndexed(it) { index: Int, item : Todo ->
                        TodoItem(item = item, onDelete = {
                            viewModel.deleteTodo(item.id)
                            Log.d("Delete Called", item.title + item.id)
                        })
                    }
                }
            )
        }?: Text(
            text = "No Items yet",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 16.sp
        )
    }
}

@Composable
fun TodoItem(item: Todo, onDelete: ()->Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 16.dp, 16.dp, 0.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.primary)
            .padding(18.dp)
    ) {
        Column(
            modifier = Modifier.weight(0.5f)
        ) {
            Text(
                text = SimpleDateFormat("hh:mm a, dd MMMM yy", Locale.ENGLISH).format(item.createdAt),
                fontSize = 12.sp,
                color = Color.LightGray
            )
            Text(
                text = item.title,
                fontSize = 24.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }

        IconButton(onClick = onDelete) {
            Icon(
                painter = painterResource(id = R.drawable.delete_icon),
                contentDescription = "Delete",
                tint = Color.White
            )
        }
    }

}