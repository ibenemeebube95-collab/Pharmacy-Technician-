package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "courses")
data class Course(
    @PrimaryKey val code: String,       // e.g., "ENG", "ANA", "PPTP", "BDT", "AUM", "PHC"
    val name: String,                   // e.g., "Anatomy & Physiology"
    val fullName: String,               // e.g., "Anatomy and Human Physiology for Pharmacy Techs"
    val iconName: String,               // Icon identifier string
    val description: String,
    val totalQuestions: Int = 800,
    val isCore: Boolean = true
)
