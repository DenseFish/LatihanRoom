package com.example.latihanroom

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.latihanroom.database.daftarBelanja
import com.example.latihanroom.database.daftarBelanjaDB
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var BelanjaDB: daftarBelanjaDB
    private lateinit var adapterDaftar: adapterDaftar
    private var arDaftar: MutableList<daftarBelanja> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        BelanjaDB = daftarBelanjaDB.getDatabaseBelanja(this)

        adapterDaftar = adapterDaftar(arDaftar)
        val _rvDaftar = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.rvDaftar)
        _rvDaftar.layoutManager = LinearLayoutManager(this)
        _rvDaftar.adapter = adapterDaftar

        val _fabAdd = findViewById<FloatingActionButton>(R.id.fabAdd)
        val _fabRefresh = findViewById<FloatingActionButton>(R.id.fabRefresh)
        val _btnHistory = findViewById<Button>(R.id.btnHistory)

        _fabAdd.setOnClickListener {
            startActivity(Intent(this, TambahData::class.java))
        }

        _fabRefresh.setOnClickListener {
            CoroutineScope(Dispatchers.Main).async {
                val daftarBelanja = BelanjaDB.fundaftarBelanjaDAO().selectAll()
                Log.d("data ROOM", daftarBelanja.toString())
                adapterDaftar.isiData(daftarBelanja)
            }
        }

        _btnHistory.setOnClickListener {
            startActivity(Intent(this, historyActivity::class.java))
        }

        super.onStart()
        CoroutineScope(Dispatchers.Main).async {
            val daftarBelanja = BelanjaDB.fundaftarBelanjaDAO().selectAll()
            Log.d("data ROOM", daftarBelanja.toString())
            adapterDaftar.isiData(daftarBelanja)
        }
        adapterDaftar.setOnItemClickCallback(
            object : adapterDaftar.OnItemClickCallback {
                override fun delData(dtBelanja: daftarBelanja) {
                    CoroutineScope(Dispatchers.IO).async {
                        BelanjaDB.fundaftarBelanjaDAO().delete(dtBelanja)
                        val daftar = BelanjaDB.fundaftarBelanjaDAO().selectAll()
                        withContext(Dispatchers.Main) {
                            adapterDaftar.isiData(daftar)
                        }
                    }
                }
            }
        )
    }
}