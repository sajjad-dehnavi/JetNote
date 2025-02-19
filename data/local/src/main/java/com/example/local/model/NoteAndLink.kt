package com.example.local.model

import androidx.room.Entity

@Entity(
    tableName = "note_and_link",
    primaryKeys = ["noteUid", "linkId"]
)
data class NoteAndLink(
    val noteUid: String,
    val linkId: String
)