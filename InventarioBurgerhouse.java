package inventario.burgerhouse.main;

/**
 *
 * @author yulieth segura
 */

import inventario.burgerhouse.domain.*;
import inventario.burgerhouse.builder.*;
import inventario.burgerhouse.factory.*;
import inventario.burgerhouse.observer.*;
import inventario.burgerhouse.decorator.*;
import inventario.burgerhouse.adapter.*;
import inventario.burgerhouse.singleton.GestorInventario;
import inventario.burgerhouse.singleton.StockInsuficienteException;

import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;
import java.util.List;
import java.util.ArrayList;

public class InventarioBurgerhouse {

    public static void main(String[] args) {
        System.out.println("Inventario Burgerhouse");

        //- Se crean productos usando Builder
        Bebida gaseosa = new BebidaBuilder()
            .id("B-001")
            .name("Gaseosa 350ml")
            .price(3500)
            .unit("botella")
            .attribute("azucar", "media")
            .build();
        
        ProductoFresco lechuga = new ProductoFrescoBuilder()
            .id("F-010")
            .name("Lechuga romana")
            .price(1800)
            .unit("pieza")
            .attribute("origen", "Local")
            .build();
        
        ProductoFresco carneRes = new ProductoFrescoBuilder()
            .id("F-100")
            .name("Carne de res 250g")
            .price(8000)
            .unit("unidad")
            .attribute("proteína", "res")
            .build();
  
        //- Se crean productos con Factory apartir de los builders internos
        AbstractCategoriaFactory fabBebidas = CategoriaFactoryProvider.getFactory(Categoria.BEBIDAS);
        Producto agua = fabBebidas.crearProducto("B-002", "Agua 600ml", 2200, "botella");

        //- Clonado y verificacion de atributos
        Bebida gaseosaClon = gaseosa.clone();
        gaseosa.getAttributes().put("temperatura", "fría");
        gaseosaClon.getAttributes().put("temperatura", "ambiente");
        
        assertTrue(
            !Objects.equals(
                gaseosa.getAttributes().get("temperatura"),
                gaseosaClon.getAttributes().get("temperatura")
            ),
            "Prototype: los atributos deben ser independientes (deep copy)."
        );
        
        //- Registro de lotes y consumo segun PEPS
       GestorInventario gi = GestorInventario.getInstance();

        gi.registrar_entrada(new Lote("L1", "B-001",
                LocalDate.now().minusMonths(8), LocalDate.now().plusMonths(6), 5));
        gi.registrar_entrada(new Lote("L2", "B-001",
               LocalDate.now().minusMonths(6), LocalDate.now().plusMonths(8), 7));

        gi.registrar_entrada(new Lote("LF1", "F-010",
                LocalDate.now().minusMonths(7), LocalDate.now().minusMonths(1), 10)); // vencido
        gi.registrar_entrada(new Lote("LF2", "F-010",
                LocalDate.now().minusMonths(1), LocalDate.now().plusMonths(3), 6));  // vigente

        // Stock inicial (no vencido)
        System.out.println("Stock Gaseosa (B-001) inicial: " + gi.consultar_stock("B-001"));
        System.out.println("Stock Lechuga (F-010) inicial: " + gi.consultar_stock("F-010"));
        
        //- TEST PEPS: consumir 6 gaseosas debe agotar primero L1 (5) y tomar 1 de L2
        try {
            gi.registrar_salida("B-001", 6);
        } catch (Exception e) {
            fail("No debería fallar consumo PEPS en B-001: " + e.getMessage());
        }
        
        //- Validacion estado de lotes despues de consumir
        int stockGaseosa = gi.consultar_stock("B-001");
        assertTrue(stockGaseosa == (5 + 7 - 6), "PEPS: stock esperado 6 en B-001");
        
        //- Muestra lotes 
        System.out.println("Lotes activos B-001 (post PEPS): " + gi.consultar_lotes_activos("B-001").stream().map(Lote::getId).toList());
        
        //- robar que lotes vencidos no cuentan para stock ni consumo
        int stockLechuga = gi.consultar_stock("F-010");
        assertTrue(stockLechuga == 6, "Stock de F-010 debe ignorar lote vencido (quedar 6)");
        
        try {
            gi.registrar_salida("F-010", 7);
            fail("Debe lanzar StockInsuficienteException (solo hay 6 no vencidos).");
        } catch (StockInsuficienteException ok) {
            System.out.println("OK esperado: " + ok.getMessage());
        } catch (Exception e) {
            fail("Esperábamos StockInsuficienteException, pero llegó: " + e);
        }
        
        // Alertamiento por stock bajo 
        System.out.println("Prueba stock bajo");
        gi.registrar_entrada(new Lote("LF3", "F-010", LocalDate.now(), LocalDate.now().plusDays(2), 1));
        
        AlertaInventarioSubject subject = new AlertaInventarioSubject();
        subject.subscribe(new AlertaConsolaObserver());
        AlertaCapturadoraObserver capturador = new AlertaCapturadoraObserver();
        subject.subscribe(capturador);
        
        AuditorInventario auditor = new AuditorInventario(subject, 7, 7);
        
        auditor.evaluar("B-001"); 
        auditor.evaluar("F-010");
        
        assertTrue(capturador.getTotalEventos() >= 2, "Observer: se esperaban >=2 alertas (low stock y/o expiring).");
        System.out.println("Observer OK. Eventos capturados: " + capturador.getEventos());
        
        //- Extra ingredientes decorator 
        
        Producto burgerBase = carneRes; 
        Producto conTocineta = new ExtraTocineta(burgerBase, 1500.0);
        Producto conTocYQueso = new ExtraQueso(conTocineta, 1000.0);

        System.out.println("Precio base: " + burgerBase.getPrice());
        System.out.println("Con tocineta: " + conTocineta.getPrice());
        System.out.println("Con tocineta + queso: " + conTocYQueso.getPrice());
        System.out.println("Atributos: " + conTocYQueso.getAttributes());
        
        //- Importacion de productos desde proveedores externos (Adapter) 
        //- Proveedor 1: Carnes Premium (formato propio incompatible)
        ProveedorCarnesPremium carnesExterno = new ProveedorCarnesPremium(
            "CP-200",
            "Carne de res molida premium 500g",
            9500,
            "kg",
            25,
            "15/03/2026"  //- Formato del proveedor: dd/MM/yyyy
        );

        //- Proveedor 2: Verduras Frescas (formato JSON-style incompatible)
        ProveedorVerdurasFrescas verduraExterno = new ProveedorVerdurasFrescas(
            "VF-050",
            "Lechuga crespa orgánica",
            2800,
            "unit",
            40,
            "2025/10/20"  //- Formato del proveedor: yyyy/MM/dd
        );

        //- Crear adaptadores para sistemas externos
        IProveedorStandard carnesAdaptado = new CarnesPremiumAdapter(carnesExterno);
        IProveedorStandard verdurasAdaptado = new VerdurasFrescasAdapter(verduraExterno);

        //- Verificar que los adaptadores convierten correctamente los formatos
        System.out.println("\n1. Verificación de Adaptación de Formatos:");
        System.out.println(" Carnes: ID Original: CP-200 / ID adaptado: " + carnesAdaptado.getProductId());
        System.out.println(" Carnes: Fecha Original: 15/03/2026 / Fecha adaptada: " + carnesAdaptado.getExpirationDate());
        System.out.println(" Verduras: SKU Original: VF-050 / SKU adaptad:" + verdurasAdaptado.getProductId());
        System.out.println(" Verduras: Fecha Original: 2025/10/20 / Fecha adaptada: " + verdurasAdaptado.getExpirationDate());

        assertTrue(
            carnesAdaptado.getProductId().equals("F-200"),
            "Adapter debe convertir CP-200 a F-200"
        );

        assertTrue(
            carnesAdaptado.getExpirationDate().equals("2026-03-15"),
            "Adapter debe convertir dd/MM/yyyy a yyyy-MM-dd"
        );

        assertTrue(
            verdurasAdaptado.getProductId().equals("F-050"),
            "Adapter debe convertir VF-050 a F-050"
        );

        assertTrue(
            verdurasAdaptado.getExpirationDate().equals("2025-10-20"),
            "Adapter debe convertir yyyy/MM/dd a yyyy-MM-dd"
        );

        //- Usar el importador para agregar productos al inventario
        System.out.println("\n2. Importación al Inventario:");
        ImportadorProductos importador = new ImportadorProductos();

        List<IProveedorStandard> proveedores = new ArrayList<>();
        proveedores.add(carnesAdaptado);
        proveedores.add(verdurasAdaptado);

        importador.importarLote(proveedores);
        importador.imprimirLog();

        //- Verificar que los productos fueron importados correctamente
        int stockCarnes = gi.consultar_stock("F-200");
        int stockVerduras = gi.consultar_stock("F-050");

        System.out.println("\n3. Verificación de Stock Post-Importación:");
        System.out.println("Stock Carne (F-200): " + stockCarnes);
        System.out.println("Stock Lechuga (F-050): " + stockVerduras);

        assertTrue(
            stockCarnes == 25,
            "Stock de carnes debe ser 25 unidades"
        );

        assertTrue(
            stockVerduras == 40,
            "Stock de verduras debe ser 40 unidades"
        );

        //- Verificar que diferentes adaptadores no interfieren entre sí
        System.out.println("\n4. Independencia de Adaptadores:");
        IProveedorStandard carnes2 = new CarnesPremiumAdapter(
            new ProveedorCarnesPremium("CP-201", "Carne cerdo", 8000, "kg", 15, "01/04/2026")
        );

        importador.importarProducto(carnes2);

        int stockCarnes2 = gi.consultar_stock("F-201");
        assertTrue(
            stockCarnes2 == 15 && stockCarnes == 25,
            "Los multiples adaptadores deben funcionar independientemente"
        );

        System.out.println("Adaptadores funcionan independientemente");
        System.out.println("Stock F-200: " + stockCarnes + " | Stock F-201: " + stockCarnes2);
        System.out.println("OK: Integracion exitosa de sistemas externos");

        //- Resumen del stock 
        Map<String, Integer> resumen = gi.consultar_stock_total();
        System.out.println("Resumen stock no vencido: " + resumen);  
        System.out.println("Final del inventario");

        //- TEST builder debe fallar
        try {
            new BebidaBuilder()
                    .id(null)
                    .name("Refresco X")
                    .price(1000)
                    .unit("botella")
                    .build();
            fail("El Builder debería lanzar IllegalArgumentException si falta 'id'.");
        } catch (IllegalArgumentException ok) {
            System.out.println("OK Builder: validación de campos obligatorios.");
        }
        
    }
    
    private static void assertTrue(boolean cond, String msg) {
        if (!cond) {
            throw new AssertionError("ASSERT FAIL: " + msg);
        }
    }

    private static void fail(String msg) {
        throw new AssertionError("ASSERT FAIL: " + msg);
    }
    
}
