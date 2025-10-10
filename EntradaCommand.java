
package inventario.burgerhouse.command;

/**
 *
 * @author Mauricio Martinez, Karen Segura
 */

import inventario.burgerhouse.singleton.GestorInventario;
import inventario.burgerhouse.domain.Lote;
import java.time.LocalDate;

public class EntradaCommand implements InventarioCommand {

    //- Dependencias minimas para registrar una entrada
    private final GestorInventario gestor;
    private final String loteId;
    private final String productoId;
    private final LocalDate fechaIngreso;
    private final LocalDate fechaVencimiento;
    private final int cantidad;

    public EntradaCommand(GestorInventario gestor, String loteId, String productoId,
                          LocalDate fechaIngreso, LocalDate fechaVencimiento, int cantidad) {
        if (gestor == null) throw new IllegalArgumentException("gestor no puede ser nulo");
        this.gestor = gestor;
        this.loteId = loteId;
        this.productoId = productoId;
        this.fechaIngreso = fechaIngreso != null ? fechaIngreso : LocalDate.now();
        this.fechaVencimiento = fechaVencimiento;
        this.cantidad = cantidad;
    }

    @Override
    public void execute() {
        //- Construye el lote y registra la entrada en el gestor
        Lote lote = new Lote(loteId, productoId, fechaIngreso, fechaVencimiento, cantidad);
        gestor.registrar_entrada(lote);
        System.out.println("Entrada registrada: lote=" + loteId + ", producto=" + productoId + ", cant=" + cantidad);
    }

    @Override
    public String getNombre() {
        return "Entrada(" + productoId + "," + cantidad + ")";
    }
}