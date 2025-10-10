
package inventario.burgerhouse.command;

/**
 *
 * @author dct-yulieth
 */

import java.util.ArrayList;
import java.util.List;

public class OperadorInventario {

    //- Historial simple de comandos ejecutados
    private final List<String> historial = new ArrayList<>();

    //- Ejecuta un comando y lo registra
    public void ejecutar(InventarioCommand cmd) {
        if (cmd == null) throw new IllegalArgumentException("cmd no puede ser nulo");
        cmd.execute();
        historial.add(cmd.getNombre());
    }

    //- Ejecuta varios comandos en orden
    public void ejecutarTodos(List<? extends InventarioCommand> comandos) {
        if (comandos == null) return;
        for (InventarioCommand c : comandos) {
            ejecutar(c);
        }
    }

    //- Devuelve una copia del historial
    public List<String> getHistorial() {
        return new ArrayList<>(historial);
    }

    //- Limpia el historial
    public void limpiar() {
        historial.clear();
    }
}