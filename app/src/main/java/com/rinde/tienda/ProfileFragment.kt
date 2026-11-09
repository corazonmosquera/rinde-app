package com.rinde.tienda

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText

/** Perfil: datos personales y activación del ingreso con huella. */
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val email = requireActivity().intent.getStringExtra(MainActivity.EXTRA_EMAIL).orEmpty()
        val name = email.substringBefore("@")
            .replaceFirstChar { it.uppercase() }
            .ifEmpty { "Usuario" }

        view.findViewById<TextView>(R.id.tvAvatar).text = name.take(1)
        view.findViewById<TextView>(R.id.tvProfileName).text = name
        view.findViewById<TextView>(R.id.tvProfileEmail).text = email
        view.findViewById<TextInputEditText>(R.id.etProfileName).setText(name)

        view.findViewById<MaterialButton>(R.id.btnSave).setOnClickListener {
            Snackbar.make(it, R.string.profile_saved, Snackbar.LENGTH_SHORT).show()
        }
    }
}
