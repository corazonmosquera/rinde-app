package com.rinde.tienda

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/** Tarjetas de estadísticas (etiqueta y valor) para dashboard y reportes. */
class StatAdapter(private val stats: List<Pair<String, String>>) :
    RecyclerView.Adapter<StatAdapter.VH>() {

    class VH(view: View) : RecyclerView.ViewHolder(view) {
        val label: TextView = view.findViewById(R.id.tvStatLabel)
        val value: TextView = view.findViewById(R.id.tvStatValue)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH =
        VH(LayoutInflater.from(parent.context).inflate(R.layout.item_stat, parent, false))

    override fun getItemCount(): Int = stats.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.label.text = stats[position].first
        holder.value.text = stats[position].second
    }
}
