package com.example.scale.ui.items

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
import com.example.scale.data.entities.Item

@Composable
fun ItemsScreen(viewModel: ItemsViewModel = viewModel()) {
    val items by viewModel.items.observeAsState(emptyList())

    var name by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var stock by remember { mutableStateOf("") }
    var sku by remember { mutableStateOf("") }
    var status by remember { mutableStateOf("In Stock") }

    var editingItem by remember { mutableStateOf<Item?>(null) }
    var showForm by remember { mutableStateOf(false) }

    val brownColor = Color(0xFF8B4513)

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    showForm = true
                    editingItem = null
                    name = ""
                    category = ""
                    location = ""
                    price = ""
                    stock = ""
                    sku = ""
                    status = "In Stock"
                },
                containerColor = brownColor
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Item", tint = Color.White)
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
                Text("Items", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(8.dp))
                Divider(modifier = Modifier.padding(vertical = 8.dp))
            }

            items(items) { item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("${item.name} (${item.sku})")
                        Text("Category: ${item.category}, Location: ${item.location}")
                        Text("Price: Ksh ${item.price}, Stock: ${item.stock}")
                        Text("Status: ${item.status}")
                    }
                    Row {
                        IconButton(onClick = {
                            // Load item into form for editing and show form
                            name = item.name
                            category = item.category
                            location = item.location
                            price = item.price.toString()
                            stock = item.stock.toString()
                            sku = item.sku
                            status = item.status
                            editingItem = item
                            showForm = true
                        }) {
                            Icon(Icons.Default.Edit, contentDescription = "Edit", tint = brownColor)
                        }
                        IconButton(onClick = { viewModel.delete(item) }) {
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
                Text(if (editingItem == null) "Add Item" else "Edit Item")
            },
            text = {
                Column {
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Product Name") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = category,
                        onValueChange = { category = it },
                        label = { Text("Category") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = location,
                        onValueChange = { location = it },
                        label = { Text("Location") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = price,
                        onValueChange = { price = it },
                        label = { Text("Price") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = stock,
                        onValueChange = { stock = it },
                        label = { Text("Stock") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = sku,
                        onValueChange = { sku = it },
                        label = { Text("SKU") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Dropdown for status
                    var expanded by remember { mutableStateOf(false) }
                    Box(modifier = Modifier.fillMaxWidth()) {
                        OutlinedButton(
                            onClick = { expanded = true },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = brownColor)
                        ) {
                            Text(status)
                        }
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("In Stock") },
                                onClick = {
                                    status = "In Stock"
                                    expanded = false
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Out of Stock") },
                                onClick = {
                                    status = "Out of Stock"
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val priceValue = price.toDoubleOrNull() ?: 0.0
                        val stockValue = stock.toIntOrNull() ?: 0

                        if (editingItem == null) {
                            // Insert new item
                            viewModel.insert(
                                Item(
                                    name = name,
                                    category = category,
                                    location = location,
                                    price = priceValue,
                                    stock = stockValue,
                                    status = status,
                                    sku = sku
                                )
                            )
                        } else {
                            // Update existing item
                            val updatedItem = editingItem!!.copy(
                                name = name,
                                category = category,
                                location = location,
                                price = priceValue,
                                stock = stockValue,
                                status = status,
                                sku = sku
                            )
                            viewModel.update(updatedItem)
                        }

                        showForm = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = brownColor)
                ) {
                    Text(if (editingItem == null) "Save" else "Update")
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
