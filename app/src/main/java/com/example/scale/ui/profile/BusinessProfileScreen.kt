package com.example.scale.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.scale.R
import com.example.scale.data.entities.BusinessProfile
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BusinessProfileScreen(onBack: () -> Unit) {
    val viewModel: BusinessProfileViewModel = viewModel()

    // Brown color
    val brownColor = Color(0xFF8B4513)

    var businessName by remember { mutableStateOf("") }
    var physicalAddress by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var emailOrWebsite by remember { mutableStateOf("") }
    var registrationNumber by remember { mutableStateOf("") }
    var kraPin by remember { mutableStateOf("") }
    var receiptNumber by remember { mutableStateOf("") }
    val dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
    var cashierNameOrId by remember { mutableStateOf("") }
    var paymentMethod by remember { mutableStateOf("") }
    var thankYouMessage by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Business Profile") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top
        ) {
            item {
                Text("Business Logo (optional but professional)")
                Spacer(modifier = Modifier.height(8.dp))
                Image(
                    painter = painterResource(R.drawable.scale),
                    contentDescription = "Business Logo",
                    modifier = Modifier
                        .height(100.dp)
                        .fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                OutlinedTextField(
                    value = businessName,
                    onValueChange = { businessName = it },
                    label = { Text("Business Name (e.g., Scale)") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                OutlinedTextField(
                    value = physicalAddress,
                    onValueChange = { physicalAddress = it },
                    label = { Text("Physical Address") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                OutlinedTextField(
                    value = phoneNumber,
                    onValueChange = { phoneNumber = it },
                    label = { Text("Phone Number") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                OutlinedTextField(
                    value = emailOrWebsite,
                    onValueChange = { emailOrWebsite = it },
                    label = { Text("Email Address or Website") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                OutlinedTextField(
                    value = registrationNumber,
                    onValueChange = { registrationNumber = it },
                    label = { Text("Business Registration / License Number (if applicable)") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                OutlinedTextField(
                    value = kraPin,
                    onValueChange = { kraPin = it },
                    label = { Text("Tax Identification Number (KRA PIN)") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                OutlinedTextField(
                    value = receiptNumber,
                    onValueChange = { receiptNumber = it },
                    label = { Text("Receipt Number") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                Text("Date and Time: $dateTime")
                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                OutlinedTextField(
                    value = cashierNameOrId,
                    onValueChange = { cashierNameOrId = it },
                    label = { Text("Cashier Name / ID") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                OutlinedTextField(
                    value = paymentMethod,
                    onValueChange = { paymentMethod = it },
                    label = { Text("Payment Method (e.g., Cash, M-Pesa, Card)") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                OutlinedTextField(
                    value = thankYouMessage,
                    onValueChange = { thankYouMessage = it },
                    label = { Text("Thank you message or slogan") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                Button(
                    onClick = {
                        val profile = BusinessProfile(
                            businessName = businessName,
                            logoPath = "android.resource://com.example.scale/${R.drawable.scale}",
                            physicalAddress = physicalAddress,
                            phoneNumber = phoneNumber,
                            emailOrWebsite = emailOrWebsite,
                            registrationNumber = registrationNumber,
                            kraPin = kraPin,
                            receiptNumber = receiptNumber,
                            dateTime = dateTime,
                            cashierNameOrId = cashierNameOrId,
                            paymentMethod = paymentMethod,
                            thankYouMessage = thankYouMessage
                        )
                        viewModel.saveProfile(profile)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = brownColor)
                ) {
                    Text("Save")
                }
            }
        }
    }
}
