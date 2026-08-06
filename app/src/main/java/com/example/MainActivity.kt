package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.components.DrawerContent
import com.example.ui.screens.AboutContactScreen
import com.example.ui.screens.AddCourseScreen
import com.example.ui.screens.AiTutorScreen
import com.example.ui.screens.AnalyticsScreen
import com.example.ui.screens.DownloadsScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.LeaderboardScreen
import com.example.ui.screens.LoginScreen
import com.example.ui.screens.MockExamScreen
import com.example.ui.screens.NotificationsScreen
import com.example.ui.screens.PastQuestionsScreen
import com.example.ui.screens.PracticeScreen
import com.example.ui.screens.PremiumScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.ResultsScreen
import com.example.ui.screens.SettingsScreen
import com.example.ui.theme.PharmacyCbtTheme
import com.example.ui.viewmodel.MainViewModel
import com.example.ui.viewmodel.ScreenRoute
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val viewModel: MainViewModel = viewModel()
            val currentRoute by viewModel.currentRoute.collectAsState()
            val userAccount by viewModel.userAccount.collectAsState()
            val courses by viewModel.courses.collectAsState(initial = emptyList())
            val recentResults by viewModel.recentResults.collectAsState(initial = emptyList())

            var isDarkTheme by remember { mutableStateOf(false) }

            PharmacyCbtTheme(darkTheme = isDarkTheme) {
                if (currentRoute == ScreenRoute.LOGIN) {
                    LoginScreen(
                        isDarkTheme = isDarkTheme,
                        onToggleDarkTheme = { isDarkTheme = !isDarkTheme },
                        onLoginSuccess = { viewModel.navigateTo(ScreenRoute.HOME) }
                    )
                } else {
                    MainAppScaffold(
                        viewModel = viewModel,
                        currentRoute = currentRoute,
                        userAccount = userAccount,
                        courses = courses,
                        recentResults = recentResults,
                        isDarkTheme = isDarkTheme,
                        onToggleDarkTheme = { isDarkTheme = !isDarkTheme }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppScaffold(
    viewModel: MainViewModel,
    currentRoute: ScreenRoute,
    userAccount: com.example.data.model.UserAccount,
    courses: List<com.example.data.model.Course>,
    recentResults: List<com.example.data.model.TestResult>,
    isDarkTheme: Boolean,
    onToggleDarkTheme: () -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val screenTitle = when (currentRoute) {
        ScreenRoute.HOME -> "Pharmacy Tech CBT Pro"
        ScreenRoute.PRACTICE -> "Practice Questions Mode"
        ScreenRoute.MOCK_EXAM -> "Timed CBT Mock Examination"
        ScreenRoute.PAST_QUESTIONS -> "National Past Questions (2018-2025)"
        ScreenRoute.RESULTS -> "CBT Examination Score Report"
        ScreenRoute.ANALYTICS -> "Board Readiness Analytics"
        ScreenRoute.LEADERBOARD -> "National Candidate Rankings"
        ScreenRoute.PREMIUM -> "Pro Activation (Sterling Bank)"
        ScreenRoute.DOWNLOADS -> "Offline Question Database"
        ScreenRoute.NOTIFICATIONS -> "PCN Board Notifications"
        ScreenRoute.PROFILE -> "Candidate Profile"
        ScreenRoute.SETTINGS -> "Application Settings"
        ScreenRoute.ABOUT -> "About App & Council Rules"
        ScreenRoute.CONTACT -> "Support & WhatsApp Hotline"
        ScreenRoute.AI_TUTOR -> "AI Pharmacy Tutor"
        ScreenRoute.ADD_COURSE -> "Admin - Add New Course"
        else -> "Pharmacy Tech CBT Pro"
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
                currentRoute = currentRoute,
                userAccount = userAccount,
                onSelectRoute = { route ->
                    scope.launch { drawerState.close() }
                    viewModel.navigateTo(route)
                }
            )
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = screenTitle,
                            fontWeight = FontWeight.Bold,
                            fontSize = 17.sp,
                            maxLines = 1
                        )
                    },
                    navigationIcon = {
                        if (currentRoute == ScreenRoute.HOME) {
                            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                Icon(Icons.Default.Menu, contentDescription = "Menu")
                            }
                        } else {
                            IconButton(onClick = { viewModel.navigateTo(ScreenRoute.HOME) }) {
                                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                            }
                        }
                    },
                    actions = {
                        if (currentRoute != ScreenRoute.AI_TUTOR) {
                            IconButton(onClick = { viewModel.navigateTo(ScreenRoute.AI_TUTOR) }) {
                                Icon(
                                    imageVector = Icons.Default.AutoAwesome,
                                    contentDescription = "AI Tutor",
                                    tint = MaterialTheme.colorScheme.tertiary
                                )
                            }
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        titleContentColor = MaterialTheme.colorScheme.primary
                    )
                )
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                AnimatedContent(
                    targetState = currentRoute,
                    transitionSpec = { fadeIn() togetherWith fadeOut() },
                    label = "screenTransition"
                ) { route ->
                    when (route) {
                        ScreenRoute.HOME -> HomeScreen(
                            userAccount = userAccount,
                            courses = courses,
                            recentResults = recentResults,
                            onNavigate = { viewModel.navigateTo(it) },
                            onStartMockExam = { viewModel.startMockExam("ALL", 50, 45) }
                        )
                        ScreenRoute.PRACTICE -> PracticeScreen(
                            viewModel = viewModel,
                            courses = courses
                        )
                        ScreenRoute.MOCK_EXAM -> MockExamScreen(
                            viewModel = viewModel
                        )
                        ScreenRoute.PAST_QUESTIONS -> PastQuestionsScreen(
                            viewModel = viewModel,
                            courses = courses,
                            userAccount = userAccount
                        )
                        ScreenRoute.RESULTS -> ResultsScreen(
                            viewModel = viewModel
                        )
                        ScreenRoute.ANALYTICS -> AnalyticsScreen(
                            viewModel = viewModel
                        )
                        ScreenRoute.LEADERBOARD -> LeaderboardScreen()
                        ScreenRoute.PREMIUM -> PremiumScreen(
                            viewModel = viewModel,
                            userAccount = userAccount
                        )
                        ScreenRoute.DOWNLOADS -> DownloadsScreen()
                        ScreenRoute.NOTIFICATIONS -> NotificationsScreen()
                        ScreenRoute.PROFILE -> ProfileScreen(
                            viewModel = viewModel,
                            userAccount = userAccount
                        )
                        ScreenRoute.SETTINGS -> SettingsScreen(
                            isDarkTheme = isDarkTheme,
                            onToggleDarkTheme = onToggleDarkTheme
                        )
                        ScreenRoute.ABOUT -> AboutContactScreen(isContactOnly = false)
                        ScreenRoute.CONTACT -> AboutContactScreen(isContactOnly = true)
                        ScreenRoute.AI_TUTOR -> AiTutorScreen(viewModel = viewModel)
                        ScreenRoute.ADD_COURSE -> AddCourseScreen(viewModel = viewModel)
                        else -> HomeScreen(
                            userAccount = userAccount,
                            courses = courses,
                            recentResults = recentResults,
                            onNavigate = { viewModel.navigateTo(it) },
                            onStartMockExam = { viewModel.startMockExam("ALL", 50, 45) }
                        )
                    }
                }
            }
        }
    }
}
