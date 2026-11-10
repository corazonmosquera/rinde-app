package com.rinde.tienda

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.google.android.material.snackbar.Snackbar

/** Detalle del producto con su huella de vida útil. */
class ProductDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        val product = ProductRepository.byId(intent.getIntExtra(EXTRA_ID, 1)) ?: run {
            finish()
            return
        }

        findViewById<MaterialToolbar>(R.id.toolbarDetail).setNavigationOnClickListener { finish() }
        findViewById<TextView>(R.id.tvDName).text = product.name
        findViewById<TextView>(R.id.tvDNeed).text = product.need
        findViewById<TextView>(R.id.tvDPrice).text = product.price.cop()
        findViewById<TextView>(R.id.tvDCostYear).text =
            getString(R.string.per_year, product.costPerYear.cop())
        findViewById<TextView>(R.id.tvDAvg).text =
            getString(R.string.lifeprint_avg, product.lifeMonths)
        findViewById<LinearProgressIndicator>(R.id.progressUsing).progress = product.stillUsingPercent
        findViewById<TextView>(R.id.tvDUsing).text =
            getString(R.string.lifeprint_using, product.stillUsingPercent)
        findViewById<TextView>(R.id.tvDFailure).text =
            getString(R.string.lifeprint_failure, product.commonFailure)
        findViewById<TextView>(R.id.tvDReviews).text =
            getString(R.string.lifeprint_reviews, product.reviews)

        findViewById<MaterialButton>(R.id.btnAdd).setOnClickListener {
            CartManager.add(product)
            Snackbar.make(it, R.string.added_to_cart, Snackbar.LENGTH_SHORT).show()
        }
    }

    companion object {
        const val EXTRA_ID = "extra_product_id"
    }
}
