package com.example.latihanroom.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [daftarBelanja::class], version = 1)
abstract class daftarBelanjaDB : RoomDatabase() {
    abstract fun fundaftarBelanjaDAO(): daftarBelanjaDAO

    companion object {
        @Volatile
        private var INSTANCE_Belanja: daftarBelanjaDB? = null

        @JvmStatic
        fun getDatabaseBelanja(context: Context): daftarBelanjaDB {
            if (INSTANCE_Belanja == null) {
                synchronized(daftarBelanjaDB::class.java) {
                    INSTANCE_Belanja = Room.databaseBuilder(
                        context.applicationContext,
                        daftarBelanjaDB::class.java, "daftarBelanja_db"
                    )
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE_Belanja as daftarBelanjaDB
        }
    }
}

