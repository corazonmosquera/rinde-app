package com.rinde.tienda

import kotlin.math.roundToInt

data class Product(
    val id: Int,
    val name: String,
    val category: String,
    val need: String,
    val price: Int,
    val lifeMonths: Int,
    val stillUsingPercent: Int,
    val commonFailure: String,
    val reviews: Int,
    val stock: Int = 10
) {
    /** Costo por año de uso = precio / (vida útil en meses / 12) */
    val costPerYear: Int get() = (price * 12.0 / lifeMonths).roundToInt()
}

/** Datos de ejemplo en memoria. Más adelante se reemplazan por la base de datos. */
object ProductRepository {
    private val items = mutableListOf(
        Product(1, "Licuadora Vital 600", "Hogar", "Jugos diarios", 190000, 48, 82, "Sello del vaso (mes 30)", 64, 14),
        Product(2, "Licuadora Express", "Hogar", "Jugos diarios", 120000, 8, 31, "Motor se quema (mes 7)", 112, 25),
        Product(3, "Audífonos Sonar Pro", "Audio", "Música y llamadas", 150000, 36, 74, "Almohadillas (mes 20)", 88, 3),
        Product(4, "Audífonos Mini", "Audio", "Música y llamadas", 60000, 6, 22, "Batería (mes 5)", 140, 40),
        Product(5, "Zapatos Trek", "Calzado", "Caminar a diario", 180000, 30, 70, "Suela (mes 24)", 57, 12),
        Product(6, "Zapatos Urban", "Calzado", "Caminar a diario", 90000, 9, 35, "Costuras (mes 8)", 93, 18)
    )

    val all: List<Product> get() = items

    fun byId(id: Int): Product? = items.firstOrNull { it.id == id }

    fun nextId(): Int = (items.maxOfOrNull { it.id } ?: 0) + 1

    fun add(product: Product) { items.add(product) }

    fun update(product: Product) {
        val index = items.indexOfFirst { it.id == product.id }
        if (index >= 0) items[index] = product
    }

    fun delete(id: Int) { items.removeAll { it.id == id } }

    /** Filtra por texto, presupuesto y categoría; ordena por costo por año. */
    fun search(query: String, budget: Int?, category: String?): List<Product> {
        val words = query.lowercase().split(" ").filter { it.length > 2 }
        return items.filter { p ->
            val fields = listOf(p.name, p.need, p.category).map { it.lowercase() }
            (category == null || p.category == category) &&
                (budget == null || p.price <= budget) &&
                (words.isEmpty() || words.any { w -> fields.any { it.contains(w) } })
        }.sortedBy { it.costPerYear }
    }
}
