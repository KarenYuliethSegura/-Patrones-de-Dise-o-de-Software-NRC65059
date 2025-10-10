
package inventario.burgerhouse.command;

/**
 *
 * @author Mauricio Martinez, Karen Segura
 */
public interface InventarioCommand {
    //- Ejecuta la operación encapsulada
    void execute();

    //- Identificador legible para auditoría
    String getNombre();
}
