package com.example.ui.screens

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
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.WatermarkBackground

@Composable
fun NotificationsScreen(
    modifier: Modifier = Modifier
) {
    val alerts = listOf(
        NotificationItem("Pharmacy Council Registration Update", "Registration portal for 2026 Pharmacy Technician Board Exams is officially open.", "Today, 09:30 AM"),
        NotificationItem("Daily CBT Practice Challenge", "Test your knowledge on Anatomy & Physiology with 10 quick questions today!", "Yesterday, 08:00 AM"),
        NotificationItem("New Past Questions Uploaded", "2025 Board Examination questions for PPTP and BDT are now available for practice.", "3 days ago"),
        NotificationItem("Study Tip: Cold Chain Storage", "Remember: Vaccines and biologicals must be maintained strictly at 2°C to 8°C.", "5 days ago")
    )

    WatermarkBackground(modifier = modifier, alpha = 0.08f) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "BOARD NOTIFICATIONS & ANNOUNCEMENTS",
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(14.dp))

            alerts.forEach { item ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.primaryContainer,
                            modifier = Modifier.padding(end = 12.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Notifications,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier
                                    .padding(8.dp)
                                    .size(20.dp)
                            )
                        }

                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = item.title, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = item.message, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(text = item.time, fontSize = 10.sp, color = MaterialTheme.colorScheme.primary)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

private data class NotificationItem(val title: String, val message: String, val time: String)
