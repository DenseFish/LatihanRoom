package com.example.latihanroom.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface historyBelanjaDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(daftar: historyBelanja)

    @Delete
    fun delete(daftar: historyBelanja)

    @Query("SELECT * FROM historyBelanja ORDER BY id asc")
    fun selectAll(): MutableList<historyBelanja>
}