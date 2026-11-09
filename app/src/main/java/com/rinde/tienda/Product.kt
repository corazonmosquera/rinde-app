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
    val reviews: Int
) {
    /** Costo por año de uso = precio / (vida útil en meses / 12) */
    val costPerYear: Int get() = (price * 12.0 / lifeMonths).roundToInt()
}

/** Datos de ejemplo. Más adelante se reemplazan por la base de datos. */
object ProductRepository {
    val all = listOf(
        Product(1, "Licuadora Vital 600", "Hogar", "Jugos diarios", 190000, 48, 82, "Sello del vaso (mes 30)", 64),
        Product(2, "Licuadora Express", "Hogar", "Jugos diarios", 120000, 8, 31, "Motor se quema (mes 7)", 112),
        Product(3, "Audífonos Sonar Pro", "Audio", "Música y llamadas", 150000, 36, 74, "Almohadillas (mes 20)", 88),
        Product(4, "Audífonos Mini", "Audio", "Música y llamadas", 60000, 6, 22, "Batería (mes 5)", 140),
        Product(5, "Zapatos Trek", "Calzado", "Caminar a diario", 180000, 30, 70, "Suela (mes 24)", 57),
        Product(6, "Zapatos Urban", "Calzado", "Caminar a diario", 90000, 9, 35, "Costuras (mes 8)", 93)
    )

    fun byId(id: Int): Product = all.firstOrNull { it.id == id } ?: all.first()

    /** Filtra por texto, presupuesto y categoría; ordena por costo por año. */
    fun search(query: String, budget: Int?, category: String?): List<Product> {
        val words = query.lowercase().split(" ").filter { it.length > 2 }
        return all.filter { p ->
            val fields = listOf(p.name, p.need, p.category).map { it.lowercase() }
            (category == null || p.category == category) &&
                (budget == null || p.price <= budget) &&
                (words.isEmpty() || words.any { w -> fields.any { it.contains(w) } })
        }.sortedBy { it.costPerYear }
    }
}
