package com.example.myinventarisbarang

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.myinventarisbarang.ui.InventoryNavGraph
import com.example.myinventarisbarang.ui.theme.MyInventarisBarangTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyInventarisBarangTheme {
                // Memanggil NavGraph dari package .ui untuk menghindari konflik overload
                InventoryNavGraph()
            }
        }
    }
}
