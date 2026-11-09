package com.rinde.tienda

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.chip.ChipGroup
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity() {

    private lateinit var chipRoles: ChipGroup
    private lateinit var tilEmail: TextInputLayout
    private lateinit var tilPassword: TextInputLayout
    private lateinit var etEmail: TextInputEditText
    private lateinit var etPassword: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        chipRoles = findViewById(R.id.chipRoles)
        tilEmail = findViewById(R.id.tilEmail)
        tilPassword = findViewById(R.id.tilPassword)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)

        findViewById<MaterialButton>(R.id.btnLogin).setOnClickListener { login() }
        findViewById<MaterialButton>(R.id.btnBiometric).setOnClickListener { loginWithBiometrics() }
        findViewById<TextView>(R.id.tvRecover).setOnClickListener {
            startActivity(Intent(this, RecoverPasswordActivity::class.java))
        }
    }

    private fun selectedRole(): Role = when (chipRoles.checkedChipId) {
        R.id.chipSeller -> Role.VENDEDOR
        R.id.chipAdmin -> Role.ADMINISTRADOR
        else -> Role.COMPRADOR
    }

    private fun login() {
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString()

        tilEmail.error = null
        tilPassword.error = null

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            tilEmail.error = getString(R.string.error_email)
            return
        }
        if (password.length < 6) {
            tilPassword.error = getString(R.string.error_password)
            return
        }
        openMain(email)
    }

    private fun loginWithBiometrics() {
        val canUse = BiometricManager.from(this)
            .canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_WEAK)
        if (canUse != BiometricManager.BIOMETRIC_SUCCESS) {
            Toast.makeText(this, R.string.biometric_unavailable, Toast.LENGTH_LONG).show()
            return
        }

        val prompt = BiometricPrompt(
            this,
            ContextCompat.getMainExecutor(this),
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    val email = etEmail.text.toString().trim().ifEmpty { "usuario@rinde.co" }
                    openMain(email)
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

    private fun openMain(email: String) {
        val intent = Intent(this, MainActivity::class.java)
            .putExtra(MainActivity.EXTRA_ROLE, selectedRole().name)
            .putExtra(MainActivity.EXTRA_EMAIL, email)
        startActivity(intent)
        finish()
    }
}
