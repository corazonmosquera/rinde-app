package com.rinde.tienda

import android.os.Bundle
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.chip.ChipGroup
import com.google.android.material.materialswitch.MaterialSwitch
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

/** Formulario para crear o editar un usuario. */
class UserFormActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_form)

        val id = intent.getIntExtra(EXTRA_ID, -1)
        val editing: User? = if (id == -1) null else UserRepository.byId(id)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbarUser)
        toolbar.setNavigationOnClickListener { finish() }
        toolbar.setTitle(if (editing == null) R.string.user_form_create else R.string.user_form_edit)

        val tilName = findViewById<TextInputLayout>(R.id.tilUName)
        val tilEmail = findViewById<TextInputLayout>(R.id.tilUEmail)
        val etName = findViewById<TextInputEditText>(R.id.etUName)
        val etEmail = findViewById<TextInputEditText>(R.id.etUEmail)
        val chips = findViewById<ChipGroup>(R.id.chipURole)
        val switchActive = findViewById<MaterialSwitch>(R.id.switchActive)

        if (editing != null) {
            etName.setText(editing.name)
            etEmail.setText(editing.email)
            switchActive.isChecked = editing.active
            chips.check(
                when (editing.role) {
                    Role.VENDEDOR -> R.id.chipURoleSeller
                    Role.ADMINISTRADOR -> R.id.chipURoleAdmin
                    else -> R.id.chipURoleBuyer
                }
            )
        }

        findViewById<MaterialButton>(R.id.btnSaveUser).setOnClickListener {
            val name = etName.text.toString().trim()
            val email = etEmail.text.toString().trim()

            tilName.error = null
            tilEmail.error = null
            var valid = true
            if (name.isEmpty()) { tilName.error = getString(R.string.error_required); valid = false }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                tilEmail.error = getString(R.string.error_email)
                valid = false
            }
            if (!valid) return@setOnClickListener

            val role = when (chips.checkedChipId) {
                R.id.chipURoleSeller -> Role.VENDEDOR
                R.id.chipURoleAdmin -> Role.ADMINISTRADOR
                else -> Role.COMPRADOR
            }

            if (editing == null) {
                UserRepository.add(User(UserRepository.nextId(), name, email, role, switchActive.isChecked))
            } else {
                UserRepository.update(
                    editing.copy(name = name, email = email, role = role, active = switchActive.isChecked)
                )
            }
            finish()
        }
    }

    companion object {
        const val EXTRA_ID = "extra_user_id"
    }
}
