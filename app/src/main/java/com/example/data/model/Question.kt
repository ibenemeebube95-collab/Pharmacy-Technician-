package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "questions")
data class Question(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val courseCode: String,      // e.g., "ENG", "ANA", "PPTP", "BDT", "AUM", "PHC"
    val courseName: String,      // e.g., "Anatomy & Physiology"
    val year: Int,               // 2018, 2019, 2020, 2021, 2022, 2023, 2024, 2025
    val questionNumber: Int,     // 1 to 100
    val questionText: String,
    val optionA: String,
    val optionB: String,
    val optionC: String,
    val optionD: String,
    val correctAnswer: String,   // "A", "B", "C", or "D"
    val explanation: String,
    val imageUrl: String? = null,
    val categoryTag: String = "General"
)
