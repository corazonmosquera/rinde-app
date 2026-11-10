package com.rinde.tienda

import android.content.ActivityNotFoundException
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.chip.ChipGroup
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

/** Formulario para publicar o editar un producto (con foto desde la cámara). */
class ProductFormActivity : AppCompatActivity() {

    private lateinit var ivPhoto: ImageView

    private val camera = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
        if (bitmap != null) {
            ivPhoto.scaleType = ImageView.ScaleType.CENTER_CROP
            ivPhoto.setImageBitmap(bitmap)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_form)

        val id = intent.getIntExtra(EXTRA_ID, -1)
        val editing: Product? = if (id == -1) null else ProductRepository.byId(id)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbarForm)
        toolbar.setNavigationOnClickListener { finish() }
        toolbar.setTitle(if (editing == null) R.string.seller_publish else R.string.seller_edit)

        ivPhoto = findViewById(R.id.ivPhoto)
        val tilName = findViewById<TextInputLayout>(R.id.tilPName)
        val tilNeed = findViewById<TextInputLayout>(R.id.tilPNeed)
        val tilPrice = findViewById<TextInputLayout>(R.id.tilPPrice)
        val tilStock = findViewById<TextInputLayout>(R.id.tilPStock)
        val tilLife = findViewById<TextInputLayout>(R.id.tilPLife)
        val etName = findViewById<TextInputEditText>(R.id.etPName)
        val etNeed = findViewById<TextInputEditText>(R.id.etPNeed)
        val etPrice = findViewById<TextInputEditText>(R.id.etPPrice)
        val etStock = findViewById<TextInputEditText>(R.id.etPStock)
        val etLife = findViewById<TextInputEditText>(R.id.etPLife)
        val chips = findViewById<ChipGroup>(R.id.chipFormCategory)

        // Si se edita, se cargan los datos actuales
        if (editing != null) {
            etName.setText(editing.name)
            etNeed.setText(editing.need)
            etPrice.setText(editing.price.toString())
            etStock.setText(editing.stock.toString())
            etLife.setText(editing.lifeMonths.toString())
            chips.check(
                when (editing.category) {
                    "Audio" -> R.id.chipFAudio
                    "Calzado" -> R.id.chipFShoes
                    else -> R.id.chipFHome
                }
            )
        }

        findViewById<MaterialButton>(R.id.btnPhoto).setOnClickListener {
            try {
                camera.launch(null)
            } catch (e: ActivityNotFoundException) {
                Snackbar.make(it, R.string.camera_unavailable, Snackbar.LENGTH_LONG).show()
            }
        }

        findViewById<MaterialButton>(R.id.btnSaveProduct).setOnClickListener {
            val name = etName.text.toString().trim()
            val need = etNeed.text.toString().trim()
            val price = etPrice.text.toString().toIntOrNull() ?: 0
            val stock = etStock.text.toString().toIntOrNull()
            val life = etLife.text.toString().toIntOrNull() ?: 0

            listOf(tilName, tilNeed, tilPrice, tilStock, tilLife).forEach { it.error = null }
            var valid = true
            if (name.isEmpty()) { tilName.error = getString(R.string.error_required); valid = false }
            if (need.isEmpty()) { tilNeed.error = getString(R.string.error_required); valid = false }
            if (price <= 0) { tilPrice.error = getString(R.string.error_number); valid = false }
            if (stock == null) { tilStock.error = getString(R.string.error_required); valid = false }
            if (life <= 0) { tilLife.error = getString(R.string.error_number); valid = false }
            if (!valid) return@setOnClickListener

            val category = when (chips.checkedChipId) {
                R.id.chipFAudio -> "Audio"
                R.id.chipFShoes -> "Calzado"
                else -> "Hogar"
            }
            val stockValue = stock ?: 0

            if (editing == null) {
                ProductRepository.add(
                    Product(
                        ProductRepository.nextId(), name, category, need,
                        price, life, 0, "Sin datos aún", 0, stockValue
                    )
                )
            } else {
                ProductRepository.update(
                    editing.copy(
                        name = name, category = category, need = need,
                        price = price, lifeMonths = life, stock = stockValue
                    )
                )
            }
            finish()
        }
    }

    companion object {
        const val EXTRA_ID = "extra_product_form_id"
    }
}
