package com.rinde.tienda

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.progressindicator.LinearProgressIndicator

/** Reporte de ventas: totales y pedidos por estado. */
class AdminReportsFragment : Fragment(R.layout.fragment_reports) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val orders = OrderRepository.all
        val total = OrderRepository.totalSales()
        val average = if (orders.isEmpty()) 0 else total / orders.size

        val stats = listOf(
            getString(R.string.stat_sales) to total.cop(),
            getString(R.string.stat_orders) to orders.size.toString(),
            getString(R.string.stat_avg) to average.cop()
        )
        val rv = view.findViewById<RecyclerView>(R.id.rvReportStats)
        rv.layoutManager = GridLayoutManager(requireContext(), 2)
        rv.adapter = StatAdapter(stats)

        fun percent(status: String): Int =
            if (orders.isEmpty()) 0 else orders.count { it.status == status } * 100 / orders.size

        view.findViewById<LinearProgressIndicator>(R.id.barPaid).progress = percent(OrderRepository.PAID)
        view.findViewById<LinearProgressIndicator>(R.id.barShipped).progress = percent(OrderRepository.SHIPPED)
        view.findViewById<LinearProgressIndicator>(R.id.barDelivered).progress = percent(OrderRepository.DELIVERED)
    }
}
