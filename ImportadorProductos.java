
package inventario.burgerhouse.adapter;

/**
 *
 * @author Mauricio Martinez, Karen Segura
 */

import inventario.burgerhouse.builder.ProductoFrescoBuilder;
import inventario.burgerhouse.domain.Lote;
import inventario.burgerhouse.domain.Producto;
import inventario.burgerhouse.singleton.GestorInventario;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ImportadorProductos {
    
    private final GestorInventario gestor;
    private final List<String> logImportacion;
    
    public ImportadorProductos() {
        this.gestor = GestorInventario.getInstance();
        this.logImportacion = new ArrayList<>();
    }
    
    //- Importa productor de cualquier proveedor que se encuentre en IproveedorStandard
    public void importarProducto(IProveedorStandard proveedor) {
        try {
            // Crea el producto usando el Builder
            Producto producto = new ProductoFrescoBuilder()
                    .id(proveedor.getProductId())
                    .name(proveedor.getProductName())
                    .price(proveedor.getProductPrice())
                    .unit(proveedor.getProductUnit())
                    .attribute("proveedor", proveedor.getProviderName())
                    .build();
            
            // Crea el lote con los datos del proveedor
            LocalDate fechaIngreso = LocalDate.now();
            LocalDate fechaVencimiento = LocalDate.parse(
                proveedor.getExpirationDate(), 
                DateTimeFormatter.ISO_LOCAL_DATE
            );
            
            String loteId = generarLoteId(proveedor);
            
            Lote lote = new Lote(
                loteId,
                proveedor.getProductId(),
                fechaIngreso,
                fechaVencimiento,
                proveedor.getProductQuantity()
            );
            
            // Registra en el gestor de inventario
            gestor.registrar_entrada(lote);
            
            // Log exitoso
            String mensaje = String.format(
                "Importado: %s | Proveedor: %s | Cantidad: %d | Vence: %s",
                producto.getName(),
                proveedor.getProviderName(),
                proveedor.getProductQuantity(),
                fechaVencimiento
            );
            logImportacion.add(mensaje);
            
        } catch (Exception e) {
            String error = String.format(
                "Error importando producto %s: %s",
                proveedor.getProductId(),
                e.getMessage()
            );
            logImportacion.add(error);
        }
    }
    
    //- Importa múltiples productos de una lista de proveedores
    public void importarLote(List<IProveedorStandard> proveedores) {
        logImportacion.clear();
        logImportacion.add("Inicio de importacion");
        
        for (IProveedorStandard proveedor : proveedores) {
            importarProducto(proveedor);
        }
        
        logImportacion.add("Fin de importacion");
    }
    
    //- Obtiene el log de la última importación
    public List<String> getLogImportacion() {
        return new ArrayList<>(logImportacion);
    }
    
    //- Genera un ID único para el lote basado en el proveedor y fecha
    private String generarLoteId(IProveedorStandard proveedor) {
        String prefijo = proveedor.getProviderName()
                .substring(0, 2)
                .toUpperCase();
        String timestamp = String.valueOf(System.currentTimeMillis() % 10000);
        return prefijo + "-" + timestamp;
    }
    
    //- Imprime el log de importación
    public void imprimirLog() {
        for (String linea : logImportacion) {
            System.out.println(linea);
        }
    }
}
