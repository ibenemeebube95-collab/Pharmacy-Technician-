package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Course
import com.example.data.model.Question
import com.example.ui.components.WatermarkBackground
import com.example.ui.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PracticeScreen(
    viewModel: MainViewModel,
    courses: List<Course>,
    modifier: Modifier = Modifier
) {
    var selectedCourseCode by remember { mutableStateOf("ANA") }
    var selectedYear by remember { mutableIntStateOf(2025) }

    val questionsFlow = remember(selectedCourseCode, selectedYear) {
        viewModel.repository.getQuestionsByCourseAndYear(selectedCourseCode, selectedYear)
    }
    val questions by questionsFlow.collectAsState(initial = emptyList())

    val bookmarkedIds by viewModel.bookmarkedIds.collectAsState(initial = emptyList())

    var currentIndex by remember { mutableIntStateOf(0) }
    var selectedAnswer by remember { mutableStateOf<String?>(null) }
    var showExplanation by remember { mutableStateOf(false) }

    var courseDropdownExpanded by remember { mutableStateOf(false) }
    var yearDropdownExpanded by remember { mutableStateOf(false) }

    val years = listOf(2025, 2024, 2023, 2022, 2021, 2020, 2019, 2018)

    WatermarkBackground(modifier = modifier, alpha = 0.08f) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "PRACTICE QUESTIONS MODE",
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Selector Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Course Dropdown
                ExposedDropdownMenuBox(
                    expanded = courseDropdownExpanded,
                    onExpandedChange = { courseDropdownExpanded = !courseDropdownExpanded },
                    modifier = Modifier.weight(1.5f)
                ) {
                    OutlinedTextField(
                        value = courses.find { it.code == selectedCourseCode }?.name ?: selectedCourseCode,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Course") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = courseDropdownExpanded) },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = courseDropdownExpanded,
                        onDismissRequest = { courseDropdownExpanded = false }
                    ) {
                        courses.forEach { c ->
                            DropdownMenuItem(
                                text = { Text("${c.name} (${c.code})") },
                                onClick = {
                                    selectedCourseCode = c.code
                                    currentIndex = 0
                                    selectedAnswer = null
                                    showExplanation = false
                                    courseDropdownExpanded = false
                                }
                            )
                        }
                    }
                }

                // Year Dropdown
                ExposedDropdownMenuBox(
                    expanded = yearDropdownExpanded,
                    onExpandedChange = { yearDropdownExpanded = !yearDropdownExpanded },
                    modifier = Modifier.weight(1f)
                ) {
                    OutlinedTextField(
                        value = "$selectedYear",
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Year") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = yearDropdownExpanded) },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = yearDropdownExpanded,
                        onDismissRequest = { yearDropdownExpanded = false }
                    ) {
                        years.forEach { y ->
                            DropdownMenuItem(
                                text = { Text("$y Exam") },
                                onClick = {
                                    selectedYear = y
                                    currentIndex = 0
                                    selectedAnswer = null
                                    showExplanation = false
                                    yearDropdownExpanded = false
                                }
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (questions.isEmpty()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Loading questions or no questions available for this filter.")
                    }
                }
            } else {
                val q = questions.getOrNull(currentIndex) ?: questions.first()
                val isBookmarked = bookmarkedIds.contains(q.id)

                // Header bar
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.primaryContainer
                    ) {
                        Text(
                            text = "Question ${currentIndex + 1} of ${questions.size}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }

                    IconButton(onClick = { viewModel.toggleBookmark(q.id, q.courseCode) }) {
                        Icon(
                            imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                            contentDescription = "Bookmark",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Question Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.15f)
                        ) {
                            Text(
                                text = "${q.courseCode} $selectedYear • Tag: ${q.categoryTag}",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.secondary,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = q.questionText,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        // Option A
                        OptionItem(
                            optionKey = "A",
                            optionText = q.optionA,
                            correctKey = q.correctAnswer,
                            selectedKey = selectedAnswer,
                            onClick = {
                                selectedAnswer = "A"
                                showExplanation = true
                            }
                        )

                        // Option B
                        OptionItem(
                            optionKey = "B",
                            optionText = q.optionB,
                            correctKey = q.correctAnswer,
                            selectedKey = selectedAnswer,
                            onClick = {
                                selectedAnswer = "B"
                                showExplanation = true
                            }
                        )

                        // Option C
                        OptionItem(
                            optionKey = "C",
                            optionText = q.optionC,
                            correctKey = q.correctAnswer,
                            selectedKey = selectedAnswer,
                            onClick = {
                                selectedAnswer = "C"
                                showExplanation = true
                            }
                        )

                        // Option D
                        OptionItem(
                            optionKey = "D",
                            optionText = q.optionD,
                            correctKey = q.correctAnswer,
                            selectedKey = selectedAnswer,
                            onClick = {
                                selectedAnswer = "D"
                                showExplanation = true
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Explanation Box
                if (showExplanation) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (selectedAnswer == q.correctAnswer) Color(0xFFDCFCE7) else Color(0xFFFEE2E2)
                        )
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Info,
                                    contentDescription = null,
                                    tint = if (selectedAnswer == q.correctAnswer) Color(0xFF15803D) else Color(0xFFB91C1C)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = if (selectedAnswer == q.correctAnswer) "CORRECT ANSWER (${q.correctAnswer})" else "INCORRECT! Correct is (${q.correctAnswer})",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    color = if (selectedAnswer == q.correctAnswer) Color(0xFF15803D) else Color(0xFFB91C1C)
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = q.explanation,
                                fontSize = 13.sp,
                                color = Color.Black.copy(alpha = 0.85f)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Previous / Next Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = {
                            if (currentIndex > 0) {
                                currentIndex--
                                selectedAnswer = null
                                showExplanation = false
                            }
                        },
                        enabled = currentIndex > 0,
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Default.ChevronLeft, contentDescription = null)
                        Text("PREVIOUS")
                    }

                    Button(
                        onClick = {
                            if (currentIndex < questions.size - 1) {
                                currentIndex++
                                selectedAnswer = null
                                showExplanation = false
                            }
                        },
                        enabled = currentIndex < questions.size - 1,
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("NEXT")
                        Icon(Icons.Default.ChevronRight, contentDescription = null)
                    }
                }
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Composable
private fun OptionItem(
    optionKey: String,
    optionText: String,
    correctKey: String,
    selectedKey: String?,
    onClick: () -> Unit
) {
    val isSelected = selectedKey == optionKey
    val isCorrect = optionKey == correctKey

    val bgColor = when {
        selectedKey == null -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
        isSelected && isCorrect -> Color(0xFFDCFCE7)
        isSelected && !isCorrect -> Color(0xFFFEE2E2)
        !isSelected && isCorrect -> Color(0xFFDCFCE7)
        else -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f)
    }

    val borderColor = when {
        selectedKey == null -> MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
        isCorrect -> Color(0xFF16A34A)
        isSelected && !isCorrect -> Color(0xFFDC2626)
        else -> Color.Transparent
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        color = bgColor,
        border = androidx.compose.foundation.BorderStroke(1.5.dp, borderColor)
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = CircleShape,
                color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
                modifier = Modifier.size(28.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = optionKey,
                        fontWeight = FontWeight.Bold,
                        color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface,
                        fontSize = 13.sp
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = optionText,
                fontSize = 13.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f)
            )

            if (selectedKey != null && isCorrect) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = Color(0xFF16A34A),
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}
