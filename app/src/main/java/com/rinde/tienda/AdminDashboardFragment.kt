package com.rinde.tienda

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

/** Dashboard principal del administrador. */
class AdminDashboardFragment : Fragment(R.layout.fragment_admin_dashboard) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val users = UserRepository.all
        val stats = listOf(
            getString(R.string.stat_users) to users.size.toString(),
            getString(R.string.stat_sellers) to users.count { it.role == Role.VENDEDOR }.toString(),
            getString(R.string.stat_products) to ProductRepository.all.size.toString(),
            getString(R.string.stat_orders) to OrderRepository.all.size.toString(),
            getString(R.string.stat_sales) to OrderRepository.totalSales().cop()
        )

        val rv = view.findViewById<RecyclerView>(R.id.rvStats)
        rv.layoutManager = GridLayoutManager(requireContext(), 2)
        rv.adapter = StatAdapter(stats)
    }
}
