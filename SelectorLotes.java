
package inventario.burgerhouse.strategy;

/**
 *
 * @author Mauricio Martinez, Karen Segura
 */

import inventario.burgerhouse.domain.Lote;
import inventario.burgerhouse.singleton.GestorInventario;
import java.util.List;

public class SelectorLotes {
    //- Estrategia actual (por defecto PEPS)
    private PoliticaConsumo politica = new PoliticaPEPS();

    //- Permite inyectar otra estrategia en tiempo de ejecucion
    public void setPolitica(PoliticaConsumo politica) {
        if (politica == null) throw new IllegalArgumentException("La política no puede ser nula");
        this.politica = politica;
    }

    public PoliticaConsumo getPolitica() {
        return politica;
    }

    //- Seleccin directa sobre una lista dada
    public Lote seleccionar(List<Lote> lotes) {
        return politica.seleccionar(lotes);
    }

    //- Selecciona a partir de los lotes activos del gestor para un producto
    public Lote seleccionarParaProducto(String productoId) {
        List<Lote> lotes = GestorInventario.getInstance().consultar_lotes_activos(productoId);
        return politica.seleccionar(lotes);
    }
}