package com.example.latihanroom

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.latihanroom.database.historyBelanja

class adapterHistory(private val historyBelanja: MutableList<historyBelanja>) :
    RecyclerView.Adapter<adapterHistory.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var _tvTanggal = itemView.findViewById<TextView>(R.id.tvTanggal)
        var _tvItem = itemView.findViewById<TextView>(R.id.tvNama)
        var _tvJumlah = itemView.findViewById<TextView>(R.id.tvJumlah)
        var _btnDelete = itemView.findViewById<ImageView>(R.id.btnDelete)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): adapterHistory.ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.history_list, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return historyBelanja.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val daftar = historyBelanja[position]

        holder._tvTanggal.text = daftar.tanggal
        holder._tvItem.text = daftar.item
        holder._tvJumlah.text = daftar.jumlah

        holder._btnDelete.setOnClickListener {
            onItemClickCallback.delData(daftar)
        }
    }

    fun isiHistory(data: List<historyBelanja>) {
        historyBelanja.clear()
        historyBelanja.addAll(data)
        notifyDataSetChanged()
    }

    interface OnItemClickCallback {
        fun delData(dtHistory: historyBelanja)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
}

