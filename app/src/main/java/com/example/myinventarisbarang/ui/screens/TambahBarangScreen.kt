package com.example.myinventarisbarang.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TambahBarangScreen(
    onBackClick: () -> Unit,
    onSaveClick: (BarangUiState) -> Unit
) {
    var uiState by remember { mutableStateOf(BarangUiState()) }
    
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    
    var expanded by remember { mutableStateOf(false) }
    val categories = listOf("Elektronik", "Aksesoris", "Pakaian", "Makanan", "Lainnya")

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Tambah Barang") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali")
                    }
                },
                actions = {
                    IconButton(onClick = { onSaveClick(uiState) }) {
                        Icon(Icons.Default.Check, contentDescription = "Simpan")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Upload Image Section
            OutlinedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                onClick = {
                    scope.launch {
                        snackbarHostState.showSnackbar("Fitur Upload Gambar belum tersedia")
                    }
                }
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(Icons.Default.CloudUpload, contentDescription = null, modifier = Modifier.size(40.dp))
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Upload Gambar Barang")
                }
            }

            OutlinedTextField(
                value = uiState.nama,
                onValueChange = { uiState = uiState.copy(nama = it) },
                label = { Text("Nama Barang") },
                modifier = Modifier.fillMaxWidth()
            )

            // Category Dropdown
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = uiState.kategori,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Kategori") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier.menuAnchor().fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    categories.forEach { selectionOption ->
                        DropdownMenuItem(
                            text = { Text(selectionOption) },
                            onClick = {
                                uiState = uiState.copy(kategori = selectionOption)
                                expanded = false
                            }
                        )
                    }
                }
            }

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                OutlinedTextField(
                    value = uiState.harga,
                    onValueChange = { uiState = uiState.copy(harga = it) },
                    label = { Text("Harga") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                OutlinedTextField(
                    value = uiState.stok,
                    onValueChange = { uiState = uiState.copy(stok = it) },
                    label = { Text("Stok") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                OutlinedTextField(
                    value = uiState.sku,
                    onValueChange = { uiState = uiState.copy(sku = it) },
                    label = { Text("SKU") },
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = uiState.berat,
                    onValueChange = { uiState = uiState.copy(berat = it) },
                    label = { Text("Berat") },
                    modifier = Modifier.weight(1f)
                )
            }

            OutlinedTextField(
                value = uiState.deskripsi,
                onValueChange = { uiState = uiState.copy(deskripsi = it) },
                label = { Text("Deskripsi") },
                modifier = Modifier.fillMaxWidth().height(120.dp),
                minLines = 3
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { onSaveClick(uiState) },
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(16.dp)
            ) {
                Text("Simpan barang", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}
