package inventario.burgerhouse.factory;

/**
 *
 * @author yulieth segura
 */
import inventario.burgerhouse.builder.ProductoConservaBuilder;
import inventario.burgerhouse.domain.Producto;

public class FabricaConservas implements AbstractCategoriaFactory {

    @Override
    public Producto crearProducto(String id, String name, double price, String unit) {
        return new ProductoConservaBuilder()
                .id(id)
                .name(name)
                .price(price)
                .unit(unit)
                .build();
    }
}