package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
fun AnalyticsScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val results by viewModel.allResults.collectAsState(initial = emptyList())

    val totalTests = results.size
    val totalQuestionsAnswered = results.sumOf { it.totalQuestions }
    val totalScore = results.sumOf { it.score }
    val overallPercentage = if (totalQuestionsAnswered > 0) (totalScore.toFloat() / totalQuestionsAnswered) * 100f else 78.5f

    WatermarkBackground(modifier = modifier, alpha = 0.08f) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "PERFORMANCE ANALYTICS & BOARD READINESS",
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Stat Summary Cards Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                AnalyticsCard(
                    title = "Average Score",
                    value = "${overallPercentage.toInt()}%",
                    subtitle = "Board Passing threshold = 50%",
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.weight(1f)
                )
                AnalyticsCard(
                    title = "Tests Completed",
                    value = "$totalTests",
                    subtitle = "Mock & Practice sessions",
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Course Strengths & Weaknesses
            Text(
                text = "COURSE PERFORMANCE BREAKDOWN",
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(10.dp))

            val courseStats = listOf(
                CourseStat("Anatomy & Physiology (ANA)", 0.85f, "Strong Mastery"),
                CourseStat("PPTP (Pharmaceutics & Tech)", 0.82f, "High Readiness"),
                CourseStat("Basic Dispensing Technique (BDT)", 0.76f, "Proficient"),
                CourseStat("Action & Uses of Medicines (AUM)", 0.71f, "Good Progress"),
                CourseStat("Primary Health Care (PHC)", 0.88f, "Excellent"),
                CourseStat("English & Communication (ENG)", 0.90f, "Superior")
            )

            courseStats.forEach { stat ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp),
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
                                text = stat.name,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
                            Text(
                                text = "${(stat.progress * 100).toInt()}% • ${stat.status}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        LinearProgressIndicator(
                            progress = { stat.progress },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .clip(RoundedCornerShape(4.dp)),
                            color = MaterialTheme.colorScheme.secondary,
                            trackColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

private data class CourseStat(val name: String, val progress: Float, val status: String)

@Composable
private fun AnalyticsCard(
    title: String,
    value: String,
    subtitle: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.12f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = title, fontSize = 12.sp, color = color, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = value, fontSize = 26.sp, fontWeight = FontWeight.ExtraBold, color = color)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = subtitle, fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}
