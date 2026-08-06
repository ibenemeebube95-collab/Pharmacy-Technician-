package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.model.Question
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestionDao {
    @Query("SELECT * FROM questions WHERE courseCode = :courseCode AND year = :year ORDER BY questionNumber ASC")
    fun getQuestionsByCourseAndYear(courseCode: String, year: Int): Flow<List<Question>>

    @Query("SELECT * FROM questions WHERE courseCode = :courseCode ORDER BY year DESC, questionNumber ASC")
    fun getQuestionsByCourse(courseCode: String): Flow<List<Question>>

    @Query("SELECT * FROM questions WHERE year = :year ORDER BY questionNumber ASC")
    fun getQuestionsByYear(year: Int): Flow<List<Question>>

    @Query("SELECT * FROM questions ORDER BY RANDOM() LIMIT :limit")
    suspend fun getRandomMockQuestions(limit: Int): List<Question>

    @Query("SELECT * FROM questions WHERE courseCode = :courseCode ORDER BY RANDOM() LIMIT :limit")
    suspend fun getRandomMockQuestionsForCourse(courseCode: String, limit: Int): List<Question>

    @Query("SELECT * FROM questions WHERE id IN (:ids)")
    fun getQuestionsByIds(ids: List<Long>): Flow<List<Question>>

    @Query("SELECT COUNT(*) FROM questions")
    suspend fun getQuestionCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(questions: List<Question>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(question: Question)
}
