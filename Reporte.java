
package inventario.burgerhouse.bridge;

/**
 *
 * @author Mauricio Martinez, Karen Segura
 */

import inventario.burgerhouse.singleton.GestorInventario;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class Reporte {

    //- Formato actual
    protected FormatoReporte formato;
    //- Punto único de acceso al inventario
    protected final GestorInventario gestor;

    protected Reporte(FormatoReporte formato) {
        if (formato == null) throw new IllegalArgumentException("El formato no puede ser nulo");
        this.formato = formato;
        this.gestor = GestorInventario.getInstance();
    }

    //- Plantilla inmutable del proceso de generacion
    public final void generar() {
        formato.iniciar(getTitulo());

        //- Metadatos comunes a todos los reportes
        formato.agregarEncabezado("Informacion General");
        formato.agregarFila("Formato", formato.getNombreFormato());
        formato.agregarFila("Fecha Generacion",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
        formato.agregarFila("Sistema", "Burger House - Gestión de Inventarios");
        formato.agregarSeparador();

        //- Contenido especifico de cada reporte
        agregarContenido();

        //- Cierre del documento
        formato.finalizar();
    }

    //- Permite cambiar el implementor en tiempo de ejecucion
    public void setFormato(FormatoReporte nuevo) {
        if (nuevo == null) throw new IllegalArgumentException("El formato no puede ser nulo");
        this.formato = nuevo;
    }

    public FormatoReporte getFormato() {
        return formato;
    }

    //- Seccion variable: cada subclase define su contenido
    protected abstract void agregarContenido();

    //- Titulo del reporte
    protected abstract String getTitulo();
}
