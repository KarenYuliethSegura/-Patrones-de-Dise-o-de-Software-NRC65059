
package inventario.burgerhouse.strategy;

/**
 *
 * @author Mauricio Martinez, Karen Segura
 */

import inventario.burgerhouse.domain.Lote;
import java.util.Comparator;
import java.util.List;

public class PoliticaPEPS implements PoliticaConsumo {
    //- PEPS: primero en entrar es el primero en salir (date_entry asc)
    @Override
    public Lote seleccionar(List<Lote> lotes) {
        if (lotes == null || lotes.isEmpty()) return null;
        return lotes.stream()
                .sorted(Comparator.comparing(
                        Lote::getDate_entry,
                        Comparator.nullsLast(Comparator.naturalOrder())
                ))
                .findFirst()
                .orElse(null);
    }
}