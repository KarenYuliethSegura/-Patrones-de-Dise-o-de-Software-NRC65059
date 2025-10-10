
package inventario.burgerhouse.strategy;

/**
 *
 * @author Mauricio Martinez, Karen Segura
 */

import inventario.burgerhouse.domain.Lote;
import java.util.Comparator;
import java.util.List;

public class PoliticaFEFO implements PoliticaConsumo {
    //- FEFO: primero en vencer es el primero en salir (date_exp asc; nulls al final)
    @Override
    public Lote seleccionar(List<Lote> lotes) {
        if (lotes == null || lotes.isEmpty()) return null;
        return lotes.stream()
                .sorted(Comparator.comparing(
                        Lote::getDate_exp,
                        Comparator.nullsLast(Comparator.naturalOrder())
                ))
                .findFirst()
                .orElse(null);
    }
}
