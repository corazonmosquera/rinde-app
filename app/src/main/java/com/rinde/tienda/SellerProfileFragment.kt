package com.rinde.tienda

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText

/** Perfil del vendedor: datos de la tienda y resumen de ventas. */
class SellerProfileFragment : Fragment(R.layout.fragment_seller_profile) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val email = requireActivity().intent.getStringExtra(MainActivity.EXTRA_EMAIL).orEmpty()
        val store = email.substringBefore("@")
            .replaceFirstChar { it.uppercase() }
            .ifEmpty { "Mi tienda" }

        view.findViewById<TextView>(R.id.tvSAvatar).text = store.take(1)
        view.findViewById<TextView>(R.id.tvSStore).text = store
        view.findViewById<TextView>(R.id.tvSEmail).text = email
        view.findViewById<TextView>(R.id.tvSalesValue).text = OrderRepository.totalSales().cop()
        view.findViewById<TextView>(R.id.tvOrdersValue).text = OrderRepository.all.size.toString()
        view.findViewById<TextInputEditText>(R.id.etStoreName).setText(store)

        view.findViewById<MaterialButton>(R.id.btnSaveStore).setOnClickListener {
            Snackbar.make(it, R.string.profile_saved, Snackbar.LENGTH_SHORT).show()
        }
    }
}
