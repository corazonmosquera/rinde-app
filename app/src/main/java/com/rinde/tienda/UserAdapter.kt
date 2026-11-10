package com.rinde.tienda

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UserAdapter(
    private val onEdit: (User) -> Unit,
    private val onToggle: (User) -> Unit,
    private val onDelete: (User) -> Unit
) : RecyclerView.Adapter<UserAdapter.VH>() {

    private var items: List<User> = emptyList()

    fun submit(list: List<User>) {
        items = list
        notifyDataSetChanged()
    }

    class VH(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.tvUName)
        val detail: TextView = view.findViewById(R.id.tvUDetail)
        val edit: TextView = view.findViewById(R.id.btnUEdit)
        val toggle: TextView = view.findViewById(R.id.btnUToggle)
        val delete: TextView = view.findViewById(R.id.btnUDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH =
        VH(LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false))

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val u = items[position]
        val ctx = holder.itemView.context
        val status = ctx.getString(if (u.active) R.string.status_active else R.string.status_inactive)
        holder.name.text = u.name
        holder.detail.text = ctx.getString(R.string.user_detail, u.email, u.role.label, status)
        holder.toggle.setText(if (u.active) R.string.deactivate else R.string.activate)
        holder.edit.setOnClickListener { onEdit(u) }
        holder.toggle.setOnClickListener { onToggle(u) }
        holder.delete.setOnClickListener { onDelete(u) }
    }
}
