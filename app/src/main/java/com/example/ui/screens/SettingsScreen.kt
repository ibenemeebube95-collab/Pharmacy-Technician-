package com.example.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.WatermarkBackground

@Composable
fun SettingsScreen(
    isDarkTheme: Boolean,
    onToggleDarkTheme: () -> Unit,
    modifier: Modifier = Modifier
) {
    var hapticFeedback by remember { mutableStateOf(true) }
    var showExplanationsImmediately by remember { mutableStateOf(true) }

    WatermarkBackground(modifier = modifier, alpha = 0.08f) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "APPLICATION SETTINGS & PREFERENCES",
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(14.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    SettingToggleRow(
                        title = "Dark Theme",
                        subtitle = "Enable dark mode for comfortable night studying",
                        checked = isDarkTheme,
                        onCheckedChange = { onToggleDarkTheme() }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    SettingToggleRow(
                        title = "Haptic Vibration Feedback",
                        subtitle = "Vibrate on selecting answers in CBT mode",
                        checked = hapticFeedback,
                        onCheckedChange = { hapticFeedback = it }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    SettingToggleRow(
                        title = "Instant Practice Explanations",
                        subtitle = "Automatically reveal detailed answer explanations during practice mode",
                        checked = showExplanationsImmediately,
                        onCheckedChange = { showExplanationsImmediately = it }
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Composable
private fun SettingToggleRow(
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Text(text = subtitle, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }

        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(checkedThumbColor = MaterialTheme.colorScheme.primary)
        )
    }
}
