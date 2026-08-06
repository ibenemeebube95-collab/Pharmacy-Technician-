package com.example.ui.screens

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.WatermarkBackground
import com.example.ui.viewmodel.MainViewModel
import com.example.ui.viewmodel.ScreenRoute

@Composable
fun ResultsScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val result by viewModel.latestResult.collectAsState()
    val activeQuestions by viewModel.activeQuestions.collectAsState()
    val selectedAnswers by viewModel.selectedAnswers.collectAsState()

    WatermarkBackground(modifier = modifier, alpha = 0.08f) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "CBT EXAMINATION SCORE REPORT",
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(14.dp))

            if (result == null) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("No recent result to display. Complete a mock test first.")
                    }
                }
            } else {
                val res = result!!
                val isPass = res.isPassed

                // Score Card Banner
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isPass) Color(0xFF064E3B) else Color(0xFF7F1D1D)
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = Color.White.copy(alpha = 0.2f),
                            modifier = Modifier.size(60.dp)
                        ) {
                            Icon(
                                imageVector = if (isPass) Icons.Default.CheckCircle else Icons.Default.Info,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier
                                    .padding(12.dp)
                                    .size(36.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = if (isPass) "PASSED BOARD STANDARDS" else "REQUIRES IMPROVEMENT",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )

                        Text(
                            text = "${res.percentage.toInt()}% Overall Grade",
                            color = Color.White,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 32.sp
                        )

                        Text(
                            text = "${res.score} Out of ${res.totalQuestions} Questions Correct",
                            color = Color.White.copy(alpha = 0.85f),
                            fontSize = 13.sp
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(20.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Timer, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "${res.timeTakenSeconds / 60}m ${res.timeTakenSeconds % 60}s",
                                    color = Color.White,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Star, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = res.courseName,
                                    color = Color.White,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Action Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(
                        onClick = { viewModel.startMockExam("ALL", 50, 45) },
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Icon(Icons.Default.Replay, contentDescription = null)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("RETRY MOCK")
                    }

                    Button(
                        onClick = { viewModel.navigateTo(ScreenRoute.ANALYTICS) },
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                    ) {
                        Text("ANALYTICS")
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Question by question review list
                Text(
                    text = "DETAILED QUESTION REVIEW & EXPLANATIONS",
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.align(Alignment.Start)
                )

                Spacer(modifier = Modifier.height(10.dp))

                activeQuestions.forEachIndexed { idx, question ->
                    val userAnswer = selectedAnswers[idx]
                    val isCorrect = userAnswer == question.correctAnswer

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Q${idx + 1} • ${question.courseCode}",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp
                                )

                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = if (isCorrect) Color(0xFFDCFCE7) else Color(0xFFFEE2E2)
                                ) {
                                    Text(
                                        text = if (isCorrect) "CORRECT" else "INCORRECT",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 11.sp,
                                        color = if (isCorrect) Color(0xFF15803D) else Color(0xFFB91C1C),
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = question.questionText,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "Your Answer: ${userAnswer ?: "None"} | Correct: ${question.correctAnswer}",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isCorrect) Color(0xFF15803D) else Color(0xFFB91C1C)
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = "Explanation: ${question.explanation}",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}
