package com.rinde.tienda

data class Order(
    val id: Int,
    val buyer: String,
    val summary: String,
    val total: Int,
    var status: String
)

/** Pedidos recibidos por el vendedor (en memoria). */
object OrderRepository {
    const val PAID = "Pagado"
    const val SHIPPED = "Enviado"
    const val DELIVERED = "Entregado"

    private val items = mutableListOf(
        Order(1042, "Laura Gómez", "Licuadora Vital 600 × 1", 190000, PAID),
        Order(1041, "Carlos Ruiz", "Audífonos Sonar Pro × 2", 300000, SHIPPED),
        Order(1038, "Ana Torres", "Zapatos Trek × 1", 180000, DELIVERED)
    )

    val all: List<Order> get() = items

    /** Crea un pedido a partir del carrito actual (se llama al pagar). */
    fun addFromCart(number: Int) {
        val summary = CartManager.items().joinToString(", ") { "${it.product.name} × ${it.quantity}" }
        items.add(0, Order(number, "Cliente nuevo", summary, CartManager.total(), PAID))
    }

    fun advance(order: Order) {
        order.status = when (order.status) {
            PAID -> SHIPPED
            SHIPPED -> DELIVERED
            else -> order.status
        }
    }

    fun totalSales(): Int = items.sumOf { it.total }
}
