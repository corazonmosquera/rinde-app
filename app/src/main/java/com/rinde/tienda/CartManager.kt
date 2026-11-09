package com.rinde.tienda

data class CartItem(val product: Product, var quantity: Int)

/** Carrito en memoria (se reemplaza por base de datos más adelante). */
object CartManager {
    private val map = LinkedHashMap<Int, CartItem>()

    fun items(): List<CartItem> = map.values.toList()

    fun add(product: Product) {
        val item = map[product.id]
        if (item == null) map[product.id] = CartItem(product, 1) else item.quantity++
    }

    fun change(id: Int, delta: Int) {
        val item = map[id] ?: return
        item.quantity += delta
        if (item.quantity <= 0) map.remove(id)
    }

    fun remove(id: Int) { map.remove(id) }
    fun clear() = map.clear()
    fun total(): Int = map.values.sumOf { it.product.price * it.quantity }
    fun count(): Int = map.values.sumOf { it.quantity }
}
