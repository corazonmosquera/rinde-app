package com.rinde.tienda

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SellerProductAdapter(
    private val onEdit: (Product) -> Unit,
    private val onDelete: (Product) -> Unit
) : RecyclerView.Adapter<SellerProductAdapter.VH>() {

    private var items: List<Product> = emptyList()

    fun submit(list: List<Product>) {
        items = list
        notifyDataSetChanged()
    }

    class VH(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.tvSName)
        val detail: TextView = view.findViewById(R.id.tvSDetail)
        val edit: TextView = view.findViewById(R.id.btnEdit)
        val delete: TextView = view.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH =
        VH(LayoutInflater.from(parent.context).inflate(R.layout.item_seller_product, parent, false))

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val p = items[position]
        holder.name.text = p.name
        holder.detail.text = holder.itemView.context.getString(
            R.string.seller_detail, p.price.cop(), p.stock, p.costPerYear.cop()
        )
        holder.edit.setOnClickListener { onEdit(p) }
        holder.delete.setOnClickListener { onDelete(p) }
    }
}
