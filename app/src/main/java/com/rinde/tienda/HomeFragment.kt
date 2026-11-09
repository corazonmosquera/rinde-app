package com.rinde.tienda

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

/** Inicio: búsqueda por necesidad y presupuesto, ordenada por costo por año. */
class HomeFragment : Fragment(R.layout.fragment_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etNeed = view.findViewById<TextInputEditText>(R.id.etNeed)
        val etBudget = view.findViewById<TextInputEditText>(R.id.etBudget)
        val rv = view.findViewById<RecyclerView>(R.id.rvHome)
        val empty = view.findViewById<TextView>(R.id.tvEmpty)

        val adapter = ProductAdapter { openProductDetail(it) }
        rv.layoutManager = LinearLayoutManager(requireContext())
        rv.adapter = adapter

        fun search() {
            val list = ProductRepository.search(
                etNeed.text.toString(),
                etBudget.text.toString().toIntOrNull(),
                null
            )
            adapter.submit(list)
            empty.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
        }

        view.findViewById<MaterialButton>(R.id.btnSearch).setOnClickListener { search() }
        search()
    }
}
