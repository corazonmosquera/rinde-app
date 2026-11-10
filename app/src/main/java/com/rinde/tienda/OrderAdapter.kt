package com.rinde.tienda

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton

class OrderAdapter(private val onAdvance: (Order) -> Unit) :
    RecyclerView.Adapter<OrderAdapter.VH>() {

    private var items: List<Order> = emptyList()

    fun submit(list: List<Order>) {
        items = list
        notifyDataSetChanged()
    }

    class VH(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.tvOrderTitle)
        val body: TextView = view.findViewById(R.id.tvOrderBody)
        val total: TextView = view.findViewById(R.id.tvOrderTotal)
        val advance: MaterialButton = view.findViewById(R.id.btnAdvance)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH =
        VH(LayoutInflater.from(parent.context).inflate(R.layout.item_order, parent, false))

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val order = items[position]
        val ctx = holder.itemView.context
        holder.title.text = ctx.getString(R.string.order_title, order.id, order.status)
        holder.body.text = order.summary + "\n" + ctx.getString(R.string.order_buyer, order.buyer)
        holder.total.text = order.total.cop()

        if (order.status == OrderRepository.DELIVERED) {
            holder.advance.visibility = View.GONE
        } else {
            holder.advance.visibility = View.VISIBLE
            holder.advance.setText(
                if (order.status == OrderRepository.PAID) R.string.mark_shipped else R.string.mark_delivered
            )
            holder.advance.setOnClickListener { onAdvance(order) }
        }
    }
}
