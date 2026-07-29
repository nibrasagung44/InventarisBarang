package com.example.myinventarisbarang.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import kotlinx.coroutines.launch
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myinventarisbarang.model.Barang

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DaftarBarangScreen(
    onItemClick: (Int) -> Unit,
    onFabClick: () -> Unit,
    viewModel: InventoryViewModel
) {
    val inventoryUiState by viewModel.inventoryUiState.collectAsState()
    
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            Column {
                TopAppBar(
                    title = { Text("Inventaris Barang", fontWeight = FontWeight.Bold) },
                    actions = {
                        IconButton(onClick = {
                            scope.launch {
                                snackbarHostState.showSnackbar("Fitur Cari belum tersedia")
                            }
                        }) {
                            Icon(Icons.Default.Search, contentDescription = "Cari")
                        }
                    }
                )
                // Filter Categories - Placeholder
                ScrollableTabRow(
                    selectedTabIndex = 0,
                    edgePadding = 16.dp,
                    containerColor = MaterialTheme.colorScheme.surface,
                    divider = {}
                ) {
                    listOf("Semua", "Elektronik", "Aksesoris", "Pakaian", "Makanan").forEachIndexed { index, title ->
                        Tab(
                            selected = index == 0,
                            onClick = { /* Filter Logic */ },
                            text = { Text(title) }
                        )
                    }
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onFabClick,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Tambah Barang")
            }
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Inventory, contentDescription = null) },
                    label = { Text("Barang") },
                    selected = true,
                    onClick = { }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.History, contentDescription = null) },
                    label = { Text("Riwayat") },
                    selected = false,
                    onClick = {
                        scope.launch {
                            snackbarHostState.showSnackbar("Halaman Riwayat belum tersedia")
                        }
                    }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Settings, contentDescription = null) },
                    label = { Text("Pengaturan") },
                    selected = false,
                    onClick = {
                        scope.launch {
                            snackbarHostState.showSnackbar("Halaman Pengaturan belum tersedia")
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        if (inventoryUiState.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text("Belum ada barang. Klik + untuk menambah.")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(inventoryUiState) { barang ->
                    BarangCard(barang = barang, onClick = { onItemClick(barang.id) })
                }
            }
        }
    }
}

@Composable
fun BarangCard(barang: Barang, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Placeholder for Image Icon
            Surface(
                modifier = Modifier.size(60.dp),
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = MaterialTheme.shapes.medium
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(Icons.Default.ShoppingCart, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                }
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(text = barang.nama, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(text = barang.kategori, color = Color.Gray, fontSize = 14.sp)
                Text(
                    text = "Rp ${barang.harga}",
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
            
            Badge(
                containerColor = if (barang.stok > 5) Color(0xFF4CAF50) else Color(0xFFF44336),
                modifier = Modifier.align(Alignment.Top)
            ) {
                Text(text = "Stok: ${barang.stok}", color = Color.White, modifier = Modifier.padding(4.dp))
            }
        }
    }
}
