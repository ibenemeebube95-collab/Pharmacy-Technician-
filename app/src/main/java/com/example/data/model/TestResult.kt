package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "test_results")
data class TestResult(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val testType: String,             // "MOCK_EXAM", "PRACTICE_MODE", "PAST_QUESTIONS"
    val courseCode: String,           // e.g., "ALL" or "PPTP"
    val courseName: String,
    val year: Int,                    // Year selected or 0 for randomized
    val score: Int,                   // Correct answer count
    val totalQuestions: Int,          // Total questions in session
    val percentage: Float,            // e.g. 85.5f
    val timeTakenSeconds: Long,       // Time taken in seconds
    val dateTimestamp: Long = System.currentTimeMillis(),
    val isPassed: Boolean             // e.g., score >= 50%
)
