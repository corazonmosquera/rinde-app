package com.rinde.tienda

import android.os.Bundle
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlin.random.Random

/** Pasarela de pagos: resumen, dirección, método y confirmación con huella. */
class PaymentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        findViewById<MaterialToolbar>(R.id.toolbarPay).setNavigationOnClickListener { finish() }
        findViewById<TextView>(R.id.tvPayItems).text =
            getString(R.string.pay_items, CartManager.count())
        findViewById<TextView>(R.id.tvPayTotal).text = CartManager.total().cop()

        val tilAddress = findViewById<TextInputLayout>(R.id.tilAddress)
        val etAddress = findViewById<TextInputEditText>(R.id.etAddress)
        val cbTerms = findViewById<CheckBox>(R.id.cbTerms)

        findViewById<MaterialButton>(R.id.btnPay).setOnClickListener {
            tilAddress.error = null
            if (etAddress.text.isNullOrBlank()) {
                tilAddress.error = getString(R.string.error_address)
                return@setOnClickListener
            }
            if (!cbTerms.isChecked) {
                Snackbar.make(it, R.string.error_terms, Snackbar.LENGTH_LONG).show()
                return@setOnClickListener
            }
            authenticateAndPay()
        }
    }

    private fun authenticateAndPay() {
        val canUse = BiometricManager.from(this)
            .canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_WEAK)
        if (canUse != BiometricManager.BIOMETRIC_SUCCESS) {
            confirmPayment()
            return
        }
        val prompt = BiometricPrompt(
            this,
            ContextCompat.getMainExecutor(this),
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    confirmPayment()
                }
            }
        )
        val info = BiometricPrompt.PromptInfo.Builder()
            .setTitle(getString(R.string.biometric_title))
            .setSubtitle(getString(R.string.biometric_subtitle))
            .setNegativeButtonText(getString(R.string.biometric_cancel))
            .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_WEAK)
            .build()
        prompt.authenticate(info)
    }

    private fun confirmPayment() {
        val order = "#" + (1000 + Random.nextInt(9000))
        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.pay_ok_title)
            .setMessage(getString(R.string.pay_ok_msg, order))
            .setCancelable(false)
            .setPositiveButton(R.string.ok) { _, _ ->
                CartManager.clear()
                finish()
            }
            .show()
    }
}
