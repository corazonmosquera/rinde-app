package com.rinde.tienda

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment

/** Pantalla temporal. Se reemplaza por la pantalla real de cada sección. */
class PlaceholderFragment : Fragment(R.layout.fragment_placeholder) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = requireArguments()
        view.findViewById<TextView>(R.id.tvTitle).text = args.getString(ARG_TITLE)
        view.findViewById<TextView>(R.id.tvSubtitle).text =
            getString(R.string.placeholder_subtitle, args.getString(ARG_ROLE))
    }

    companion object {
        private const val ARG_TITLE = "title"
        private const val ARG_ROLE = "role"

        fun newInstance(title: String, role: String) = PlaceholderFragment().apply {
            arguments = bundleOf(ARG_TITLE to title, ARG_ROLE to role)
        }
    }
}
