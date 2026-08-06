package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.Course
import com.example.data.model.PaymentRecord
import com.example.data.model.Question
import com.example.data.model.TestResult
import com.example.data.model.UserAccount
import com.example.data.repository.CbtRepository
import com.google.firebase.ai.FirebaseAI
import com.google.firebase.ai.type.content
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

enum class ScreenRoute(val title: String) {
    LOGIN("Login"),
    HOME("Home"),
    PRACTICE("Practice Questions"),
    MOCK_EXAM("Mock Examination"),
    PAST_QUESTIONS("Past Questions"),
    RESULTS("Results"),
    ANALYTICS("Performance Analytics"),
    LEADERBOARD("Leaderboard"),
    PREMIUM("Premium"),
    DOWNLOADS("Downloads"),
    NOTIFICATIONS("Notifications"),
    PROFILE("Profile"),
    SETTINGS("Settings"),
    ABOUT("About"),
    CONTACT("Contact"),
    AI_TUTOR("AI Pharmacy Tutor"),
    ADD_COURSE("Add Course")
}

data class AiChatMessage(
    val sender: String, // "USER" or "AI"
    val text: String,
    val timestamp: Long = System.currentTimeMillis()
)

class MainViewModel(application: Application) : AndroidViewModel(application) {
    val repository = CbtRepository(application)

    private val _isDarkTheme = MutableStateFlow(false)
    val isDarkTheme: StateFlow<Boolean> = _isDarkTheme.asStateFlow()

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    private val _currentRoute = MutableStateFlow(ScreenRoute.HOME)
    val currentRoute: StateFlow<ScreenRoute> = _currentRoute.asStateFlow()

    val courses = repository.getAllCourses()
    val userAccount = repository.userAccount
    val allResults = repository.getAllResults()
    val recentResults = repository.getAllResults()
    val paymentRecords = repository.getAllPaymentRecords()
    val bookmarkedIds = repository.getBookmarkedQuestionIds()

    // CBT Active Session State
    private val _activeQuestions = MutableStateFlow<List<Question>>(emptyList())
    val activeQuestions: StateFlow<List<Question>> = _activeQuestions.asStateFlow()

    private val _currentQuestionIndex = MutableStateFlow(0)
    val currentQuestionIndex: StateFlow<Int> = _currentQuestionIndex.asStateFlow()

    private val _selectedAnswers = MutableStateFlow<Map<Int, String>>(emptyMap())
    val selectedAnswers: StateFlow<Map<Int, String>> = _selectedAnswers.asStateFlow()

    private val _flaggedQuestions = MutableStateFlow<Set<Int>>(emptySet())
    val flaggedQuestions: StateFlow<Set<Int>> = _flaggedQuestions.asStateFlow()

    private val _timerSeconds = MutableStateFlow(3000L) // 50 mins default
    val timerSeconds: StateFlow<Long> = _timerSeconds.asStateFlow()

    private val _isExamSubmitted = MutableStateFlow(false)
    val isExamSubmitted: StateFlow<Boolean> = _isExamSubmitted.asStateFlow()

    private val _latestResult = MutableStateFlow<TestResult?>(null)
    val latestResult: StateFlow<TestResult?> = _latestResult.asStateFlow()

    // AI Tutor State
    private val _aiMessages = MutableStateFlow<List<AiChatMessage>>(
        listOf(
            AiChatMessage("AI", "Hello! I am your AI Pharmacy Tutor. Ask me anything about pharmacology, dosage calculations, prescription abbreviations, or Anatomy & Physiology!")
        )
    )
    val aiMessages: StateFlow<List<AiChatMessage>> = _aiMessages.asStateFlow()

    private val _isAiLoading = MutableStateFlow(false)
    val isAiLoading: StateFlow<Boolean> = _isAiLoading.asStateFlow()

    private var timerJob: Job? = null

    init {
        viewModelScope.launch {
            repository.checkAndSeedDatabase()
        }
    }

    fun toggleDarkTheme() {
        _isDarkTheme.value = !_isDarkTheme.value
    }

    fun setLoggedIn(loggedIn: Boolean) {
        _isLoggedIn.value = loggedIn
        if (loggedIn) {
            _currentRoute.value = ScreenRoute.HOME
        } else {
            _currentRoute.value = ScreenRoute.LOGIN
        }
    }

    fun navigateTo(route: ScreenRoute) {
        _currentRoute.value = route
    }

    // CBT Session Functions
    fun startMockExam(courseCode: String = "ALL", questionLimit: Int = 50, durationMinutes: Int = 45) {
        viewModelScope.launch {
            val questions = repository.getMockExamQuestions(courseCode, questionLimit)
            _activeQuestions.value = questions
            _currentQuestionIndex.value = 0
            _selectedAnswers.value = emptyMap()
            _flaggedQuestions.value = emptySet()
            _timerSeconds.value = (durationMinutes * 60).toLong()
            _isExamSubmitted.value = false
            _latestResult.value = null
            _currentRoute.value = ScreenRoute.MOCK_EXAM

            startTimer()
        }
    }

    fun startPracticeSession(questions: List<Question>) {
        _activeQuestions.value = questions
        _currentQuestionIndex.value = 0
        _selectedAnswers.value = emptyMap()
        _flaggedQuestions.value = emptySet()
        _timerSeconds.value = 0L
        _isExamSubmitted.value = false
        _latestResult.value = null
        _currentRoute.value = ScreenRoute.PRACTICE
    }

    private fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (_timerSeconds.value > 0 && !_isExamSubmitted.value) {
                delay(1000)
                _timerSeconds.value -= 1
            }
            if (_timerSeconds.value <= 0 && !_isExamSubmitted.value) {
                submitExam("MOCK_EXAM")
            }
        }
    }

    fun selectAnswer(questionIndex: Int, option: String) {
        val map = _selectedAnswers.value.toMutableMap()
        map[questionIndex] = option
        _selectedAnswers.value = map
    }

    fun toggleFlagQuestion(questionIndex: Int) {
        val set = _flaggedQuestions.value.toMutableSet()
        if (set.contains(questionIndex)) {
            set.remove(questionIndex)
        } else {
            set.add(questionIndex)
        }
        _flaggedQuestions.value = set
    }

    fun goToQuestion(index: Int) {
        if (index in 0 until _activeQuestions.value.size) {
            _currentQuestionIndex.value = index
        }
    }

    fun submitExam(testType: String = "MOCK_EXAM") {
        timerJob?.cancel()
        _isExamSubmitted.value = true

        val questions = _activeQuestions.value
        val answers = _selectedAnswers.value
        var correctCount = 0

        questions.forEachIndexed { idx, q ->
            if (answers[idx] == q.correctAnswer) {
                correctCount++
            }
        }

        val total = questions.size
        val percentage = if (total > 0) (correctCount.toFloat() / total) * 100f else 0f
        val timeSpent = (45 * 60) - _timerSeconds.value

        val courseCode = questions.firstOrNull()?.courseCode ?: "ALL"
        val courseName = if (courseCode == "ALL") "All Core Courses" else questions.firstOrNull()?.courseName ?: "Course Practice"

        val result = TestResult(
            testType = testType,
            courseCode = courseCode,
            courseName = courseName,
            year = questions.firstOrNull()?.year ?: 2025,
            score = correctCount,
            totalQuestions = total,
            percentage = percentage,
            timeTakenSeconds = timeSpent,
            isPassed = percentage >= 50f
        )

        _latestResult.value = result

        viewModelScope.launch {
            repository.saveTestResult(result)
            _currentRoute.value = ScreenRoute.RESULTS
        }
    }

    fun toggleBookmark(questionId: Long, courseCode: String) {
        viewModelScope.launch {
            repository.toggleBookmark(questionId, courseCode)
        }
    }

    // Payment Submission
    fun submitSterlingTransfer(accountName: String, refNumber: String, transferDate: String) {
        val user = userAccount.value
        val record = PaymentRecord(
            userEmail = user.email,
            accountName = accountName,
            bankName = "Sterling Bank",
            amountPaidNgn = 3500,
            referenceNumber = refNumber,
            transferDate = transferDate,
            status = "VERIFIED" // Instant verification simulation for demo!
        )
        viewModelScope.launch {
            repository.submitPaymentRecord(record)
            repository.updatePremiumStatus(true)
        }
    }

    // Dynamic Course Creation
    fun addNewCourse(code: String, name: String, fullName: String, description: String) {
        val newCourse = Course(
            code = code.uppercase(),
            name = name,
            fullName = fullName,
            iconName = "extension",
            description = description,
            totalQuestions = 100,
            isCore = false
        )
        viewModelScope.launch {
            repository.addCourse(newCourse)
            _currentRoute.value = ScreenRoute.HOME
        }
    }

    // AI Tutor Chat Query
    fun sendAiMessage(promptText: String) {
        if (promptText.isBlank()) return

        val userMsg = AiChatMessage("USER", promptText)
        _aiMessages.value = _aiMessages.value + userMsg
        _isAiLoading.value = true

        viewModelScope.launch {
            delay(1000)
            val fallbackReply = generateFallbackAiReply(promptText)
            _aiMessages.value = _aiMessages.value + AiChatMessage("AI", fallbackReply)
            _isAiLoading.value = false
        }
    }

    private fun generateFallbackAiReply(query: String): String {
        val q = query.lowercase()
        return when {
            q.contains("calculation") || q.contains("dosage") || q.contains("ml") || q.contains("mg") -> {
                "Formula for Dosage Calculation:\nDesired Dose (D) / Hand Dose (H) × Quantity (V) = Amount to Administer.\nFor liquid solutions, ensure percent w/v conversion: 1% w/v = 1 g in 100 mL (10 mg/mL)."
            }
            q.contains("malaria") || q.contains("act") || q.contains("artemether") -> {
                "Artemether-Lumefantrine (ACT) is the 1st-line treatment for uncomplicated P. falciparum malaria in Nigeria. Administer with fatty food or milk to enhance Lumefantrine absorption."
            }
            q.contains("epi") || q.contains("vaccine") || q.contains("bcg") -> {
                "Nigeria EPI Schedule Highlights:\n- Birth: BCG, OPV 0, Hep B 0\n- 6 Weeks: Penta 1, OPV 1, PCV 1, Rotavirus 1\n- 10 Weeks: Penta 2, OPV 2, PCV 2\n- 14 Weeks: Penta 3, OPV 3, PCV 3, IPV\n- 9 Months: Measles 1, Yellow Fever"
            }
            q.contains("autoclave") || q.contains("steril") -> {
                "Standard Autoclaving parameters: 121°C (250°F) at 15 psi pressure for 15–20 minutes using saturated steam. Kills bacterial spores."
            }
            else -> {
                "In Pharmacy Technician Board Practice, always verify the 5 Rights of Medication Administration: Right Patient, Right Drug, Right Dose, Right Route, and Right Time."
            }
        }
    }
}
