
package inventario.burgerhouse.factory;

/**
 *
 * @author yulieth segura
 */
import inventario.burgerhouse.builder.ProductoPanaderiaBuilder;
import inventario.burgerhouse.domain.Producto;

public class FabricaPanaderia implements AbstractCategoriaFactory {

    @Override
    public Producto crearProducto(String id, String name, double price, String unit) {
        return new ProductoPanaderiaBuilder()
                .id(id)
                .name(name)
                .price(price)
                .unit(unit)
                .build();
    }
}