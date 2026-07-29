package com.example.myinventarisbarang.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.myinventarisbarang.model.Barang
import com.example.myinventarisbarang.model.BarangDao
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class InventoryViewModel(private val barangDao: BarangDao) : ViewModel() {

    val inventoryUiState: StateFlow<List<Barang>> =
        barangDao.getAllBarang().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )

    fun getBarang(id: Int) = barangDao.getBarang(id)

    fun insertBarang(barang: Barang) {
        viewModelScope.launch {
            barangDao.insert(barang)
        }
    }

    fun updateBarang(barang: Barang) {
        viewModelScope.launch {
            barangDao.update(barang)
        }
    }

    fun deleteBarang(barang: Barang) {
        viewModelScope.launch {
            barangDao.delete(barang)
        }
    }
}

class InventoryViewModelFactory(private val barangDao: BarangDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InventoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return InventoryViewModel(barangDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

data class BarangUiState(
    val id: Int = 0,
    val nama: String = "",
    val kategori: String = "",
    val harga: String = "",
    val stok: String = "",
    val sku: String = "",
    val berat: String = "",
    val status: String = "Tersedia",
    val deskripsi: String = ""
)

fun BarangUiState.toBarang(): Barang = Barang(
    id = id,
    nama = nama,
    kategori = kategori,
    harga = harga.toLongOrNull() ?: 0L,
    stok = stok.toIntOrNull() ?: 0,
    sku = sku,
    berat = berat,
    status = status,
    deskripsi = deskripsi
)

fun Barang.toBarangUiState(): BarangUiState = BarangUiState(
    id = id,
    nama = nama,
    kategori = kategori,
    harga = harga.toString(),
    stok = stok.toString(),
    sku = sku,
    berat = berat,
    status = status,
    deskripsi = deskripsi
)
