package com.rinde.tienda

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

class ProductAdapter(private val onClick: (Product) -> Unit) :
    RecyclerView.Adapter<ProductAdapter.VH>() {

    private var items: List<Product> = emptyList()

    fun submit(list: List<Product>) {
        items = list
        notifyDataSetChanged()
    }

    class VH(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.tvName)
        val need: TextView = view.findViewById(R.id.tvNeed)
        val price: TextView = view.findViewById(R.id.tvPrice)
        val costYear: TextView = view.findViewById(R.id.tvCostYear)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH =
        VH(LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false))

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val p = items[position]
        val ctx = holder.itemView.context
        holder.name.text = p.name
        holder.need.text = p.need
        holder.price.text = "${p.price.cop()} · " + ctx.getString(R.string.life_months, p.lifeMonths)
        holder.costYear.text = ctx.getString(R.string.per_year, p.costPerYear.cop())
        holder.itemView.setOnClickListener { onClick(p) }
    }
}

fun Fragment.openProductDetail(product: Product) {
    startActivity(
        Intent(requireContext(), ProductDetailActivity::class.java)
            .putExtra(ProductDetailActivity.EXTRA_ID, product.id)
    )
}
