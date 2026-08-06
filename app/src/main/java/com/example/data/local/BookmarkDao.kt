package com.example.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.model.Bookmark
import kotlinx.coroutines.flow.Flow

@Dao
interface BookmarkDao {
    @Query("SELECT * FROM bookmarks ORDER BY bookmarkedAt DESC")
    fun getAllBookmarks(): Flow<List<Bookmark>>

    @Query("SELECT questionId FROM bookmarks")
    fun getBookmarkedQuestionIds(): Flow<List<Long>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmark(bookmark: Bookmark)

    @Query("DELETE FROM bookmarks WHERE questionId = :questionId")
    suspend fun removeBookmark(questionId: Long)

    @Query("SELECT EXISTS(SELECT 1 FROM bookmarks WHERE questionId = :questionId)")
    suspend fun isBookmarked(questionId: Long): Boolean
}
