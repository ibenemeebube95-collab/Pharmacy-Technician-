package com.example.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.UploadFile
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.UserAccount
import com.example.ui.components.WatermarkBackground
import com.example.ui.viewmodel.MainViewModel

@Composable
fun PremiumScreen(
    viewModel: MainViewModel,
    userAccount: UserAccount,
    modifier: Modifier = Modifier
) {
    var accountNameInput by remember { mutableStateOf(userAccount.fullName) }
    var refNumberInput by remember { mutableStateOf("STB/2026/89123") }
    var dateInput by remember { mutableStateOf("2026-08-06") }
    var isSuccessMessage by remember { mutableStateOf(false) }

    WatermarkBackground(modifier = modifier, alpha = 0.08f) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "PREMIUM LICENSE ACTIVATION (STERLING BANK)",
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(14.dp))

            if (userAccount.isPremium) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF064E3B))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.padding(bottom = 12.dp)
                        )
                        Text(
                            text = "FULL PRO VERSION ACTIVATED",
                            color = Color.White,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 18.sp
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "You have unlocked all 2018–2025 past questions, unlimited mock examinations, AI Tutor queries, and full offline database caching.",
                            color = Color.White.copy(alpha = 0.9f),
                            fontSize = 13.sp
                        )
                    }
                }
            } else {
                // Bank Details Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.AccountBalance, contentDescription = null, tint = Color.White)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "STERLING BANK DIRECT TRANSFER",
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                fontSize = 15.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        BankDetailRow("Bank Name:", "Sterling Bank Plc")
                        BankDetailRow("Account Number:", "0098765432")
                        BankDetailRow("Account Name:", "Pharmacy Technician CBT Pro Nigeria")
                        BankDetailRow("Activation Fee:", "₦3,500 (One-Time Lifetime Access)")

                        Spacer(modifier = Modifier.height(12.dp))

                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = Color.White.copy(alpha = 0.15f)
                        ) {
                            Text(
                                text = "Note: Architecture structured for seamless future Paystack & Flutterwave integration.",
                                color = Color.White.copy(alpha = 0.85f),
                                fontSize = 10.sp,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Payment Proof Form Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text(
                            text = "SUBMIT PAYMENT RECEIPT PROOF",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        OutlinedTextField(
                            value = accountNameInput,
                            onValueChange = { accountNameInput = it },
                            label = { Text("Sender Bank Account Name") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        OutlinedTextField(
                            value = refNumberInput,
                            onValueChange = { refNumberInput = it },
                            label = { Text("Transfer Reference / Transaction ID") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        OutlinedTextField(
                            value = dateInput,
                            onValueChange = { dateInput = it },
                            label = { Text("Date of Transfer") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = {
                                viewModel.submitSterlingTransfer(
                                    accountName = accountNameInput,
                                    refNumber = refNumberInput,
                                    transferDate = dateInput
                                )
                                isSuccessMessage = true
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                        ) {
                            Icon(Icons.Default.UploadFile, contentDescription = null, tint = Color.White)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "VERIFY PAYMENT & ACTIVATE PRO",
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }

                        if (isSuccessMessage) {
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = "Payment verified! Your account has been upgraded to PRO.",
                                color = Color(0xFF16A34A),
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Composable
private fun BankDetailRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, color = Color.White.copy(alpha = 0.8f), fontSize = 12.sp)
        Text(text = value, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
    }
}
