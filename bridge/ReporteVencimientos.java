
package inventario.burgerhouse.bridge;

/**
 *
 * @author Mauricio Martinez, Karen Segura
 */

import inventario.burgerhouse.domain.Lote;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class ReporteVencimientos extends Reporte {

    //- Días para considerar un producto "por vencer"
    private int diasUmbral = 30;

    public ReporteVencimientos(FormatoReporte formato) {
        super(formato);
    }

    public ReporteVencimientos(FormatoReporte formato, int diasUmbral) {
        super(formato);
        setDiasUmbral(diasUmbral);
    }

    @Override
    protected String getTitulo() {
        return "Reporte de productos por vencer";
    }

    @Override
    protected void agregarContenido() {
        //- Encabezado y parámetros del reporte
        formato.agregarEncabezado("Productos proximos a vencer");
        formato.agregarFila("umbral de alerta", diasUmbral + " días");
        formato.agregarSeparador();

        Map<String, Integer> stockTotal = gestor.consultar_stock_total();
        List<Map<String, Object>> alertas = new ArrayList<>();

        //- Recorre productos y sus lotes activos
        for (String productId : stockTotal.keySet()) {
            List<Lote> lotes = gestor.consultar_lotes_activos(productId);
            for (Lote lote : lotes) {
                if (lote.getDate_exp() == null) continue;

                long dias = ChronoUnit.DAYS.between(LocalDate.now(), lote.getDate_exp());
                if (dias < 0 || dias > diasUmbral) continue;

                //- Construye la alerta con metadatos de urgencia
                Map<String, Object> info = new LinkedHashMap<>();
                info.put("Producto ID", productId);
                info.put("Lote ID", lote.getId());
                info.put("Cantidad", lote.getAmount());
                info.put("Fecha Vencimiento", String.valueOf(lote.getDate_exp()));
                info.put("Días Restantes", dias);
                info.put("Urgencia", dias <= 3 ? "CRITICO" : (dias <= 7 ? "URGENTE" : "NORMAL"));
                alertas.add(info);
            }
        }

        //- Sin resultados dentro del umbral
        if (alertas.isEmpty()) {
            formato.agregarFila("Estado", "No hay productos proximos a vencer en los próximos " + diasUmbral + " días");
            return;
        }

        //- Ordena por días restantes y lista cada alerta como tabla
        formato.agregarFila("Total alertas", String.valueOf(alertas.size()));
        formato.agregarSeparador();

        alertas.sort(Comparator.comparingLong(a -> {Object v = a.get("Días Restantes");
             if (!(v instanceof Number)) return Long.MAX_VALUE;
             return ((Number) v).longValue();
        })); 
        int i = 1;
        for (Map<String, Object> a : alertas) {
            formato.agregarTabla("Alerta #" + (i++), a);
        }
    }

    public void setDiasUmbral(int diasUmbral) {
        //- Validación del parámetro de configuración
        if (diasUmbral < 0) throw new IllegalArgumentException("El umbral debe ser positivo");
        this.diasUmbral = diasUmbral;
    }
}