package com.example.myinventarisbarang.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myinventarisbarang.model.InventoryDatabase
import com.example.myinventarisbarang.ui.screens.*

@Composable
fun InventoryNavGraph() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val database = InventoryDatabase.getDatabase(context)
    val viewModel: InventoryViewModel = viewModel(
        factory = InventoryViewModelFactory(database.barangDao())
    )

    NavHost(navController = navController, startDestination = "daftar") {
        composable("daftar") {
            DaftarBarangScreen(
                onItemClick = { id ->
                    navController.navigate("detail/$id")
                },
                onFabClick = {
                    navController.navigate("tambah")
                },
                viewModel = viewModel
            )
        }
        composable(
            route = "detail/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: 0
            DetailBarangScreen(
                barangId = id,
                onBackClick = {
                    navController.popBackStack()
                },
                viewModel = viewModel
            )
        }
        composable("tambah") {
            TambahBarangScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onSaveClick = { uiState ->
                    viewModel.insertBarang(uiState.toBarang())
                    navController.popBackStack()
                }
            )
        }
    }
}
