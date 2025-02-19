package com.example.local.model

import androidx.room.Entity

@Entity(
    tableName = "note_and_todo",
    primaryKeys = ["noteUid","todoId"]
)
data class NoteAndTodo(
    val noteUid:String,
    val todoId:Long
)
