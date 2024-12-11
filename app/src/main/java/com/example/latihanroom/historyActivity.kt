package com.example.latihanroom

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.latihanroom.adapterDaftar.OnItemClickCallback
import com.example.latihanroom.database.daftarBelanja
import com.example.latihanroom.database.daftarBelanjaDB
import com.example.latihanroom.database.historyBelanja
import com.example.latihanroom.database.historyBelanjaDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class historyActivity : AppCompatActivity() {
    private lateinit var HistoryDB: historyBelanjaDB
    private lateinit var adapterHistory: adapterHistory
    private var arHistory: MutableList<historyBelanja> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_history)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        HistoryDB = historyBelanjaDB.getHistoryBelanja(this)

        adapterHistory = adapterHistory(arHistory)
        val _rvHistory = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.rvHistory)
        _rvHistory.layoutManager = LinearLayoutManager(this)
        _rvHistory.adapter = adapterHistory

        val _btnBelanja = findViewById<Button>(R.id.btnBelanja)
        val _btnDelete = findViewById<ImageView>(R.id.btnDelete)

        val iID: Int = intent.getIntExtra("id", 0)
        val iItem: String = intent.getStringExtra("item").toString()
        val iTanggal: String = intent.getStringExtra("tanggal").toString()
        val iJumlah: String = intent.getStringExtra("jumlah").toString()
        val iMove: String = intent.getStringExtra("move").toString()

        _btnBelanja.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        if(iMove == "true") {
            CoroutineScope(Dispatchers.IO).async {
                HistoryDB.funhistoryBelanjaDAO().insert(
                    historyBelanja(
                        id = iID,
                        tanggal = iTanggal,
                        item = iItem,
                        jumlah = iJumlah
                    )
                )
                CoroutineScope(Dispatchers.Main).async {
                    val daftarBelanja = HistoryDB.funhistoryBelanjaDAO().selectAll()
                    Log.d("data ROOM", daftarBelanja.toString())
                    adapterHistory.isiHistory(daftarBelanja)
                }
            }
        }

        CoroutineScope(Dispatchers.Main).async {
            val daftarBelanja = HistoryDB.funhistoryBelanjaDAO().selectAll()
            Log.d("data ROOM", daftarBelanja.toString())
            adapterHistory.isiHistory(daftarBelanja)
        }
        adapterHistory.setOnItemClickCallback(
            object : adapterHistory.OnItemClickCallback {
                override fun delData(dtHistory: historyBelanja) {
                    CoroutineScope(Dispatchers.IO).async {
                        HistoryDB.funhistoryBelanjaDAO().delete(dtHistory)
                        val daftar = HistoryDB.funhistoryBelanjaDAO().selectAll()
                        withContext(Dispatchers.Main) {
                            adapterHistory.isiHistory(daftar)
                        }
                    }
                }
            }
        )
    }
}