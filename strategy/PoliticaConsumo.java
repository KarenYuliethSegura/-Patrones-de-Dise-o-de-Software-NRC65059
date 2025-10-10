
package inventario.burgerhouse.strategy;

/**
 *
 * @author Mauricio Martinez, Karen Segura
 */
import inventario.burgerhouse.domain.Lote;
import java.util.List;

public interface PoliticaConsumo {
    //- Selecciona el lote a consumir según la estrategia
    Lote seleccionar(List<Lote> lotes);
}
