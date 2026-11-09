package com.rinde.tienda

/** Roles de la tienda. Cada rol tiene su color y su menú inferior. */
enum class Role(val label: String, val colorRes: Int, val menuRes: Int) {
    COMPRADOR("Comprador", R.color.role_buyer, R.menu.bottom_comprador),
    VENDEDOR("Vendedor", R.color.role_seller, R.menu.bottom_vendedor),
    ADMINISTRADOR("Administrador", R.color.role_admin, R.menu.bottom_admin)
}
