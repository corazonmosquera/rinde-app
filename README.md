# Rinde – Tienda virtual Android

Tienda que permite comprar por necesidad y presupuesto, ordenando los productos por **costo por año de uso** y mostrando su **huella de vida útil**.

## Roles
Comprador · Vendedor · Administrador

## Tecnología
Kotlin, vistas XML, Material Components, AndroidX Biometric. minSdk 26, compileSdk 34.

## Estructura
- `app/src/main/java/com/rinde/tienda`: activities y fragments
- `app/src/main/res/layout`: layouts XML
- `app/src/main/res/values`: colores, textos y temas
- `app/src/main/res/drawable`: íconos y fondos
- `app/src/main/res/menu`: menús inferior y lateral

## Generar el APK
Cada `git push` a la rama `main` ejecuta GitHub Actions. Entra a la pestaña **Actions**, abre la última ejecución y descarga **rinde-apk** en *Artifacts*.
