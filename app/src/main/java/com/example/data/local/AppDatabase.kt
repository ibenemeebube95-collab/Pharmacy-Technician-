package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.model.Bookmark
import com.example.data.model.Course
import com.example.data.model.PaymentRecord
import com.example.data.model.Question
import com.example.data.model.TestResult

@Database(
    entities = [Question::class, TestResult::class, Bookmark::class, Course::class, PaymentRecord::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun questionDao(): QuestionDao
    abstract fun testResultDao(): TestResultDao
    abstract fun bookmarkDao(): BookmarkDao
    abstract fun courseDao(): CourseDao
    abstract fun paymentDao(): PaymentDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "pharmacy_cbt_db"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
