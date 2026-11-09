package com.rinde.tienda

import java.util.Locale

/** Da formato de pesos colombianos: 190000 -> $190.000 */
fun Int.cop(): String = "$" + String.format(Locale("es", "CO"), "%,d", this)
