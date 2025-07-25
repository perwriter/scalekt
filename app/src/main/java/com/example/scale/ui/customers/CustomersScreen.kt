package com.example.scale.ui.customers

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.input.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.scale.data.entities.Customer

@Composable
fun CustomersScreen(viewModel: CustomersViewModel = viewModel()) {
    val customers by viewModel.customers.observeAsState(emptyList())

    var showForm by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var editingCustomer by remember { mutableStateOf<Customer?>(null) }

    val brownColor = Color(0xFF8B4513) // SaddleBrown or your preferred brown

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    showForm = true
                    editingCustomer = null
                    name = ""
                    email = ""
                    phone = ""
                    address = ""
                },
                containerColor = brownColor
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Customer", tint = Color.White)
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            item {
                Text("Customers", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(8.dp))
                Divider(modifier = Modifier.padding(vertical = 12.dp))
            }

            items(customers) { customer ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(customer.name, style = MaterialTheme.typography.bodyLarge)
                        Text(customer.email, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text(customer.phone, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text(customer.address, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    Row {
                        IconButton(
                            onClick = {
                                // Load customer data into form for editing
                                name = customer.name
                                email = customer.email
                                phone = customer.phone
                                address = customer.address
                                editingCustomer = customer
                                showForm = true
                            }
                        ) {
                            Icon(Icons.Default.Edit, contentDescription = "Edit", tint = brownColor)
                        }
                        IconButton(
                            onClick = { viewModel.delete(customer) }
                        ) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete", tint = brownColor)
                        }
                    }
                }
                Divider()
            }
        }
    }

    if (showForm) {
        AlertDialog(
            onDismissRequest = { showForm = false },
            title = {
                Text(if (editingCustomer == null) "Add Customer" else "Edit Customer")
            },
            text = {
                Column {
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Name") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = phone,
                        onValueChange = { phone = it },
                        label = { Text("Phone") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = address,
                        onValueChange = { address = it },
                        label = { Text("Address") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (editingCustomer == null) {
                            // Insert new customer
                            viewModel.insert(
                                Customer(
                                    name = name,
                                    email = email,
                                    phone = phone,
                                    address = address
                                )
                            )
                        } else {
                            // Update existing customer
                            val updatedCustomer = editingCustomer!!.copy(
                                name = name,
                                email = email,
                                phone = phone,
                                address = address
                            )
                            viewModel.update(updatedCustomer)
                        }
                        showForm = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = brownColor)
                ) {
                    Text(if (editingCustomer == null) "Save" else "Update")
                }
            },
            dismissButton = {
                TextButton(onClick = { showForm = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}
