package com.rinde.tienda

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton

class CartAdapter(private val onChange: () -> Unit) :
    RecyclerView.Adapter<CartAdapter.VH>() {

    private var items: List<CartItem> = emptyList()

    fun submit(list: List<CartItem>) {
        items = list
        notifyDataSetChanged()
    }

    class VH(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.tvCartName)
        val detail: TextView = view.findViewById(R.id.tvCartDetail)
        val qty: TextView = view.findViewById(R.id.tvQty)
        val minus: MaterialButton = view.findViewById(R.id.btnMinus)
        val plus: MaterialButton = view.findViewById(R.id.btnPlus)
        val remove: TextView = view.findViewById(R.id.btnRemove)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH =
        VH(LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false))

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]
        val p = item.product
        val ctx = holder.itemView.context
        holder.name.text = p.name
        holder.detail.text = "${p.price.cop()} · " + ctx.getString(R.string.per_year, p.costPerYear.cop())
        holder.qty.text = item.quantity.toString()
        holder.minus.setOnClickListener { CartManager.change(p.id, -1); onChange() }
        holder.plus.setOnClickListener { CartManager.change(p.id, 1); onChange() }
        holder.remove.setOnClickListener { CartManager.remove(p.id); onChange() }
    }
}
