package com.rinde.tienda

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar

/** Carrito: ver, cambiar cantidades y quitar productos. */
class CartFragment : Fragment(R.layout.fragment_cart) {

    private lateinit var adapter: CartAdapter
    private lateinit var tvEmpty: TextView
    private lateinit var tvTotal: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvEmpty = view.findViewById(R.id.tvCartEmpty)
        tvTotal = view.findViewById(R.id.tvCartTotal)
        val rv = view.findViewById<RecyclerView>(R.id.rvCart)

        adapter = CartAdapter { refresh() }
        rv.layoutManager = LinearLayoutManager(requireContext())
        rv.adapter = adapter

        view.findViewById<MaterialButton>(R.id.btnCheckout).setOnClickListener {
            if (CartManager.count() == 0) {
                Snackbar.make(it, R.string.cart_empty, Snackbar.LENGTH_SHORT).show()
            } else {
                startActivity(Intent(requireContext(), PaymentActivity::class.java))
            }
        }
    }

    override fun onResume() {
        super.onResume()
        refresh()
    }

    private fun refresh() {
        val items = CartManager.items()
        adapter.submit(items)
        tvEmpty.visibility = if (items.isEmpty()) View.VISIBLE else View.GONE
        tvTotal.text = getString(R.string.cart_total, CartManager.total().cop())
    }
}
