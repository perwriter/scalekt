package com.example.scale.ui.sales

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.scale.data.entities.Customer
import com.example.scale.data.entities.Sale
import com.example.scale.data.entities.SaleItem
import com.example.scale.ui.customers.CustomersViewModel
import com.example.scale.ui.items.ItemsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SalesScreen(
    viewModel: SalesViewModel = viewModel(),
    customersViewModel: CustomersViewModel = viewModel(),
    itemsViewModel: ItemsViewModel = viewModel()
) {
    val customers by customersViewModel.customers.observeAsState(emptyList())
    val items by itemsViewModel.items.observeAsState(emptyList())
    val sales by viewModel.sales.observeAsState(emptyList())

    val defaultCustomer = customers.find { it.name == "Walk-in Customer" }
        ?: Customer(id = 0, name = "Walk-in Customer", email = "", phone = "", address = "")

    var selectedCustomer by remember { mutableStateOf<Customer?>(null) }
    var customerSearchQuery by remember { mutableStateOf("") }

    var showRecords by remember { mutableStateOf(false) }
    var showCart by remember { mutableStateOf(false) }

    var selectedItems by remember { mutableStateOf(listOf<SaleItem>()) }
    var editingSale by remember { mutableStateOf<Sale?>(null) }

    val brownColor = Color(0xFF8B4513)

    val filteredCustomers = customers.filter {
        it.name.contains(customerSearchQuery, ignoreCase = true) ||
                it.email.contains(customerSearchQuery, ignoreCase = true) ||
                it.phone.contains(customerSearchQuery, ignoreCase = true)
    }

    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp
    val gridColumns = when {
        screenWidthDp < 600 -> 2 // Mobile
        screenWidthDp < 840 -> 3 // Small tablets
        else -> 4 // Large tablets
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Sales") },
                actions = {
                    Box {
                        IconButton(onClick = { showCart = true }) {
                            Icon(Icons.Default.ShoppingCart, contentDescription = "Cart", tint = brownColor)
                        }
                        if (selectedItems.isNotEmpty()) {
                            Badge(
                                containerColor = Color.Red,
                                modifier = Modifier.align(Alignment.TopEnd)
                            ) {
                                Text("${selectedItems.size}", color = Color.White)
                            }
                        }
                    }
                    IconButton(onClick = { showRecords = true }) {
                        Icon(Icons.Default.List, contentDescription = "Sales Records", tint = brownColor)
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            item {
                OutlinedTextField(
                    value = customerSearchQuery,
                    onValueChange = { customerSearchQuery = it },
                    label = { Text("Search Customer by name, phone, or email") },
                    modifier = Modifier.fillMaxWidth()
                )

                if (customerSearchQuery.isNotBlank() && selectedCustomer == null) {
                    filteredCustomers.forEach { customer ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    selectedCustomer = customer
                                    customerSearchQuery = ""
                                }
                                .padding(8.dp)
                        ) {
                            Column {
                                Text(customer.name)
                                Text(customer.phone, style = MaterialTheme.typography.bodySmall)
                                Text(customer.email, style = MaterialTheme.typography.bodySmall)
                            }
                        }
                    }
                }

                selectedCustomer?.let {
                    Text(
                        "Selected Customer: ${it.name}",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }

                Divider(modifier = Modifier.padding(vertical = 8.dp))
                Text("Select Items", style = MaterialTheme.typography.titleMedium)
            }

            item {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(gridColumns),
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 600.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    content = {
                        items(items) { item ->
                            Card(
                                modifier = Modifier
                                    .padding(4.dp)
                                    .fillMaxWidth()
                                    .clickable {
                                        val existing = selectedItems.find { it.itemId == item.id }
                                        selectedItems = if (existing != null) {
                                            selectedItems.map {
                                                if (it.itemId == item.id) it.copy(
                                                    quantity = it.quantity + 1,
                                                    price = (it.quantity + 1) * item.price
                                                ) else it
                                            }
                                        } else {
                                            selectedItems + SaleItem(
                                                saleId = editingSale?.id ?: 0,
                                                itemId = item.id,
                                                quantity = 1,
                                                price = item.price
                                            )
                                        }
                                    }
                            ) {
                                Column(modifier = Modifier.padding(8.dp)) {
                                    Text(item.name)
                                    Text("Ksh ${item.price}")
                                }
                            }
                        }
                    }
                )
            }
        }
    }

    // Cart modal with scrollable content
    if (showCart) {
        AlertDialog(
            onDismissRequest = { showCart = false },
            title = { Text("Cart") },
            text = {
                if (selectedItems.isEmpty()) {
                    Text("No items in cart.")
                } else {
                    LazyColumn(
                        modifier = Modifier.heightIn(max = 400.dp)
                    ) {
                        items(selectedItems) { saleItem ->
                            val itemData = items.find { it.id == saleItem.itemId }
                            val itemName = itemData?.name ?: "Unknown"
                            val unitPrice = itemData?.price ?: 0.0
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(itemName)
                                    Text("Unit: Ksh $unitPrice | Total: Ksh ${saleItem.price}")
                                }
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    IconButton(onClick = {
                                        val newQty = saleItem.quantity - 1
                                        selectedItems = if (newQty <= 0) {
                                            selectedItems.filter { it.itemId != saleItem.itemId }
                                        } else {
                                            selectedItems.map {
                                                if (it.itemId == saleItem.itemId)
                                                    it.copy(
                                                        quantity = newQty,
                                                        price = newQty * unitPrice
                                                    )
                                                else it
                                            }
                                        }
                                    }) {
                                        Text("-")
                                    }
                                    Text("${saleItem.quantity}")
                                    IconButton(onClick = {
                                        val newQty = saleItem.quantity + 1
                                        selectedItems = selectedItems.map {
                                            if (it.itemId == saleItem.itemId)
                                                it.copy(
                                                    quantity = newQty,
                                                    price = newQty * unitPrice
                                                )
                                            else it
                                        }
                                    }) {
                                        Text("+")
                                    }
                                }
                            }
                        }
                    }
                }
            },
            confirmButton = {
                Column {
                    val total = selectedItems.sumOf { it.price }
                    Button(
                        onClick = {
                            val customerToUse = selectedCustomer ?: defaultCustomer
                            if (selectedItems.isNotEmpty()) {
                                if (editingSale == null) {
                                    viewModel.insertSaleWithItems(
                                        Sale(customerId = customerToUse.id, totalPrice = total),
                                        selectedItems
                                    )
                                } else {
                                    viewModel.updateSaleWithItems(
                                        editingSale!!.copy(customerId = customerToUse.id, totalPrice = total),
                                        selectedItems
                                    )
                                }
                                selectedCustomer = null
                                selectedItems = listOf()
                                editingSale = null
                                showCart = false
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = brownColor)
                    ) {
                        Text("Save Sale (Total: Ksh $total)")
                    }
                    TextButton(onClick = { showCart = false }, modifier = Modifier.fillMaxWidth()) {
                        Text("Close")
                    }
                }
            }
        )
    }

    // Records modal with scrollable content
    if (showRecords) {
        AlertDialog(
            onDismissRequest = { showRecords = false },
            title = { Text("Sales Records") },
            text = {
                LazyColumn(
                    modifier = Modifier.heightIn(max = 400.dp)
                ) {
                    items(sales) { sale ->
                        Column(modifier = Modifier.padding(4.dp)) {
                            val customerName = customers.find { it.id == sale.customerId }?.name ?: defaultCustomer.name
                            Text("Sale #${sale.id} | $customerName | Ksh ${sale.totalPrice}")

                            val saleItems by viewModel.getSaleItems(sale.id).observeAsState(emptyList())
                            saleItems.forEach { si ->
                                val itemName = items.find { it.id == si.itemId }?.name ?: "Unknown"
                                Text("- $itemName x${si.quantity} (Ksh ${si.price})")
                            }

                            Row {
                                Button(
                                    onClick = {
                                        editingSale = sale
                                        selectedCustomer = customers.find { it.id == sale.customerId }
                                        viewModel.getSaleItems(sale.id).observeForever {
                                            selectedItems = it
                                        }
                                        showRecords = false
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = brownColor),
                                    modifier = Modifier.padding(end = 8.dp)
                                ) {
                                    Text("Edit")
                                }

                                Button(
                                    onClick = { viewModel.deleteSale(sale) },
                                    colors = ButtonDefaults.buttonColors(containerColor = brownColor)
                                ) {
                                    Text("Delete")
                                }
                            }
                            Divider()
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showRecords = false }) {
                    Text("Close")
                }
            }
        )
    }
}
