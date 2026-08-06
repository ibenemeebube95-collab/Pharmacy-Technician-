package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.GridOn
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.WatermarkBackground
import com.example.ui.viewmodel.MainViewModel

@Composable
fun MockExamScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val activeQuestions by viewModel.activeQuestions.collectAsState()
    val currentIndex by viewModel.currentQuestionIndex.collectAsState()
    val selectedAnswers by viewModel.selectedAnswers.collectAsState()
    val flaggedQuestions by viewModel.flaggedQuestions.collectAsState()
    val timerSeconds by viewModel.timerSeconds.collectAsState()

    var showPaletteGrid by remember { mutableStateOf(false) }
    var showSubmitConfirmation by remember { mutableStateOf(false) }

    if (activeQuestions.isEmpty()) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("No exam active.", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(12.dp))
                Button(onClick = { viewModel.startMockExam("ALL", 50, 45) }) {
                    Text("START 50-QUESTION MOCK EXAM")
                }
            }
        }
        return
    }

    val q = activeQuestions.getOrNull(currentIndex) ?: activeQuestions.first()
    val isFlagged = flaggedQuestions.contains(currentIndex)

    val mins = timerSeconds / 60
    val secs = timerSeconds % 60
    val formattedTime = String.format("%02d:%02d", mins, secs)

    WatermarkBackground(modifier = modifier, alpha = 0.08f) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Timer and Control Banner
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Timer, contentDescription = null, tint = Color.White)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = formattedTime,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = { showPaletteGrid = !showPaletteGrid }) {
                            Icon(
                                imageVector = Icons.Default.GridOn,
                                contentDescription = "Question Palette",
                                tint = Color.White
                            )
                        }

                        Button(
                            onClick = { showSubmitConfirmation = true },
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Icon(Icons.Default.Send, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("SUBMIT", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color.White)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Question Palette Grid overlay
            if (showPaletteGrid) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "QUESTION PALETTE GRID (${selectedAnswers.size}/${activeQuestions.size} Answered)",
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                        // Render 50 question circles
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(7),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                        ) {
                            items(activeQuestions.size) { idx ->
                                val isAnswered = selectedAnswers.containsKey(idx)
                                val isCurrent = idx == currentIndex
                                val isFlaggedItem = flaggedQuestions.contains(idx)

                                val itemBg = when {
                                    isCurrent -> MaterialTheme.colorScheme.primary
                                    isAnswered -> MaterialTheme.colorScheme.secondary
                                    isFlaggedItem -> MaterialTheme.colorScheme.tertiary
                                    else -> MaterialTheme.colorScheme.surfaceVariant
                                }

                                Box(
                                    modifier = Modifier
                                        .padding(3.dp)
                                        .size(36.dp)
                                        .clip(CircleShape)
                                        .background(itemBg)
                                        .clickable {
                                            viewModel.goToQuestion(idx)
                                            showPaletteGrid = false
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "${idx + 1}",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isCurrent || isAnswered || isFlaggedItem) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }

            // Question Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
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
                                text = "Question ${currentIndex + 1} of ${activeQuestions.size}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                            )
                        }

                        IconButton(onClick = { viewModel.toggleFlagQuestion(currentIndex) }) {
                            Icon(
                                imageVector = Icons.Default.Flag,
                                contentDescription = "Flag Question",
                                tint = if (isFlagged) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "[${q.courseCode}] ${q.questionText}",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Option Radios
                    listOf("A" to q.optionA, "B" to q.optionB, "C" to q.optionC, "D" to q.optionD).forEach { (key, optText) ->
                        val isSelected = selectedAnswers[currentIndex] == key
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .clickable { viewModel.selectAnswer(currentIndex, key) },
                            shape = RoundedCornerShape(12.dp),
                            color = if (isSelected) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f),
                            border = androidx.compose.foundation.BorderStroke(
                                1.dp,
                                if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent
                            )
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = isSelected,
                                    onClick = { viewModel.selectAnswer(currentIndex, key) },
                                    colors = RadioButtonDefaults.colors(selectedColor = MaterialTheme.colorScheme.primary)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "$key. $optText",
                                    fontSize = 13.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Navigation Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = { viewModel.goToQuestion(currentIndex - 1) },
                    enabled = currentIndex > 0,
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Default.ChevronLeft, contentDescription = null)
                    Text("PREVIOUS")
                }

                Button(
                    onClick = { viewModel.goToQuestion(currentIndex + 1) },
                    enabled = currentIndex < activeQuestions.size - 1,
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("NEXT")
                    Icon(Icons.Default.ChevronRight, contentDescription = null)
                }
            }

            Spacer(modifier = Modifier.height(30.dp))
        }

        // Submit Dialog
        if (showSubmitConfirmation) {
            val answeredCount = selectedAnswers.size
            val unAnsweredCount = activeQuestions.size - answeredCount

            AlertDialog(
                onDismissRequest = { showSubmitConfirmation = false },
                title = { Text("Submit CBT Mock Examination?") },
                text = {
                    Column {
                        Text("You have answered $answeredCount of ${activeQuestions.size} questions.")
                        if (unAnsweredCount > 0) {
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "Warning: You have $unAnsweredCount unanswered questions!",
                                color = MaterialTheme.colorScheme.tertiary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Are you sure you want to finalize and grade your examination now?")
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            showSubmitConfirmation = false
                            viewModel.submitExam("MOCK_EXAM")
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary)
                    ) {
                        Text("SUBMIT & GRADE", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showSubmitConfirmation = false }) {
                        Text("Continue Exam")
                    }
                }
            )
        }
    }
}
