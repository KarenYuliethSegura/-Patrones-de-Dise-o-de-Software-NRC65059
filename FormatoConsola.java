
package inventario.burgerhouse.bridge;

/**
 *
 * @author Mauricio Martinez, Karen Segura
 */

import java.util.Map;

public class FormatoConsola implements FormatoReporte {
    
    private StringBuilder reporte;
    
    public FormatoConsola() {
        this.reporte = new StringBuilder();
    }
    
    @Override
    public void iniciar(String titulo) {
        reporte.append("\n");
        reporte.append("═".repeat(60)).append("\n");
        reporte.append("  ").append(titulo.toUpperCase()).append("\n");
        reporte.append("═".repeat(60)).append("\n");
    }
    
    @Override
    public void agregarEncabezado(String encabezado) {
        reporte.append("\n");
        reporte.append(">").append(encabezado).append("\n");
        reporte.append("─".repeat(60)).append("\n");
    }
    
    @Override
    public void agregarFila(String clave, String valor) {
        reporte.append(String.format("%-30s : %s\n", clave, valor));
    }
    
    @Override
    public void agregarTabla(String titulo, Map<String, Object> datos) {
        reporte.append("\n");
        reporte.append("📊 ").append(titulo).append("\n");
        reporte.append("─".repeat(60)).append("\n");
        
        for (Map.Entry<String, Object> entry : datos.entrySet()) {
            reporte.append(String.format("%-30s : %s\n", 
                entry.getKey(), 
                entry.getValue()));
        }
    }
    
    @Override
    public void agregarSeparador() {
        reporte.append("─".repeat(60)).append("\n");
    }
    
    @Override
    public void finalizar() {
        reporte.append("═".repeat(60)).append("\n");
        System.out.println(reporte.toString());
    }
    
    @Override
    public String getNombreFormato() {
        return "Consola";
    }
}