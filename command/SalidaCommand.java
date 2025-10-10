
package inventario.burgerhouse.command;

/**
 *
 * @author Mauricio Martinez, Karen Segura
 */

import inventario.burgerhouse.singleton.GestorInventario;
import inventario.burgerhouse.singleton.ProductoNoEncontradoException;
import inventario.burgerhouse.singleton.StockInsuficienteException;

public class SalidaCommand implements InventarioCommand {

    private final GestorInventario gestor;
    private final String productoId;
    private final int cantidad;

    public SalidaCommand(GestorInventario gestor, String productoId, int cantidad) {
        if (gestor == null) throw new IllegalArgumentException("gestor no puede ser nulo");
        this.gestor = gestor;
        this.productoId = productoId;
        this.cantidad = cantidad;
    }

    @Override
    public void execute() {
        try {
            gestor.registrar_salida(productoId, cantidad);
            System.out.println("Salida registrada: producto=" + productoId + ", cant=" + cantidad);
        } catch (ProductoNoEncontradoException | StockInsuficienteException e) {
            System.out.println("Error al registrar salida: " + e.getMessage());
        }
    }

    @Override
    public String getNombre() {
        return "Salida(" + productoId + "," + cantidad + ")";
    }
}