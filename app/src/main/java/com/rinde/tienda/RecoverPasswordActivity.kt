package com.rinde.tienda

import android.os.Bundle
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class RecoverPasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recover_password)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbarRecover)
        val til = findViewById<TextInputLayout>(R.id.tilRecoverEmail)
        val et = findViewById<TextInputEditText>(R.id.etRecoverEmail)

        toolbar.setNavigationOnClickListener { finish() }

        findViewById<MaterialButton>(R.id.btnSendLink).setOnClickListener {
            val email = et.text.toString().trim()
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                til.error = getString(R.string.error_email)
            } else {
                til.error = null
                // Mensaje genérico: no revela si el correo existe (RF-07)
                Snackbar.make(it, R.string.recover_sent, Snackbar.LENGTH_LONG).show()
            }
        }
    }
}
