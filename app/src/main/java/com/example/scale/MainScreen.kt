@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.scale

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.scale.ui.customers.CustomersScreen
import com.example.scale.ui.items.ItemsScreen
import com.example.scale.ui.sales.SalesScreen
import com.example.scale.ui.profile.BusinessProfileScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    var selectedItem by remember { mutableStateOf(0) }
    var showProfileScreen by remember { mutableStateOf(false) }
    val items = listOf("Home", "Sales", "Customers", "Items")
    var menuExpanded by remember { mutableStateOf(false) }

    val brownColor = Color(0xFF8B4513) // brown
    val highlightedColor = Color(0xFFFFA500) // Orange

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Scale", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = brownColor
                ),
                actions = {
                    IconButton(onClick = { menuExpanded = true }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "Menu", tint = Color.White)
                    }
                    DropdownMenu(
                        expanded = menuExpanded,
                        onDismissRequest = { menuExpanded = false },
                        modifier = Modifier.width(200.dp)
                    ) {
                        MenuItem(icon = Icons.Default.Person, text = "Profile") {
                            menuExpanded = false
                            showProfileScreen = true
                        }

                    }
                }
            )
        },
        bottomBar = {
            if (!showProfileScreen) { // hide bottom bar when Profile screen is shown
                NavigationBar(
                    containerColor = brownColor
                ) {
                    items.forEachIndexed { index, item ->
                        NavigationBarItem(
                            icon = {
                                when (item) {
                                    "Home" -> Icon(Icons.Default.Home, contentDescription = item)
                                    "Sales" -> Icon(Icons.Default.ShoppingCart, contentDescription = item)
                                    "Customers" -> Icon(Icons.Default.Person, contentDescription = item)
                                    "Items" -> Icon(Icons.Default.List, contentDescription = item)
                                }
                            },
                            label = { Text(item, color = if (selectedItem == index) highlightedColor else Color.White) },
                            selected = selectedItem == index,
                            onClick = { selectedItem = index },
                            alwaysShowLabel = true,
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = highlightedColor,
                                selectedTextColor = highlightedColor,
                                unselectedIconColor = Color.White,
                                unselectedTextColor = Color.White,
                                indicatorColor = Color.Transparent
                            )
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            if (showProfileScreen) {
                BusinessProfileScreen(
                    onBack = { showProfileScreen = false }
                )
            } else {
                when (selectedItem) {
                    0 -> HomeScreen()
                    1 -> SalesScreen()
                    2 -> CustomersScreen()
                    3 -> ItemsScreen()
                }
            }
        }
    }
}

@Composable
fun MenuItem(icon: androidx.compose.ui.graphics.vector.ImageVector, text: String, onClick: () -> Unit) {
    DropdownMenuItem(
        text = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(icon, contentDescription = text, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(12.dp))
                Text(text)
            }
        },
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    )
}


