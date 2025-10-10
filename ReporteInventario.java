
package inventario.burgerhouse.bridge;

/**
 *
 * @author Mauricio Martinez, Karen Segura
 */

import java.util.LinkedHashMap;
import java.util.Map;

public class ReporteInventario extends Reporte {

    public ReporteInventario(FormatoReporte formato) {
        super(formato);
    }

    @Override
    protected String getTitulo() {
        return "Reporte de Inventario";
    }

    @Override
    protected void agregarContenido() {
        //- Resumen general
        formato.agregarEncabezado("Resumen de Stock");

        Map<String, Integer> stock = gestor.consultar_stock_total();
        if (stock.isEmpty()) {
            formato.agregarFila("Estado", "Sin productos en inventario");
            return;
        }

        int totalUnidades = stock.values().stream().mapToInt(Integer::intValue).sum();
        formato.agregarFila("Total Productos Diferentes", String.valueOf(stock.size()));
        formato.agregarFila("Total Unidades", String.valueOf(totalUnidades));
        formato.agregarSeparador();

        //- Detalle por producto
        formato.agregarEncabezado("Detalle de Stock por Producto");
        Map<String, Object> detalle = new LinkedHashMap<>();
        stock.forEach((k, v) -> detalle.put(k, v + " unidades"));
        formato.agregarTabla("Stock Disponible", detalle);
    }
}
