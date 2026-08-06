package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmarks")
data class Bookmark(
    @PrimaryKey val questionId: Long,
    val courseCode: String,
    val bookmarkedAt: Long = System.currentTimeMillis()
)
