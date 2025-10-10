
package inventario.burgerhouse.bridge;

/**
 *
 * @author Mauricio Martinez, Karen Segura
 */
import java.util.Map;

public interface FormatoReporte {
    
    //- Inicia el reporte en el formato específico
    void iniciar(String titulo);
    //- Agrega un encabezado al reporte
    void agregarEncabezado(String encabezado);
    //- Agrega una fila de datos al reporte
    void agregarFila(String clave, String valor);  
    //- Agrega una tabla de datos al reporte
    void agregarTabla(String titulo, Map<String, Object> datos);
    //- Agrega un separador visual
    void agregarSeparador();
    //- Finaliza el reporte y lo guarda/muestra
    void finalizar();
    //- Obtiene el nombre del formato
    String getNombreFormato();
}
