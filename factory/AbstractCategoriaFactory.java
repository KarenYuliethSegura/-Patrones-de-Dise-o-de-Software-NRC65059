
package inventario.burgerhouse.factory;
/**
 *
 * @author yulieth segura
 */
import inventario.burgerhouse.domain.Producto;

public interface AbstractCategoriaFactory {
    Producto crearProducto(String id, String name, double price, String unit);
}
