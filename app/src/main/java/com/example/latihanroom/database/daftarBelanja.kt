package com.example.latihanroom.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class daftarBelanja(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "Id")
    val id: Int = 0,

    @ColumnInfo(name = "tanggal")
    val tanggal: String? = null,

    @ColumnInfo(name = "item")
    val item: String? = null,

    @ColumnInfo(name = "jumlah")
    var jumlah: String? = null,

    @ColumnInfo(name = "status")
    var status: Int = 0
)
