package com.example.taskmaster

import java.time.Instant
import java.util.Date

data class Todo(
    var id: Int,
    var title: String,
    var createdAt: Date
)

fun getFakeTodo(): List<Todo> {
    return listOf<Todo>(
        Todo(1, "First Task", Date()),
        Todo(2, "Second Task", Date()),
        Todo(3, "Third Task", Date()),
        Todo(4, "Fourth Task", Date()),
        Todo(5, "Five Task", Date()),
    )
}