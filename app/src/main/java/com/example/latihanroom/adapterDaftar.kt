package com.example.latihanroom

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.latihanroom.database.daftarBelanja

class adapterDaftar(private val daftarBelanja: MutableList<daftarBelanja>) :
    RecyclerView.Adapter<adapterDaftar.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var _tvTanggal = itemView.findViewById<TextView>(R.id.tvTanggal)
        var _tvItem = itemView.findViewById<TextView>(R.id.tvNama)
        var _tvJumlah = itemView.findViewById<TextView>(R.id.tvJumlah)
        var _btnEdit = itemView.findViewById<ImageView>(R.id.btnEdit)
        var _btnDone = itemView.findViewById<ImageView>(R.id.btnDone)
        var _btnDelete = itemView.findViewById<ImageView>(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): adapterDaftar.ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: adapterDaftar.ListViewHolder, position: Int) {
        val daftar = daftarBelanja[position]

        holder._tvTanggal.text = daftar.tanggal
        holder._tvItem.text = daftar.item
        holder._tvJumlah.text = daftar.jumlah

        holder._btnEdit.setOnClickListener {
            val intent = Intent(it.context, TambahData::class.java)
            intent.putExtra("id", daftar.id)
            intent.putExtra("addEdit", 1)
            it.context.startActivity(intent)
        }

        holder._btnDelete.setOnClickListener {
            onItemClickCallback.delData(daftar)
        }

        holder._btnDone.setOnClickListener {
            val intent = Intent(it.context, historyActivity::class.java)
            intent.putExtra("id", daftar.id)
            intent.putExtra("item", daftar.item)
            intent.putExtra("tanggal", daftar.tanggal)
            intent.putExtra("jumlah", daftar.jumlah)
            intent.putExtra("move", "true")
            onItemClickCallback.delData(daftar)
            it.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return daftarBelanja.size
    }

    interface OnItemClickCallback {
        fun delData(dtBelanja: daftarBelanja)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun isiData(data: List<daftarBelanja>) {
        daftarBelanja.clear()
        daftarBelanja.addAll(data)
        notifyDataSetChanged()
    }
}