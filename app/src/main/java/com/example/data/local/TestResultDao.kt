package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.model.TestResult
import kotlinx.coroutines.flow.Flow

@Dao
interface TestResultDao {
    @Query("SELECT * FROM test_results ORDER BY dateTimestamp DESC")
    fun getAllResults(): Flow<List<TestResult>>

    @Query("SELECT * FROM test_results WHERE courseCode = :courseCode ORDER BY dateTimestamp DESC")
    fun getResultsByCourse(courseCode: String): Flow<List<TestResult>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertResult(result: TestResult)

    @Query("DELETE FROM test_results")
    suspend fun clearHistory()
}
