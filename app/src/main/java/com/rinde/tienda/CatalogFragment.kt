package com.rinde.tienda

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.ChipGroup

/** Catálogo: todos los productos con filtro por categoría. */
class CatalogFragment : Fragment(R.layout.fragment_catalog) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val chips = view.findViewById<ChipGroup>(R.id.chipCategories)
        val rv = view.findViewById<RecyclerView>(R.id.rvCatalog)
        val adapter = ProductAdapter { openProductDetail(it) }
        rv.layoutManager = LinearLayoutManager(requireContext())
        rv.adapter = adapter

        fun show(checkedId: Int?) {
            val category = when (checkedId) {
                R.id.chipHome -> "Hogar"
                R.id.chipAudio -> "Audio"
                R.id.chipShoes -> "Calzado"
                else -> null
            }
            adapter.submit(ProductRepository.search("", null, category))
        }

        chips.setOnCheckedStateChangeListener { _, ids -> show(ids.firstOrNull()) }
        show(null)
    }
}
