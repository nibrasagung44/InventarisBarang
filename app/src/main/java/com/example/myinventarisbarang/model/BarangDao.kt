package com.example.myinventarisbarang.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface BarangDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(barang: Barang)

    @Update
    suspend fun update(barang: Barang)

    @Delete
    suspend fun delete(barang: Barang)

    @Query("SELECT * from barang WHERE id = :id")
    fun getBarang(id: Int): Flow<Barang>

    @Query("SELECT * from barang ORDER BY nama ASC")
    fun getAllBarang(): Flow<List<Barang>>
}
