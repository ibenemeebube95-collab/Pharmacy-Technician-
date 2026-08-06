package com.example.ui.screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R

@Composable
fun LoginScreen(
    isDarkTheme: Boolean,
    onToggleDarkTheme: () -> Unit,
    onLoginSuccess: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableIntStateOf(0) } // 0 = Email, 1 = Phone, 2 = Register
    var emailInput by remember { mutableStateOf("c.okonkwo@pharmacy.edu.ng") }
    var phoneInput by remember { mutableStateOf("+234 803 123 4567") }
    var passwordInput by remember { mutableStateOf("123456") }
    var nameInput by remember { mutableStateOf("Chidimma Okonkwo") }
    var schoolInput by remember { mutableStateOf("School of Health Tech, Yaba") }

    var showForgotPasswordModal by remember { mutableStateOf(false) }
    var resetEmail by remember { mutableStateOf("") }
    var resetSuccessMessage by remember { mutableStateOf<String?>(null) }

    // Pulse scale animation for blue login logo
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 0.96f,
        targetValue = 1.04f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseScale"
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Theme toggle button at top right
        IconButton(
            onClick = onToggleDarkTheme,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = if (isDarkTheme) Icons.Default.LightMode else Icons.Default.DarkMode,
                contentDescription = "Toggle Theme",
                tint = MaterialTheme.colorScheme.primary
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            // Main Blue Mortar & Pestle Logo with pulse scale
            Box(
                modifier = Modifier
                    .size(110.dp)
                    .scale(pulseScale)
                    .clip(CircleShape)
                    .background(Color(0xFF104E8B))
                    .padding(4.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_login_logo),
                    contentDescription = "Pharmacy Logo",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Pharmacy Technician CBT Pro",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center
            )

            Text(
                text = "Nigeria National Board Examination Portal",
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TabRow(
                        selectedTabIndex = selectedTab,
                        containerColor = Color.Transparent,
                        contentColor = MaterialTheme.colorScheme.primary
                    ) {
                        Tab(
                            selected = selectedTab == 0,
                            onClick = { selectedTab = 0 },
                            text = { Text("Email", fontSize = 13.sp, fontWeight = FontWeight.Bold) }
                        )
                        Tab(
                            selected = selectedTab == 1,
                            onClick = { selectedTab = 1 },
                            text = { Text("Phone", fontSize = 13.sp, fontWeight = FontWeight.Bold) }
                        )
                        Tab(
                            selected = selectedTab == 2,
                            onClick = { selectedTab = 2 },
                            text = { Text("Register", fontSize = 13.sp, fontWeight = FontWeight.Bold) }
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    when (selectedTab) {
                        0 -> {
                            // Email Login
                            OutlinedTextField(
                                value = emailInput,
                                onValueChange = { emailInput = it },
                                label = { Text("Email Address") },
                                leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            OutlinedTextField(
                                value = passwordInput,
                                onValueChange = { passwordInput = it },
                                label = { Text("Password") },
                                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                                visualTransformation = PasswordVisualTransformation(),
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true
                            )
                        }
                        1 -> {
                            // Phone Login
                            OutlinedTextField(
                                value = phoneInput,
                                onValueChange = { phoneInput = it },
                                label = { Text("Phone Number (+234...)") },
                                leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null) },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            OutlinedTextField(
                                value = passwordInput,
                                onValueChange = { passwordInput = it },
                                label = { Text("Passcode") },
                                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                                visualTransformation = PasswordVisualTransformation(),
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true
                            )
                        }
                        2 -> {
                            // Register
                            OutlinedTextField(
                                value = nameInput,
                                onValueChange = { nameInput = it },
                                label = { Text("Full Name") },
                                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedTextField(
                                value = emailInput,
                                onValueChange = { emailInput = it },
                                label = { Text("Email Address") },
                                leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedTextField(
                                value = schoolInput,
                                onValueChange = { schoolInput = it },
                                label = { Text("School / Institution") },
                                leadingIcon = { Icon(Icons.Default.School, contentDescription = null) },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedTextField(
                                value = passwordInput,
                                onValueChange = { passwordInput = it },
                                label = { Text("Password") },
                                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                                visualTransformation = PasswordVisualTransformation(),
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = onLoginSuccess,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Text(
                            text = if (selectedTab == 2) "CREATE ACCOUNT" else "LOG IN TO DASHBOARD",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = Color.White
                        )
                    }

                    if (selectedTab != 2) {
                        Spacer(modifier = Modifier.height(8.dp))
                        TextButton(onClick = { showForgotPasswordModal = true }) {
                            Text("Forgot Password?", color = MaterialTheme.colorScheme.primary)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(30.dp))
        }

        // Forgot Password Modal
        if (showForgotPasswordModal) {
            AlertDialog(
                onDismissRequest = {
                    showForgotPasswordModal = false
                    resetSuccessMessage = null
                },
                title = { Text("Reset Password") },
                text = {
                    Column {
                        Text("Enter your registered email address or phone number to receive password reset instructions.")
                        Spacer(modifier = Modifier.height(12.dp))
                        OutlinedTextField(
                            value = resetEmail,
                            onValueChange = { resetEmail = it },
                            label = { Text("Email or Phone") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        if (resetSuccessMessage != null) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = resetSuccessMessage!!,
                                color = MaterialTheme.colorScheme.secondary,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                },
                confirmButton = {
                    Button(onClick = {
                        resetSuccessMessage = "Reset link sent to $resetEmail! Check your SMS/Inbox."
                    }) {
                        Text("Send Reset Link")
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        showForgotPasswordModal = false
                        resetSuccessMessage = null
                    }) {
                        Text("Close")
                    }
                }
            )
        }
    }
}
