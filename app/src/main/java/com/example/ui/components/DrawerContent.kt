package com.example.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.ContactSupport
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Leaderboard
import androidx.compose.material.icons.filled.Medication
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.UserAccount
import com.example.ui.viewmodel.ScreenRoute

@Composable
fun DrawerContent(
    currentRoute: ScreenRoute,
    userAccount: UserAccount,
    onSelectRoute: (ScreenRoute) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxHeight()
            .width(300.dp),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 2.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .verticalScroll(rememberScrollState())
        ) {
            // Header
            BoxHeader(userAccount = userAccount)

            Spacer(modifier = Modifier.height(8.dp))

            // Navigation Items
            DrawerGroupTitle("CORE CBT MODULES")

            DrawerItem(
                label = "Home",
                icon = Icons.Default.Home,
                selected = currentRoute == ScreenRoute.HOME,
                onClick = { onSelectRoute(ScreenRoute.HOME) }
            )

            DrawerItem(
                label = "Practice Questions",
                icon = Icons.Default.Medication,
                selected = currentRoute == ScreenRoute.PRACTICE,
                onClick = { onSelectRoute(ScreenRoute.PRACTICE) }
            )

            DrawerItem(
                label = "Mock Examination",
                icon = Icons.Default.Quiz,
                selected = currentRoute == ScreenRoute.MOCK_EXAM,
                onClick = { onSelectRoute(ScreenRoute.MOCK_EXAM) }
            )

            DrawerItem(
                label = "Past Questions",
                icon = Icons.AutoMirrored.Filled.MenuBook,
                selected = currentRoute == ScreenRoute.PAST_QUESTIONS,
                onClick = { onSelectRoute(ScreenRoute.PAST_QUESTIONS) }
            )

            DrawerItem(
                label = "Results",
                icon = Icons.Default.Star,
                selected = currentRoute == ScreenRoute.RESULTS,
                onClick = { onSelectRoute(ScreenRoute.RESULTS) }
            )

            DrawerItem(
                label = "Performance Analytics",
                icon = Icons.Default.Analytics,
                selected = currentRoute == ScreenRoute.ANALYTICS,
                onClick = { onSelectRoute(ScreenRoute.ANALYTICS) }
            )

            DrawerItem(
                label = "Leaderboard",
                icon = Icons.Default.Leaderboard,
                selected = currentRoute == ScreenRoute.LEADERBOARD,
                onClick = { onSelectRoute(ScreenRoute.LEADERBOARD) }
            )

            Divider(modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp))

            DrawerGroupTitle("STUDY TOOLS & AI")

            DrawerItem(
                label = "AI Pharmacy Tutor",
                icon = Icons.Default.AutoAwesome,
                selected = currentRoute == ScreenRoute.AI_TUTOR,
                badgeText = "AI",
                onClick = { onSelectRoute(ScreenRoute.AI_TUTOR) }
            )

            DrawerItem(
                label = "Premium Activation",
                icon = Icons.Default.WorkspacePremium,
                selected = currentRoute == ScreenRoute.PREMIUM,
                badgeText = if (userAccount.isPremium) "ACTIVE" else "UPGRADE",
                onClick = { onSelectRoute(ScreenRoute.PREMIUM) }
            )

            DrawerItem(
                label = "Downloads",
                icon = Icons.Default.Download,
                selected = currentRoute == ScreenRoute.DOWNLOADS,
                onClick = { onSelectRoute(ScreenRoute.DOWNLOADS) }
            )

            DrawerItem(
                label = "Notifications",
                icon = Icons.Default.Notifications,
                selected = currentRoute == ScreenRoute.NOTIFICATIONS,
                onClick = { onSelectRoute(ScreenRoute.NOTIFICATIONS) }
            )

            Divider(modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp))

            DrawerGroupTitle("ACCOUNT & SUPPORT")

            DrawerItem(
                label = "Profile",
                icon = Icons.Default.Person,
                selected = currentRoute == ScreenRoute.PROFILE,
                onClick = { onSelectRoute(ScreenRoute.PROFILE) }
            )

            DrawerItem(
                label = "Settings",
                icon = Icons.Default.Settings,
                selected = currentRoute == ScreenRoute.SETTINGS,
                onClick = { onSelectRoute(ScreenRoute.SETTINGS) }
            )

            DrawerItem(
                label = "About App",
                icon = Icons.AutoMirrored.Filled.Help,
                selected = currentRoute == ScreenRoute.ABOUT,
                onClick = { onSelectRoute(ScreenRoute.ABOUT) }
            )

            DrawerItem(
                label = "Contact Support",
                icon = Icons.Default.ContactSupport,
                selected = currentRoute == ScreenRoute.CONTACT,
                onClick = { onSelectRoute(ScreenRoute.CONTACT) }
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun BoxHeader(userAccount: UserAccount) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .padding(20.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_dashboard_watermark),
                contentDescription = "Header Logo",
                modifier = Modifier
                    .size(52.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .padding(2.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = "PHARMACY CBT PRO",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(
                    text = "Nigeria Edition 2026",
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 12.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = userAccount.fullName,
            color = Color.White,
            fontWeight = FontWeight.SemiBold,
            fontSize = 15.sp
        )
        Text(
            text = "${userAccount.institution} • ${userAccount.indexNumber}",
            color = Color.White.copy(alpha = 0.85f),
            fontSize = 11.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Surface(
            shape = RoundedCornerShape(12.dp),
            color = if (userAccount.isPremium) Color(0xFF10B981) else Color(0xFFF59E0B)
        ) {
            Text(
                text = if (userAccount.isPremium) "PRO SUBSCRIBER" else "FREE TRIAL USER",
                color = Color.White,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
            )
        }
    }
}

@Composable
private fun DrawerGroupTitle(title: String) {
    Text(
        text = title,
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
    )
}

@Composable
private fun DrawerItem(
    label: String,
    icon: ImageVector,
    selected: Boolean,
    badgeText: String? = null,
    onClick: () -> Unit
) {
    NavigationDrawerItem(
        label = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = label,
                    fontSize = 14.sp,
                    fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium
                )

                if (badgeText != null) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.primaryContainer
                    ) {
                        Text(
                            text = badgeText,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
            }
        },
        icon = {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        selected = selected,
        onClick = onClick,
        colors = NavigationDrawerItemDefaults.colors(
            selectedContainerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
        ),
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
    )
}
