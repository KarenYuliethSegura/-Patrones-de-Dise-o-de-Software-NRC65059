# 🍔 Inventario Burgerhouse

Sistema de gestión de inventario para hamburguesería desarrollado en Java, implementando múltiples patrones de diseño de software como proyecto académico.

---

## 📑 Tabla de Contenidos

- [Características](#-características)
- [Patrones de Diseño](#-patrones-de-diseño-implementados)
- [Estructura del Proyecto](#-estructura-del-proyecto)
- [Requisitos](#-requisitos)
- [Instalación y Ejecución](#-instalación-y-ejecución)
- [Uso](#-uso)
- [Pruebas](#-pruebas)

---

## ✨ Características

- **Gestión centralizada de inventario** con instancia única (Singleton)
- **Política PEPS (FIFO)** para consumo de lotes
- **Control automático de vencimientos** de productos
- **Sistema de alertas** configurable con observadores
- **Construcción flexible** de productos con validación
- **Reportes en múltiples formatos** (Consola, JSON)
- **Integración con proveedores externos** mediante adaptadores
- **Historial de operaciones** con soporte para deshacer acciones

---

## 🎯 Patrones de Diseño Implementados

### Patrones Creacionales

#### 🔹 Singleton
**Clase:** `GestorInventario`
- Garantiza una única instancia global del gestor de inventario
- Implementa política PEPS (FIFO) en el consumo de lotes
- Ignora automáticamente lotes vencidos
- Lanza excepciones controladas ante stock insuficiente

#### 🔹 Builder
**Clases:** `BebidaBuilder`, `ProductoFrescoBuilder`, `ProductoConservaBuilder`, `ProductoPanaderiaBuilder`
- Construcción paso a paso de productos
- Validación de campos obligatorios
- Soporte para atributos opcionales (temperatura, presentación, etc.)

**Ejemplo de uso:**
```java
Bebida cola = new BebidaBuilder()
    .id("B-001")
    .name("Cola 350ml")
    .price(3500)
    .unit("botella")
    .attribute("azucar", "media")
    .build();
```

#### 🔹 Prototype
**Clase:** `Producto`
- Implementa `clone()` para crear copias independientes
- Deep copy de atributos del producto
- Permite duplicar productos sin afectar los originales

#### 🔹 Abstract Factory
**Clases:** `AbstractCategoriaFactory`, `CategoriaFactoryProvider`
- Crea familias de productos por categoría (Bebidas, Frescos, Conservas, Panadería)
- Garantiza consistencia en la creación de productos relacionados

**Ejemplo de uso:**
```java
AbstractCategoriaFactory fabrica = CategoriaFactoryProvider.getFactory(Categoria.BEBIDAS);
Producto agua = fabrica.crearProducto("B-002", "Agua 600ml", 2200, "botella");
```

---

### Patrones Estructurales

#### 🔹 Adapter
**Clases:** `CarnesPremiumAdapter`, `VerdurasFrescasAdapter`
- Integra proveedores externos con formatos distintos
- Convierte interfaces incompatibles a `ProveedorStandard`

#### 🔹 Bridge
**Clases:** `FormatoConsola`, `FormatoJSON`, `FormatoReporte`
- Separa la lógica de formato de salida del contenido del reporte
- Permite cambiar el formato sin modificar los reportes

#### 🔹 Decorator
**Clases:** `ExtraQueso`, `ExtraTocineta`, `ExtraJalapenos`
- Añade ingredientes extra a productos dinámicamente
- No modifica la estructura base del producto

#### 🔹 Facade
**Clase:** `SistemaInventarioFacade`
- Simplifica la interacción con múltiples subsistemas
- Ofrece una interfaz unificada para gestión, reportes y políticas

---

### Patrones de Comportamiento

#### 🔹 Command
**Clases:** `EntradaCommand`, `SalidaCommand`, `InventarioCommand`
- Encapsula operaciones de inventario como objetos
- Facilita deshacer operaciones
- Mantiene historial de acciones ejecutadas

#### 🔹 Observer
**Clases:** `AlertaInventarioSubject`, `AlertaConsolaObserver`, `AlertaCapturadoraObserver`
- Sistema de alertas de inventario
- Notificación automática a observadores ante cambios
- Desacoplamiento entre sujeto y observadores

#### 🔹 Strategy
**Clases:** `PoliticaPEPS`, `PoliticaFEFO`, `PoliticaConsumo`
- Selección dinámica de política de consumo de lotes
- Intercambio de algoritmos en tiempo de ejecución

---

## 📁 Estructura del Proyecto

```
inventario.burgerhouse/
│
├── adapter/                    # Integración con proveedores externos
│   ├── CarnesPremiumAdapter.java
│   ├── VerdurasFrescasAdapter.java
│   ├── ProveedorStandard.java
│   └── ProveedorCarnesPremium.java
│
├── bridge/                     # Formatos de reportes
│   ├── FormatoConsola.java
│   ├── FormatoJSON.java
│   ├── FormatoReporte.java
│   ├── ReporteInventario.java
│   └── ReporteVencimientos.java
│
├── builder/                    # Construcción de productos
│   ├── ProductoBuilder.java
│   ├── BebidaBuilder.java
│   ├── ProductoFrescoBuilder.java
│   ├── ProductoConservaBuilder.java
│   └── ProductoPanaderiaBuilder.java
│
├── command/                    # Operaciones de inventario
│   ├── EntradaCommand.java
│   ├── SalidaCommand.java
│   ├── InventarioCommand.java
│   └── OperadorInventario.java
│
├── decorator/                  # Ingredientes extra
│   ├── ProductoDecorator.java
│   ├── ExtraQueso.java
│   ├── ExtraTocineta.java
│   └── ExtraJalapenos.java
│
├── domain/                     # Modelos de dominio
│   ├── Producto.java
│   ├── Bebida.java
│   ├── ProductoFresco.java
│   ├── ProductoConserva.java
│   ├── ProductoPanaderia.java
│   ├── Lote.java
│   └── Categoria.java
│
├── factory/                    # Fábricas de productos
│   ├── AbstractCategoriaFactory.java
│   ├── FabricaBebidas.java
│   ├── FabricaFrescos.java
│   ├── FabricaConservas.java
│   ├── FabricaPanaderia.java
│   └── CategoriaFactoryProvider.java
│
├── observer/                   # Sistema de alertas
│   ├── AlertaInventarioSubject.java
│   ├── AlertaInventarioObserver.java
│   ├── AlertaConsolaObserver.java
│   └── AlertaCapturadoraObserver.java
│
├── strategy/                   # Políticas de consumo
│   ├── SelectorLotes.java
│   ├── PoliticaPEPS.java
│   ├── PoliticaFEFO.java
│   └── PoliticaConsumo.java
│
├── singleton/                  # Gestión centralizada
│   ├── GestorInventario.java
│   ├── AuditorInventario.java
│   ├── ProductoNoEncontradoException.java
│   └── StockInsuficienteException.java
│
├── facade/                     # Interfaz simplificada
│   └── SistemaInventarioFacade.java
│
└── main/                       # Punto de entrada
    └── InventarioBurgerhouse.java
```

---

## 🔧 Requisitos

- **Java 17** o superior (recomendado)
- IDE compatible: NetBeans, IntelliJ IDEA, Eclipse o VS Code
- Alternativamente: `javac` y `java` desde línea de comandos

---

## 🚀 Instalación y Ejecución

### Compilación desde consola

```bash
# Compilar todos los archivos Java
javac -d out $(find src -name "*.java")

# Ejecutar el programa
java -cp out inventario.burgerhouse.main.InventarioBurgerhouse
```

### Ejecución desde IDE

1. Importar el proyecto en tu IDE preferido
2. Configurar JDK 17 o superior
3. Ejecutar la clase `InventarioBurgerhouse.java`

---

## 💡 Uso

### Ejemplo básico

```java
// Obtener instancia única del gestor
GestorInventario gestor = GestorInventario.getInstance();

// Crear producto usando Builder
Bebida cola = new BebidaBuilder()
    .id("B-001")
    .name("Cola 350ml")
    .price(3500)
    .unit("botella")
    .build();

// Registrar entrada de lote
gestor.registrarEntrada("B-001", cantidad, fechaVencimiento);

// Consumir stock (aplica PEPS automáticamente)
gestor.consumirStock("B-001", cantidad);

// Consultar stock disponible
int stockDisponible = gestor.consultarStock("B-001");
```

### Uso de Facade

```java
// Interfaz simplificada del sistema
SistemaInventarioFacade sistema = new SistemaInventarioFacade();
sistema.registrarProducto(producto);
sistema.registrarEntrada(idProducto, cantidad, fechaVencimiento);
sistema.generarReporte("JSON");
```

---

## 🧪 Pruebas

El programa incluye casos de prueba integrados en el método `main`:

### Salida esperada

```yaml
=== DEMO Burgerhouse ===

✓ Stock Gaseosa (B-001) inicial: 12
✓ Stock Lechuga (F-010) inicial: 6
✓ Lotes activos B-001 (post PEPS): [L2]
✓ Stock insuficiente (no vencido) para F-010. Faltante: 1
✓ Builder: validación de campos obligatorios
✓ Resumen stock no vencido: {B-001=6, F-010=6}

=== FIN DEMO ===
```

### Casos de prueba cubiertos

| Prueba | Descripción |
|--------|-------------|
| **PEPS** | Consumo de 6 unidades de gaseosa (5 del lote más antiguo + 1 del siguiente) |
| **Vencimientos** | El stock de lechugas cuenta solo los lotes vigentes |
| **Prototype** | Clonación de productos crea copias independientes |
| **Builder** | Validación de campos obligatorios (falla si falta alguno) |
| **Resumen** | Muestra inventario total no vencido por producto |

---

## 📝 Licencia

Proyecto académico desarrollado con fines educativos.

---

## 👥 Autores

Desarrollado como proyecto de patrones de diseño en Java.

---

## 🤝 Contribuciones

Este es un proyecto académico. Las sugerencias y mejoras son bienvenidas a través de issues o pull requests.
