package com.example.myinventarisbarang.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "barang")
data class Barang(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nama: String,
    val kategori: String,
    val harga: Long,
    val stok: Int,
    val sku: String = "",
    val berat: String = "",
    val status: String = "Tersedia",
    val deskripsi: String = ""
)
