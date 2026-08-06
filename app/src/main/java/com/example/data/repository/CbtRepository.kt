package com.example.data.repository

import android.content.Context
import com.example.data.local.AppDatabase
import com.example.data.local.PharmacyDataSeeder
import com.example.data.model.Bookmark
import com.example.data.model.Course
import com.example.data.model.PaymentRecord
import com.example.data.model.Question
import com.example.data.model.TestResult
import com.example.data.model.UserAccount
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext

class CbtRepository(context: Context) {
    private val db = AppDatabase.getDatabase(context)
    private val questionDao = db.questionDao()
    private val testResultDao = db.testResultDao()
    private val bookmarkDao = db.bookmarkDao()
    private val courseDao = db.courseDao()
    private val paymentDao = db.paymentDao()

    private val _userAccount = MutableStateFlow(
        UserAccount(
            id = "USR_8921",
            fullName = "Ibrahim Abubakar",
            email = "i.abubakar@pharmtech.ng",
            phone = "+234 812 345 6789",
            institution = "School of Health Tech, Kano",
            state = "Kano State",
            indexNumber = "PHT/2024/0411",
            isPremium = false
        )
    )
    val userAccount: StateFlow<UserAccount> = _userAccount.asStateFlow()

    suspend fun checkAndSeedDatabase() = withContext(Dispatchers.IO) {
        val count = questionDao.getQuestionCount()
        if (count == 0) {
            courseDao.insertAll(PharmacyDataSeeder.defaultCourses)
            questionDao.insertAll(PharmacyDataSeeder.generateInitialQuestions())
        }
    }

    fun getAllCourses(): Flow<List<Course>> = courseDao.getAllCourses()

    suspend fun addCourse(course: Course) = withContext(Dispatchers.IO) {
        courseDao.insertCourse(course)
    }

    fun getQuestionsByCourseAndYear(courseCode: String, year: Int): Flow<List<Question>> {
        return questionDao.getQuestionsByCourseAndYear(courseCode, year)
    }

    fun getQuestionsByCourse(courseCode: String): Flow<List<Question>> {
        return questionDao.getQuestionsByCourse(courseCode)
    }

    fun getQuestionsByYear(year: Int): Flow<List<Question>> {
        return questionDao.getQuestionsByYear(year)
    }

    suspend fun getMockExamQuestions(courseCode: String = "ALL", limit: Int = 50): List<Question> {
        return withContext(Dispatchers.IO) {
            if (courseCode == "ALL") {
                questionDao.getRandomMockQuestions(limit)
            } else {
                questionDao.getRandomMockQuestionsForCourse(courseCode, limit)
            }
        }
    }

    suspend fun saveTestResult(result: TestResult) = withContext(Dispatchers.IO) {
        testResultDao.insertResult(result)
    }

    fun getAllResults(): Flow<List<TestResult>> = testResultDao.getAllResults()

    suspend fun toggleBookmark(questionId: Long, courseCode: String) = withContext(Dispatchers.IO) {
        if (bookmarkDao.isBookmarked(questionId)) {
            bookmarkDao.removeBookmark(questionId)
        } else {
            bookmarkDao.insertBookmark(Bookmark(questionId = questionId, courseCode = courseCode))
        }
    }

    fun getBookmarkedQuestionIds(): Flow<List<Long>> = bookmarkDao.getBookmarkedQuestionIds()

    suspend fun isBookmarked(questionId: Long): Boolean = withContext(Dispatchers.IO) {
        bookmarkDao.isBookmarked(questionId)
    }

    suspend fun submitPaymentRecord(payment: PaymentRecord) = withContext(Dispatchers.IO) {
        paymentDao.insertPayment(payment)
    }

    fun getAllPaymentRecords(): Flow<List<PaymentRecord>> = paymentDao.getAllPaymentRecords()

    fun updatePremiumStatus(isPremium: Boolean) {
        _userAccount.value = _userAccount.value.copy(
            isPremium = isPremium,
            activationDate = if (isPremium) "2026-08-06" else null
        )
    }

    fun updateUserProfile(fullName: String, email: String, phone: String, institution: String, state: String) {
        _userAccount.value = _userAccount.value.copy(
            fullName = fullName,
            email = email,
            phone = phone,
            institution = institution,
            state = state
        )
    }
}
