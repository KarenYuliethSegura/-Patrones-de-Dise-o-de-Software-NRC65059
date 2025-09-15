📦 Inventario Burgerhouse

Proyecto académico en Java que implementa un sistema de inventario para una hamburguesería ficticia, aplicando patrones de diseño:

Singleton → GestorInventario

Builder → construcción paso a paso de productos

Prototype → clonación de productos

Abstract Factory → creación de familias de productos por categoría

🚀 Estructura del proyecto
inventario.burgerhouse
├── builder/
│   ├── ProductoBuilder.java
│   ├── BebidaBuilder.java
│   ├── ProductoFrescoBuilder.java
│   ├── ProductoConservaBuilder.java
│   └── ProductoPanaderiaBuilder.java
│
├── domain/
│   ├── Producto.java
│   ├── Bebida.java
│   ├── ProductoFresco.java
│   ├── ProductoConserva.java
│   ├── ProductoPanaderia.java
│   ├── Lote.java
│   └── Categoria.java
│
├── factory/
│   ├── AbstractCategoriaFactory.java
│   ├── FabricaBebidas.java
│   ├── FabricaFrescos.java
│   ├── FabricaConservas.java
│   ├── FabricaPanaderia.java
│   └── CategoriaFactoryProvider.java
│
├── singleton/
│   ├── GestorInventario.java
│   ├── ProductoNoEncontradoException.java
│   └── StockInsuficienteException.java
│
└── main/
    └── InventarioBurgerhouse.java

⚙️ Requisitos

Java 17+ (puede correr en Java 11, pero se recomienda >=17).

IDE como NetBeans, IntelliJ o Eclipse, o consola con javac/java.

▶️ Ejecución

Compila y ejecuta desde consola:

javac -d out $(find src -name "*.java")
java -cp out inventario.burgerhouse.main.InventarioBurgerhouse


Salida esperada (resumen):

=== DEMO Burgerhouse ===
Stock Gaseosa (B-001) inicial: 12
Stock Lechuga (F-010) inicial: 6
Lotes activos B-001 (post PEPS): [L2]
OK esperado: Stock insuficiente (no vencido) para F-010. Faltante: 1
OK Builder: validación de campos obligatorios.
Resumen stock no vencido: {B-001=6, F-010=6}
=== FIN DEMO ===

📌 Patrones implementados
🔒 Singleton – GestorInventario

Única instancia para administrar el inventario.

Aplica PEPS (FIFO) en consumo de lotes.

Ignora lotes vencidos.

Lanza excepciones controladas si no hay stock suficiente.

🛠️ Builder

Construcción paso a paso de productos.

Valida campos obligatorios (id, name, unit, price).

Permite atributos opcionales (atributos como temperatura, presentación, etc.).

Ejemplo:

Bebida cola = new BebidaBuilder()
    .id("B-001")
    .name("Cola 350ml")
    .price(3500)
    .unit("botella")
    .attribute("azucar", "media")
    .build();

🧬 Prototype

Los productos (Producto) implementan clone().

El clon genera un nuevo mapa de atributos, garantizando independencia (deep copy).

🏭 Abstract Factory

Fábricas concretas para cada categoría (FabricaBebidas, FabricaFrescos, etc.).

Centralizadas en CategoriaFactoryProvider.

Permiten crear familias de productos con atributos consistentes.

Ejemplo:

AbstractCategoriaFactory fabrica = CategoriaFactoryProvider.getFactory(Categoria.BEBIDAS);
Producto agua = fabrica.crearProducto("B-002", "Agua 600ml", 2200, "botella");

✅ Pruebas de integración (incluidas en main)

PEPS: consumir 6 unidades de gaseosa (5 del lote más antiguo + 1 del siguiente).

Ignorar vencidos: el stock de lechugas cuenta solo las vigentes.

Prototype: clonación de productos no comparte atributos con el original.

Builder: falla si falta un campo obligatorio.

Resumen de stock: muestra inventario total no vencido por producto.
