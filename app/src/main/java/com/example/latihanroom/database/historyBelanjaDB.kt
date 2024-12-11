package com.example.latihanroom.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [historyBelanja::class], version = 1)
abstract class historyBelanjaDB : RoomDatabase() {
    abstract fun funhistoryBelanjaDAO(): historyBelanjaDAO

    companion object {
        @Volatile
        private var INSTANCE_History: historyBelanjaDB? = null

        @JvmStatic
        fun getHistoryBelanja(context: Context): historyBelanjaDB {
            if (INSTANCE_History == null) {
                synchronized(historyBelanjaDB::class.java) {
                    INSTANCE_History = Room.databaseBuilder(
                        context.applicationContext,
                        historyBelanjaDB::class.java, "historyBelanja_db"
                    )
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE_History as historyBelanjaDB
        }
    }
}

