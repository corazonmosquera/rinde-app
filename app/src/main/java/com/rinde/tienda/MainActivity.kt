package com.rinde.tienda

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val role = Role.valueOf(intent.getStringExtra(EXTRA_ROLE) ?: Role.COMPRADOR.name)
        val email = intent.getStringExtra(EXTRA_EMAIL).orEmpty()

        val drawer = findViewById<DrawerLayout>(R.id.drawerLayout)
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        val navView = findViewById<NavigationView>(R.id.navView)

        // Color propio de cada rol
        val color = ContextCompat.getColor(this, role.colorRes)
        toolbar.setBackgroundColor(color)
        window.statusBarColor = color
        navView.getHeaderView(0).apply {
            setBackgroundColor(color)
            findViewById<TextView>(R.id.navRole).text = role.label
            findViewById<TextView>(R.id.navEmail).text = email
        }

        // Menú lateral (Drawer)
        toolbar.setNavigationOnClickListener { drawer.openDrawer(GravityCompat.START) }
        navView.setNavigationItemSelectedListener { item ->
            drawer.closeDrawer(GravityCompat.START)
            if (item.itemId == R.id.drawer_logout) {
                startActivity(Intent(this, LoginActivity::class.java))
                finishAffinity()
            } else {
                showScreen(item.title.toString(), role, toolbar)
            }
            true
        }

        // Menú inferior (Bottom Navigation) según el rol
        bottomNav.inflateMenu(role.menuRes)
        bottomNav.setOnItemSelectedListener { item ->
            showScreen(item.title.toString(), role, toolbar)
            true
        }
        bottomNav.selectedItemId = bottomNav.menu.getItem(0).itemId
    }

    private fun showScreen(title: String, role: Role, toolbar: MaterialToolbar) {
        toolbar.title = title
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, PlaceholderFragment.newInstance(title, role.label))
            .commit()
    }

    companion object {
        const val EXTRA_ROLE = "extra_role"
        const val EXTRA_EMAIL = "extra_email"
    }
}
