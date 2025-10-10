package inventario.burgerhouse.facade;

/**
 *
 * @author dct-yulieth
 */

import inventario.burgerhouse.singleton.GestorInventario;
import inventario.burgerhouse.singleton.StockInsuficienteException;
import inventario.burgerhouse.singleton.ProductoNoEncontradoException;

import inventario.burgerhouse.builder.*;
import inventario.burgerhouse.factory.*;
import inventario.burgerhouse.domain.*;
import inventario.burgerhouse.observer.*;
import inventario.burgerhouse.adapter.*;
import inventario.burgerhouse.decorator.*;
import inventario.burgerhouse.bridge.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class SistemaInventarioFacade {

    //- Subsistemas principales
    private final GestorInventario gestorInventario;
    private final AlertaInventarioSubject alertaSubject;
    private final AuditorInventario auditor;
    private final ImportadorProductos importador;
    private final AlertaCapturadoraObserver capturadorAlertas;

    //- Configuracion por defecto
    private static final int UMBRAL_STOCK_BAJO = 10;
    private static final int UMBRAL_DIAS_VENCER = 7;

    public SistemaInventarioFacade() {
        gestorInventario = GestorInventario.getInstance();
        alertaSubject = new AlertaInventarioSubject();
        alertaSubject.subscribe(new AlertaConsolaObserver());
        capturadorAlertas = new AlertaCapturadoraObserver();
        alertaSubject.subscribe(capturadorAlertas);
        auditor = new AuditorInventario(alertaSubject, UMBRAL_STOCK_BAJO, UMBRAL_DIAS_VENCER);
        importador = new ImportadorProductos();
    }

    //- Registra un producto nuevo con Factory y Singleton
    public void registrarProductoNuevo(String id, String nombre, double precio,
                                       String unidad, Categoria categoria,
                                       int cantidad, LocalDate fechaVencimiento) {
        try {
            AbstractCategoriaFactory factory = CategoriaFactoryProvider.getFactory(categoria);
            Producto producto = factory.crearProducto(id, nombre, precio, unidad);
            String loteId = "L-" + (System.currentTimeMillis() % 10000);
            Lote lote = new Lote(loteId, producto.getId(), LocalDate.now(), fechaVencimiento, cantidad);
            gestorInventario.registrar_entrada(lote);
            System.out.println("Producto registrado:" + producto.getName() + " (" + cantidad + " unidades)");
        } catch (Exception e) {
            System.out.println("Error al registrar producto:" + e.getMessage());
        }
    }

    //- Registra un producto fresco con Builder y atributos opcionales
    public void registrarProductoFrescoConDetalles(String id, String nombre, double precio,
                                                   String unidad, int cantidad,
                                                   LocalDate fechaVencimiento,
                                                   Map<String, String> atributos) {
        try {
            ProductoFrescoBuilder builder = new ProductoFrescoBuilder()
                    .id(id)
                    .name(nombre)
                    .price(precio)
                    .unit(unidad);
            if (atributos != null && !atributos.isEmpty()) builder.attributes(atributos);
            Producto producto = builder.build();
            String loteId = "LF-" + (System.currentTimeMillis() % 10000);
            Lote lote = new Lote(loteId, producto.getId(), LocalDate.now(), fechaVencimiento, cantidad);
            gestorInventario.registrar_entrada(lote);
            System.out.println("Producto fresco registrado: " + producto.getName());
        } catch (Exception e) {
            System.out.println("Error al registrar producto fresco: " + e.getMessage());
        }
    }

    //- Clona un producto existente (Prototype)
    public Producto clonarProducto(Producto original, String nuevoId, String nuevoNombre) {
        Producto clonado = original.clone();
        System.out.println("Producto clonado: " + original.getName() + " → " + nuevoNombre);
        return clonado;
    }

    //- Aplica extras a un producto (Decorator)
    public Producto agregarExtras(Producto base, boolean extraQueso,
                                  boolean extraTocineta, boolean extraJalapenos) {
        Producto resultado = base;
        if (extraQueso) resultado = new ExtraQueso(resultado, 1000);
        if (extraTocineta) resultado = new ExtraTocineta(resultado, 1500);
        if (extraJalapenos) resultado = new ExtraJalapenos(resultado, 800);
        System.out.println("Extras agregados. Precio final: $" + resultado.getPrice());
        return resultado;
    }

    //- Registra una salida de producto (PEPS)
    public boolean registrarConsumo(String productoId, int cantidad) {
        try {
            gestorInventario.registrar_salida(productoId, cantidad);
            System.out.println("Consumo registrado: " + cantidad + " unidades de " + productoId);
            return true;
        } catch (ProductoNoEncontradoException | StockInsuficienteException e) {
            System.out.println("Error en consumo: " + e.getMessage());
            return false;
        }
    }

    //- Importa productos desde proveedor de carnes
    public void importarDesdeProveedorCarnes(ProveedorCarnesPremium proveedorExterno) {
        IProveedorStandard adaptado = new CarnesPremiumAdapter(proveedorExterno);
        importador.importarProducto(adaptado);
        System.out.println("Importacion desde proveedor de carnes completada");
    }

    //- Importa productos desde proveedor de verduras
    public void importarDesdeProveedorVerduras(ProveedorVerdurasFrescas proveedorExterno) {
        IProveedorStandard adaptado = new VerdurasFrescasAdapter(proveedorExterno);
        importador.importarProducto(adaptado);
        System.out.println("Importacion desde proveedor de verduras completada");
    }

    //- Importa varios productos de diferentes proveedores
    public void importarLoteProveedores(List<IProveedorStandard> proveedores) {
        importador.importarLote(proveedores);
        System.out.println("Importacion masiva completada: " + proveedores.size() + " productos");
    }

    //- Verifica alertas de stock bajo y vencimientos
    public void verificarAlertas() {
        System.out.println("Verificando alertas del sistema...");
        capturadorAlertas.reset();
        Map<String, Integer> stockTotal = gestorInventario.consultar_stock_total();
        for (String productId : stockTotal.keySet()) auditor.evaluar(productId);
        int totalAlertas = capturadorAlertas.getTotalEventos();
        if (totalAlertas == 0) {
            System.out.println("No se encontraron alertas.");
        } else {
            System.out.println("Total de alertas detectadas: " + totalAlertas);
            System.out.println("Stock bajo: " + capturadorAlertas.getLowStockCount());
            System.out.println("Por vencer: " + capturadorAlertas.getExpiringCount());
        }
    }

    //- Devuelve lista de alertas registradas
    public List<String> obtenerAlertas() {
        return capturadorAlertas.getEventos();
    }

    //- Genera reporte de inventario en consola
    public void generarReporteInventario() {
        Reporte reporte = new ReporteInventario(new FormatoConsola());
        reporte.generar();
    }

    //- Genera reporte de inventario en formato específico
    public void generarReporteInventarioEnFormato(String formato) {
        FormatoReporte formatoReporte = obtenerFormato(formato);
        Reporte reporte = new ReporteInventario(formatoReporte);
        reporte.generar();
    }

    //- Genera reporte de productos por vencer
    public void generarReporteVencimientos(int diasUmbral) {
        Reporte reporte = new ReporteVencimientos(new FormatoConsola(), diasUmbral);
        reporte.generar();
    }

    //- Genera reporte de vencimientos en formato específico
    public void generarReporteVencimientosEnFormato(String formato, int diasUmbral) {
        FormatoReporte formatoReporte = obtenerFormato(formato);
        Reporte reporte = new ReporteVencimientos(formatoReporte, diasUmbral);
        reporte.generar();
    }

    //- Genera todos los reportes del sistema
    public void generarTodosLosReportes() {
        System.out.println("Generando todos los reportes del sistema...");
        generarReporteInventario();
        generarReporteVencimientos(30);
    }

    //- Consulta el stock de un producto
    public int consultarStock(String productoId) {
        return gestorInventario.consultar_stock(productoId);
    }

    //- Obtiene resumen de inventario
    public Map<String, Integer> obtenerResumenInventario() {
        return gestorInventario.consultar_stock_total();
    }

    //- Consulta lotes activos de un producto
    public List<Lote> consultarLotesActivos(String productoId) {
        return gestorInventario.consultar_lotes_activos(productoId);
    }

    //- Verifica si hay suficiente stock disponible
    public boolean tieneStockDisponible(String productoId, int cantidadRequerida) {
        return consultarStock(productoId) >= cantidadRequerida;
    }

    //- Ejecuta flujo completo
    public void procesarImportacionCompleta(List<IProveedorStandard> proveedores) {
        System.out.println("Iniciando proceso completo de importacion...");
        importarLoteProveedores(proveedores);
        verificarAlertas();
        System.out.println("Generando reporte posterior a la importacion...");
        generarReporteInventario();
        System.out.println("Proceso completo finalizado");
    }

    //- Ejecuta cierre diario automatico
    public void ejecutarCierreDiario() {
        System.out.println("Ejecutando cierre diario del sistema...");
        System.out.println("Verificando alertas...");
        verificarAlertas();
        System.out.println("Generando reportes del dia...");
        generarTodosLosReportes();
        System.out.println("Cierre diario completado");
    }

    //- Limpia inventario y alertas (para pruebas)
    public void limpiarInventario() {
        gestorInventario.limpiar();
        capturadorAlertas.reset();
        System.out.println("Inventario limpiado");
    }

    //- Obtiene formato segun nombre
    private FormatoReporte obtenerFormato(String formato) {
        if (formato == null) return new FormatoConsola();
        switch (formato.toLowerCase()) {
            case "json":
                return new FormatoJSON();
            default:
                return new FormatoConsola();
        }
    }

    //- Imprime estadisticas generales
    public void imprimirEstadisticas() {
        Map<String, Integer> stock = obtenerResumenInventario();
        int totalProductos = stock.size();
        int totalUnidades = stock.values().stream().mapToInt(Integer::intValue).sum();
        System.out.println("Estadisticas del Sistema:");
        System.out.println("Productos diferentes: " + totalProductos);
        System.out.println("Unidades totales: " + totalUnidades);
        System.out.println("Alertas activas: " + capturadorAlertas.getTotalEventos());
    }
}