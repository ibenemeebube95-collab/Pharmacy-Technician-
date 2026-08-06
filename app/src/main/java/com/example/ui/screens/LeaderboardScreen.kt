package com.example.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.WatermarkBackground

@Composable
fun LeaderboardScreen(
    modifier: Modifier = Modifier
) {
    val candidates = listOf(
        LeaderboardEntry(1, "Amina Bello", "Kano State School of Health Tech", "98.4%", "Gold"),
        LeaderboardEntry(2, "Chidimma Okonkwo", "School of Health Tech, Yaba", "96.2%", "Silver"),
        LeaderboardEntry(3, "Emeka Eze", "Enugu State College of Health", "94.8%", "Bronze"),
        LeaderboardEntry(4, "Fatima Ibrahim", "Kaduna Health Tech Institute", "93.5%", "Top 5"),
        LeaderboardEntry(5, "Oluwaseun Adebayo", "Lagos State Health Tech", "92.1%", "Top 5"),
        LeaderboardEntry(6, "Usman Garba", "Sokoto School of Health Science", "90.6%", "Top 10"),
        LeaderboardEntry(7, "Blessing Nwachukwu", "Rivers State College of Health", "89.2%", "Top 10")
    )

    WatermarkBackground(modifier = modifier, alpha = 0.08f) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "NATIONAL CANDIDATE LEADERBOARD 2026",
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(14.dp))

            candidates.forEach { entry ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = when (entry.rank) {
                                1 -> Color(0xFFEAB308)
                                2 -> Color(0xFF94A3B8)
                                3 -> Color(0xFFD97706)
                                else -> MaterialTheme.colorScheme.primaryContainer
                            },
                            modifier = Modifier.padding(end = 14.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                if (entry.rank <= 3) {
                                    Icon(
                                        imageVector = Icons.Default.EmojiEvents,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.width(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(2.dp))
                                }
                                Text(
                                    text = "#${entry.rank}",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    color = if (entry.rank <= 3) Color.White else MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            }
                        }

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = entry.name,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                            Text(
                                text = entry.school,
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Text(
                            text = entry.score,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 15.sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

private data class LeaderboardEntry(
    val rank: Int,
    val name: String,
    val school: String,
    val score: String,
    val tier: String
)
