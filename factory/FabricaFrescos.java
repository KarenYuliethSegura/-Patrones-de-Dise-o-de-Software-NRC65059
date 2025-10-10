
package inventario.burgerhouse.factory;

/**
 *
 * @author dct-yulieth
 */
import inventario.burgerhouse.builder.ProductoFrescoBuilder;
import inventario.burgerhouse.domain.Producto;

public class FabricaFrescos implements AbstractCategoriaFactory {

    @Override
    public Producto crearProducto(String id, String name, double price, String unit) {
        return new ProductoFrescoBuilder()
                .id(id)
                .name(name)
                .price(price)
                .unit(unit)
                .build();
    }
}
