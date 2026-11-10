package com.rinde.tienda

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder

/** Panel de productos del vendedor: listar, crear, editar y eliminar. */
class SellerProductsFragment : Fragment(R.layout.fragment_seller_products) {

    private lateinit var adapter: SellerProductAdapter
    private lateinit var tvEmpty: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvEmpty = view.findViewById(R.id.tvSellerEmpty)
        val rv = view.findViewById<RecyclerView>(R.id.rvSeller)

        adapter = SellerProductAdapter(
            onEdit = { product ->
                startActivity(
                    Intent(requireContext(), ProductFormActivity::class.java)
                        .putExtra(ProductFormActivity.EXTRA_ID, product.id)
                )
            },
            onDelete = { product -> confirmDelete(product) }
        )
        rv.layoutManager = LinearLayoutManager(requireContext())
        rv.adapter = adapter

        view.findViewById<MaterialButton>(R.id.btnPublish).setOnClickListener {
            startActivity(Intent(requireContext(), ProductFormActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        refresh()
    }

    private fun refresh() {
        val list = ProductRepository.all
        adapter.submit(list)
        tvEmpty.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
    }

    private fun confirmDelete(product: Product) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.delete_title)
            .setMessage(getString(R.string.delete_msg, product.name))
            .setNegativeButton(R.string.cancel, null)
            .setPositiveButton(R.string.delete) { _, _ ->
                ProductRepository.delete(product.id)
                refresh()
            }
            .show()
    }
}
