package com.rinde.tienda

data class User(
    val id: Int,
    val name: String,
    val email: String,
    val role: Role,
    val active: Boolean
)

/** Usuarios de ejemplo en memoria. Más adelante se reemplazan por la base de datos. */
object UserRepository {
    private val items = mutableListOf(
        User(1, "Laura Gómez", "laura@correo.com", Role.COMPRADOR, true),
        User(2, "Carlos Ruiz", "carlos@correo.com", Role.COMPRADOR, false),
        User(3, "Tienda Hogar+", "hogar@correo.com", Role.VENDEDOR, true),
        User(4, "TecnoSur", "tecnosur@correo.com", Role.VENDEDOR, true),
        User(5, "Admin Rinde", "admin@rinde.co", Role.ADMINISTRADOR, true)
    )

    val all: List<User> get() = items

    fun byId(id: Int): User? = items.firstOrNull { it.id == id }

    fun nextId(): Int = (items.maxOfOrNull { it.id } ?: 0) + 1

    fun add(user: User) { items.add(user) }

    fun update(user: User) {
        val index = items.indexOfFirst { it.id == user.id }
        if (index >= 0) items[index] = user
    }

    fun delete(id: Int) { items.removeAll { it.id == id } }
}
