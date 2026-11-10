package com.rinde.tienda

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/** Historial de pedidos recibidos por el vendedor. */
class SellerOrdersFragment : Fragment(R.layout.fragment_orders) {

    private lateinit var adapter: OrderAdapter
    private lateinit var tvEmpty: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvEmpty = view.findViewById(R.id.tvOrdersEmpty)
        val rv = view.findViewById<RecyclerView>(R.id.rvOrders)

        adapter = OrderAdapter { order ->
            OrderRepository.advance(order)
            refresh()
        }
        rv.layoutManager = LinearLayoutManager(requireContext())
        rv.adapter = adapter
        refresh()
    }

    private fun refresh() {
        val list = OrderRepository.all
        adapter.submit(list)
        tvEmpty.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
    }
}
